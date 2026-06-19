(function() {
    'use strict';

    // Biến toàn cục
    let searchInput, searchSuggestions, currentFocus;

    // Khởi tạo khi DOM sẵn sàng
    function init() {
        searchInput = document.getElementById('searchInput');
        searchSuggestions = document.getElementById('searchSuggestions');
        currentFocus = -1;

        if (!searchInput || !searchSuggestions) return;

        setupEventListeners();
    }

    // Thiết lập event listeners
    function setupEventListeners() {
        // Input event với debounce
        let timeout;
        searchInput.addEventListener('input', function() {
            clearTimeout(timeout);
            timeout = setTimeout(() => {
                handleInput(this.value.trim());
            }, 300);
        });

        // Keyboard navigation
        searchInput.addEventListener('keydown', handleKeydown);

        // Click outside to close
        document.addEventListener('click', handleClickOutside);

        // Prevent form submit when selecting suggestion
        const searchForm = document.getElementById('searchForm');
        if (searchForm) {
            searchForm.addEventListener('submit', function(e) {
                if (currentFocus > -1) {
                    e.preventDefault();
                }
            });
        }
    }

    // Xử lý input
    function handleInput(keyword) {
        if (keyword.length < 2) {
            hideSuggestions();
            return;
        }

        fetchSuggestions(keyword);
    }

    // Xử lý phím
    function handleKeydown(e) {
        const items = searchSuggestions.querySelectorAll('.search-suggestion-item');

        switch(e.key) {
            case 'ArrowDown':
                e.preventDefault();
                navigateSuggestions(1, items);
                break;

            case 'ArrowUp':
                e.preventDefault();
                navigateSuggestions(-1, items);
                break;

            case 'Enter':
                if (currentFocus > -1) {
                    e.preventDefault();
                    const activeItem = items[currentFocus];
                    if (activeItem) {
                        window.location.href = activeItem.href;
                    }
                }
                break;

            case 'Escape':
                hideSuggestions();
                searchInput.blur();
                break;
        }
    }

    // Điều hướng suggestions
    function navigateSuggestions(direction, items) {
        if (items.length === 0) return;

        // Remove active class from all items
        items.forEach(item => item.classList.remove('active'));

        // Calculate new index
        if (direction === 1) { // Down
            currentFocus = (currentFocus + 1) % items.length;
        } else { // Up
            currentFocus = (currentFocus - 1 + items.length) % items.length;
        }

        // Add active class to current item
        items[currentFocus].classList.add('active');

        // Update input value with current suggestion
        const itemName = items[currentFocus].querySelector('.search-suggestion-name').textContent;
        searchInput.value = itemName;
    }

    // Click outside to close
    function handleClickOutside(e) {
        if (!searchInput.contains(e.target) && !searchSuggestions.contains(e.target)) {
            hideSuggestions();
        }
    }

    // Fetch suggestions from server
    function fetchSuggestions(keyword) {
        // Show loading
        searchSuggestions.innerHTML = '<div class="search-no-results">Đang tìm kiếm...</div>';
        searchSuggestions.style.display = 'block';

        // Use fetch API (modern)
        fetch(`search?keyword=${encodeURIComponent(keyword)}&ajax=true`)
            .then(response => {
                if (!response.ok) throw new Error('Network error');
                return response.json();
            })
            .then(data => {
                displaySuggestions(data, keyword);
            })
            .catch(error => {
                console.error('Search error:', error);
                searchSuggestions.innerHTML = '<div class="search-no-results">Không thể kết nối đến server</div>';
                searchSuggestions.style.display = 'block';
            });
    }

    // Display suggestions
    function displaySuggestions(data, keyword) {
        if (!data || data.length === 0) {
            searchSuggestions.innerHTML = '<div class="search-no-results">Không tìm thấy sản phẩm</div>';
            searchSuggestions.style.display = 'block';
            currentFocus = -1;
            return;
        }

        let html = '';
        data.forEach((item, index) => {
            html += `
                <a href="detail?id=${item.id}" 
                   class="search-suggestion-item ${index === 0 ? 'active' : ''}" 
                   data-index="${index}">
                    ${item.img ? `<img src="${item.img}" class="search-suggestion-img" alt="${item.name || ''}">` : ''}
                    <div class="search-suggestion-info">
                        <div class="search-suggestion-name">${highlightText(item.name, keyword)}</div>
                        <div class="search-suggestion-price">${formatPrice(item.price)}₫</div>
                    </div>
                </a>
            `;
        });

        // Add "View all results" link
        html += `<a href="search?keyword=${encodeURIComponent(keyword)}" class="search-view-all">Xem tất cả kết quả (${data.length})</a>`;

        searchSuggestions.innerHTML = html;
        searchSuggestions.style.display = 'block';
        currentFocus = 0;

        // Add hover events
        document.querySelectorAll('.search-suggestion-item').forEach(item => {
            item.addEventListener('mouseenter', function() {
                document.querySelectorAll('.search-suggestion-item').forEach(i => i.classList.remove('active'));
                this.classList.add('active');
                currentFocus = parseInt(this.dataset.index);
            });
        });
    }

    // Highlight search text
    function highlightText(text, keyword) {
        if (!keyword || !text) return text;

        try {
            const escapedKeyword = keyword.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
            const regex = new RegExp(`(${escapedKeyword})`, 'gi');
            return text.replace(regex, '<mark class="search-highlight">$1</mark>');
        } catch (e) {
            return text;
        }
    }

    // Format price
    function formatPrice(price) {
        if (!price) return '0';
        return new Intl.NumberFormat('vi-VN').format(price);
    }

    // Hide suggestions
    function hideSuggestions() {
        if (searchSuggestions) {
            searchSuggestions.style.display = 'none';
            currentFocus = -1;
        }
    }

    // Public API (nếu cần)
    window.SearchAutocomplete = {
        init: init,
        hide: hideSuggestions,
        focus: function() {
            if (searchInput) searchInput.focus();
        }
    };

    // Initialize on DOM ready
    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', init);
    } else {
        init();
    }

})();
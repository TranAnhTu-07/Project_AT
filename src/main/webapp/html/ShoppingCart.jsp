<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>

    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Giỏ Hàng</title>
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/footer.css">
    <link rel="stylesheet" href="css/ShoppingCart.css">
    <script src="../js/ShoppingCart.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">

</head>

<body>
<script>
    // Hàm cập nhật số lượng
    function updateQuantity(productId, change, newValue = null) {
        let quantity;
        const input = document.getElementById('quantity-' + productId);
        const maxStock = parseInt(input.getAttribute('max'));

        if (newValue !== null) {
            // Người dùng nhập trực tiếp
            quantity = parseInt(newValue);
            if (isNaN(quantity) || quantity < 1) {
                quantity = 1;
            }
        } else {
            // Bấm nút +/-
            quantity = parseInt(input.value) + change;
            if (quantity < 1) {
                quantity = 1;
            }
        }

        // Kiểm tra không vượt quá stock
        if (quantity > maxStock) {
            alert('Số lượng vượt quá tồn kho! Tồn kho hiện có: ' + maxStock);
            quantity = maxStock;
        }

        // Cập nhật giá trị hiển thị ngay
        input.value = quantity;

        // Gửi request đến server
        fetch('<%= request.getContextPath() %>/cart?action=update&productId=' + productId + '&quantity=' + quantity, {
            method: 'POST'
        })
            .then(response => {
                if (response.ok) {
                    // Hiệu ứng visual
                    const btn = event.target;
                    btn.style.backgroundColor = '#4CAF50';
                    btn.style.color = 'white';
                    setTimeout(() => {
                        btn.style.backgroundColor = '';
                        btn.style.color = '';
                        // Tải lại trang sau 0.5s để cập nhật tổng tiền
                        setTimeout(() => {
                            location.reload();
                        }, 500);
                    }, 300);
                } else {
                    alert('Có lỗi xảy ra khi cập nhật');
                    location.reload(); // Tải lại để lấy giá trị cũ
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Không thể kết nối đến server');
            });
    }

    // Hàm xóa sản phẩm
    function removeItem(productId) {
        if (confirm('Bạn có chắc muốn xóa sản phẩm này khỏi giỏ hàng?')) {
            fetch('<%= request.getContextPath() %>/cart?action=remove&productId=' + productId, {
                method: 'POST'
            })
                .then(response => {
                    if (response.ok) {
                        // Hiệu ứng xóa
                        const item = document.querySelector('[data-product-id="' + productId + '"]');
                        if (item) {
                            item.style.transition = 'opacity 0.3s';
                            item.style.opacity = '0';
                            setTimeout(() => {
                                item.style.display = 'none';
                                location.reload();
                            }, 300);
                        }
                    }
                });
        }
    }

    // Hàm xóa toàn bộ giỏ hàng
    function clearCart() {
        if (confirm('Bạn có chắc muốn xóa toàn bộ giỏ hàng?')) {
            window.location.href = '<%= request.getContextPath() %>/cart?action=clear';
        }
    }

    // Thêm hiệu ứng khi hover nút +/-
    document.addEventListener('DOMContentLoaded', function() {
        const quantityBtns = document.querySelectorAll('.quantity-btn');
        quantityBtns.forEach(btn => {
            btn.addEventListener('mouseenter', function() {
                this.style.transform = 'scale(1.1)';
                this.style.transition = 'transform 0.2s';
            });
            btn.addEventListener('mouseleave', function() {
                this.style.transform = 'scale(1)';
            });
        });

        // Hiệu ứng input
        const quantityInputs = document.querySelectorAll('.quantity-input');
        quantityInputs.forEach(input => {
            input.addEventListener('focus', function() {
                this.style.borderColor = '#4CAF50';
                this.style.boxShadow = '0 0 5px rgba(76, 175, 80, 0.3)';
            });
            input.addEventListener('blur', function() {
                this.style.borderColor = '';
                this.style.boxShadow = '';
            });
        });
    });
</script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    // Lấy giỏ hàng từ session
    HttpSession sessionObj = request.getSession();
    Object cartObj = sessionObj.getAttribute("cart");
    vn.edu.nlu.fit.projectweb.cart.Cart cart = null;
    java.util.List<vn.edu.nlu.fit.projectweb.cart.CartItem> cartItems = null;
    double cartTotal = 0;
    int totalQuantity = 0;
    String cartTotalFormatted = "0 ₫";

    if (cartObj != null) {
        cart = (vn.edu.nlu.fit.projectweb.cart.Cart) cartObj;
        cartItems = cart.getItems();
        cartTotal = cart.getTotal();
        totalQuantity = cart.getTotalQuantity();

        // Format tiền tệ
        java.text.NumberFormat currencyFormat = java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("vi", "VN"));
        cartTotalFormatted = currencyFormat.format(cartTotal);
    }
%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!--header-->
<jsp:include page="/common/header.jsp"/>
<!--body-->

<div class="container">
    <h1 class="page-title">Giỏ Hàng Của Bạn</h1>

    <div class="cart-container">
        <div class="cart-items">
            <c:choose>
                <c:when test="<%= cartItems == null || cartItems.isEmpty() %>">
                    <!-- Giỏ hàng trống -->
                    <div style="text-align: center; padding: 60px 0;">
                        <div style="font-size: 80px; color: #ddd; margin-bottom: 20px;">🛒</div>
                        <div style="font-size: 18px; color: #777; margin-bottom: 20px;">Giỏ hàng của bạn đang trống</div>
                        <a href="<%= request.getContextPath() %>/ListProduct" style="display: inline-block; padding: 12px 24px; background-color: #ee4d2d; color: white; text-decoration: none; border-radius: 4px;">Mua sắm ngay</a>
                    </div>
                </c:when>
                <c:otherwise>
                    <!-- Có sản phẩm trong giỏ -->
                    <%
                        for (vn.edu.nlu.fit.projectweb.cart.CartItem item : cartItems) {
                            vn.edu.nlu.fit.projectweb.model.Product product = item.getProduct();
                            java.text.NumberFormat currency = java.text.NumberFormat.getCurrencyInstance(new java.util.Locale("vi", "VN"));
                            String itemPrice = currency.format(item.getPrice());
                    %>
                    <div class="cart-item" data-product-id="<%= product.getProductID() %>">
                        <img src="<%= product.getImg() != null ? product.getImg() : "https://via.placeholder.com/100" %>"
                             alt="<%= product.getProductName() %>" class="item-image">
                        <div class="item-details">
                            <div class="item-name"><%= product.getProductName() %></div>
                            <div class="item-price"><%= itemPrice %></div>
                            <div class="quantity-controls">
                                <a href="<%= request.getContextPath() %>/cart?action=decrease&productId=<%= product.getProductID() %>"
                                   class="quantity-btn">-</a>
                                <input type="text"
                                       class="quantity-input"
                                       value="<%= item.getQuantity() %>"
                                       readonly
                                       style="text-align: center; width: 40px;">

                                <a href="<%= request.getContextPath() %>/cart?action=increase&productId=<%= product.getProductID() %>"
                                   class="quantity-btn">+</a>
                            </div>
                            <button class="remove-btn" onclick="removeFromCart(<%= product.getProductID() %>)">Xóa</button>
                        </div>
                    </div>
                    <%
                        }
                    %>
                </c:otherwise>
            </c:choose>
        </div>



        <div class="cart-summary">
            <h2 class="summary-title">Tóm tắt đơn hàng</h2>

            <div class="summary-row">
                <span>Tạm tính:</span>
                <span id="subtotal"><%= cartTotalFormatted %></span>
            </div>

            <div class="summary-row">
                <span>Phí vận chuyển:</span>
                <span id="shipping">30.000 ₫</span>
            </div>

            <div class="summary-row summary-total">
                <span>Tổng cộng:</span>
                <%
                    double totalWithShipping = cartTotal + 30000;
                    NumberFormat currency = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                    String totalFormatted = currency.format(totalWithShipping);
                %>
                <span id="total"><%= totalFormatted %></span>
            </div>

            <div class="promo-code">
                <label for="promo">Mã giảm giá:</label>
                <input type="text" id="promo" class="promo-input" placeholder="Nhập mã giảm giá">
                <button class="apply-promo" onclick="applyPromo()">Áp dụng</button>
            </div>

            <button class="checkout-btn" onclick="window.location.href='${pageContext.request.contextPath}/PrepareCheckout'">Tiến hành thanh toán</button>
            <a href="<%= request.getContextPath() %>/ListProduct" class="continue-shopping">Tiếp tục mua sắm</a>
        </div>
    </div>
</div>
<button onclick="clearCart()" style="margin-top: 15px; width: 100%; padding: 12px; background-color: #e74c3c; color: white; border: none; border-radius: 4px; cursor: pointer;">
    Xóa giỏ hàng
</button>

<div class="notification" id="notification">Sản phẩm đã được xóa khỏi giỏ hàng!</div>
<!--footer-->
<footer class="footer">
    <div class="footer-content">
        <!-- Logo Section -->
        <div class="footer-section logo-section">
            <div class="logo">
                <div class="logo-icon">📷</div>
                <div class="logo-text">
                    <h2>GROUP11</h2>
                    <p>Vì lời tín khách hàng</p>
                </div>
            </div>
            <p class="description">
                Máy Ảnh Việt Nam là đơn vị tiên phong trong lĩnh vực phân phối và bán lẻ các sản phẩm máy ảnh tại
                thị trường Việt Nam.
            </p>
            <div class="social-icons">
                <div class="social-icon">📘</div>
                <div class="social-icon">📺</div>
                <div class="social-icon">📸</div>
                <div class="social-icon">🐦</div>
                <div class="social-icon">📍</div>
            </div>
            <div class="payment-methods">
                <h4 style="color: #fff; margin-bottom: 15px;">PHƯƠNG THỨC THANH TOÁN</h4>
                <div class="payment-icons">
                    <div class="payment-icon">💳 VISA</div>
                    <div class="payment-icon">💳 MC</div>
                    <div class="payment-icon">💳 JCB</div>
                    <div class="payment-icon">💳 Napas</div>
                    <div class="payment-icon">💳 Home</div>
                    <div class="payment-icon">💳 Momo</div>
                </div>
            </div>
        </div>

        <!-- Policies Section -->
        <div class="footer-section">
            <h3>Chính sách</h3>
            <ul class="policy-links">
                <li><a href="#">Chính Sách Bảo Hành</a></li>
                <li><a href="#">Chính Sách Thanh Toán</a></li>
                <li><a href="#">Chính Sách Đổi Trả, Hoàn Tiền</a></li>
                <li><a href="#">Chính Sách Vận Chuyển</a></li>
                <li><a href="#">Chính Sách Bảo Mật Thông Tin Khách Hàng</a></li>
                <li><a href="#">Thông Tin Liên Hệ</a></li>
            </ul>
            <h3 style="margin-top: 30px;">Thông tin liên hệ</h3>
            <ul class="contact-info">
                <li>
                    <span class="icon">📄</span>
                    <span><strong>Fanpage:</strong>GROUP11</span>
                </li>
                <li>
                    <span class="icon">✉️</span>
                    <span><strong>Email:</strong> 23130364@st.hcmuaf.edu.vn</span>
                </li>
            </ul>
        </div>

        <!-- Store Locations Section -->
        <div class="footer-section">
            <h3>Thành viên Group 11</h3>
            <div class="location-item">
                <strong>Trần Anh Tú - 23130364 </strong>
            </div>
            <div class="location-item">
                <strong>Trần Công Vinh - 23130384 </strong>
            </div>
            <div class="location-item">
                <strong>Nguyễn Thúy Vy - 23130394 </strong>
            </div>
        </div>
    </div>
</footer>

</body>
</html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Group 11</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="css/footer.css">
    <link rel="stylesheet" href="css/Product.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
    <link rel="stylesheet" href="css/index.css">
    <link rel="stylesheet" href="css/spnoibat.css">
    <link rel="stylesheet" href="css/ShoppingCart.css">
    <script src="js/search.js"></script>


</head>
<body>
<header class="header">
    <div class="header-top">
        <div class="logo-search">
            <a href="ListProduct" class="logo">
                <div class="logo-icon">📷</div>
                <div class="logo-text">
                    <div class="logo-main">GROUP11</div>
                    <div class="logo-sub">Shop máy ảnh uy tín nhất Việt Nam</div>
                </div>
            </a>

            <div class="search-box">
                <form action="search" method="get" id="searchForm">
                    <input type="text" name="keyword" id="searchInput"
                           placeholder="Tìm kiếm sản phẩm..."
                           value="${param.keyword}"
                           autocomplete="off">
                    <button type="submit"><i class="fas fa-search"></i></button>
                    <div id="searchSuggestions" class="search-suggestions"></div>
                </form>
            </div>
        </div>

        <div class="header-right">
            <div class="hotline">
                <div class="hotline-label">HOTLINE: 0903.148-222</div>
                <div class="header-links">
                    <a href="ListProduct">MUA HÀNG</a> -
                    <a href="#">TRẢ GÓP</a> -
                    <a href="#">BẢO HÀNH</a>
                </div>
            </div>
            <div id="custom-user-account" style="position: relative; display: flex; align-items: center; margin-left: 15px; height: 100%; cursor: pointer; z-index: 9999;">

                <c:choose>
                    <c:when test="${sessionScope.account == null}">
                        <a href="${pageContext.request.contextPath}/html/login.jsp"
                           style="color: white; text-decoration: none; display: flex; align-items: center; gap: 5px; font-weight: bold;">
                            <i class="fas fa-user"></i>
                            <span>Đăng nhập</span>
                        </a>
                    </c:when>

                    <c:otherwise>
                        <div class="user-trigger" style="display: flex; align-items: center; gap: 8px; color: white; font-weight: bold; padding: 10px 0;">
                            <i class="fas fa-user-circle" style="font-size: 22px; color: #28a745;"></i>
                            <span>${sessionScope.account.fullName}</span>
                            <i class="fas fa-caret-down" style="font-size: 14px; opacity: 0.8;"></i>
                        </div>

                        <div class="custom-dropdown-box">
                            <div style="padding: 12px 15px; background: #f8f9fa; border-bottom: 1px solid #eee; font-size: 11px; color: #888; font-weight: bold; text-transform: uppercase; letter-spacing: 0.5px;">
                                Tài khoản của tôi
                            </div>

                            <a href="profile" class="dropdown-item">
                                <i class="fas fa-id-card"></i> Hồ sơ cá nhân
                            </a>
                            <a href="BaoMatKhoa" class="dropdown-item">
                                <i class="fas fa-key"></i> Báo mất khóa
                            </a>
                            <a href="CapNhatKhoa" class="dropdown-item">
                                <i class="fas fa-plus-circle"></i> Cập nhật / Tạo khóa mới
                            </a>
                            <a href="Logout" class="dropdown-item" style="color: #dc3545 !important; border-top: 1px solid #eee;">
                                <i class="fas fa-sign-out-alt"></i> Đăng xuất
                            </a>
                        </div>
                    </c:otherwise>
                </c:choose>
                <style>

                    .custom-dropdown-box {
                        display: none; /* Mặc định ẩn */
                        position: absolute; /* Tuyệt đối so với cha */
                        top: 100%; /* Nằm ngay dưới đáy */
                        right: 0; /* Căn phải */
                        width: 230px; /* Chiều rộng cố định để không bị bể */
                        background-color: #ffffff !important; /* Nền trắng tuyệt đối */
                        box-shadow: 0 8px 20px rgba(0,0,0,0.15); /* Đổ bóng */
                        border-radius: 6px;
                        border: 1px solid #e1e1e1;
                        z-index: 99999; /* Luôn nổi lên trên cùng */
                        overflow: hidden; /* Cắt góc bo tròn */
                        margin-top: 5px; /* Cách header 1 xíu cho đẹp */
                    }

                    /* Mũi tên nhọn trang trí */
                    .custom-dropdown-box::before {
                        content: "";
                        position: absolute;
                        top: -6px;
                        right: 20px;
                        width: 12px;
                        height: 12px;
                        background: white;
                        transform: rotate(45deg);
                        border-top: 1px solid #e1e1e1;
                        border-left: 1px solid #e1e1e1;
                    }

                    /* Hover vào cha thì hiện con */
                    #custom-user-account:hover .custom-dropdown-box {
                        display: block !important;
                        animation: fadeInDrop 0.2s ease-out;
                    }

                    /* Style cho từng dòng link */
                    .dropdown-item {
                        display: block !important; /* Bắt buộc xuống dòng */
                        padding: 12px 15px !important;
                        color: #333333 !important; /* Màu chữ đen xám */
                        text-decoration: none !important;
                        font-size: 14px;
                        font-weight: 500;
                        background: white;
                        transition: all 0.2s;
                        text-align: left;
                        line-height: 1.5;
                    }

                    .dropdown-item i {
                        width: 25px;
                        text-align: center;
                        color: #666;
                        margin-right: 5px;
                    }

                    .dropdown-item:hover {
                        background-color: #f0f7ff !important;
                        color: #007bff !important;
                        padding-left: 20px !important; /* Hiệu ứng đẩy chữ */
                    }

                    .dropdown-item:hover i {
                        color: #007bff !important;
                    }

                    @keyframes fadeInDrop {
                        from { opacity: 0; transform: translateY(10px); }
                        to { opacity: 1; transform: translateY(0); }
                    }
                </style>
            </div>

            <a href="${pageContext.request.contextPath}/cart">
                <div class="logo-icon" style="position: relative;">
                    🛒
                    <span class="cart-count">
                        ${sessionScope.cart.getTotalQuantity()}
                    </span>
                </div>
            </a>
        </div>
    </div>
</header>
<nav class="nav-menu">
    <ul>
        <li class="sub">
            <a href="#" class="sub-item">
                <i class="fas fa-camera"></i>
                <span>MÁY ẢNH CANON</span>
            </a>
            <div class="null">
                <ul class="sub-menu">
                    <li><a href="Canon?cid=8" class="nav-item" >MÁY ẢNH CANON DSLR</a></li>
                    <li><a href="Canon?cid=9" class="nav-item" >MÁY ẢNH CANON COMPACT</a></li>
                    <li><a href="Canon?cid=10" class="nav-item" >MÁY ẢNH CANON MIRRORLESS</a></li>
                </ul>
            </div>
        </li>
    </ul>

    <ul>
        <li class="sub">
            <a href="#" class="sub-item">
                <i class="fas fa-camera"></i>
                <span>MÁY ẢNH SONY</span>
            </a>
            <div class="null">
                <ul class="sub-menu">
                    <li><a href="Sony?cid=11" class="nav-item" >MÁY ẢNH SONY COMPACT</a></li>
                    <li><a href="Sony?cid=12" class="nav-item" >MÁY ẢNH SONY MIRRORLESS</a></li>
                </ul>
            </div>
        </li>
    </ul>

    <ul>
        <li class="sub">
            <a href="#" class="sub-item">
                <i class="fas fa-camera"></i>
                <span>MÁY ẢNH NIKON</span>
            </a>
            <div class="null">
                <ul class="sub-menu">
                    <li><a href="Nikon?cid=14" class="nav-item">MÁY ẢNH NIKON COMPACT</a></li>
                    <li><a href="Nikon?cid=15" class="nav-item">MÁY ẢNH NIKON MIRRORLESS</a></li>
                </ul>
            </div>
        </li>
    </ul>

    <ul>
        <li class="sub">
            <a href="#" class="sub-item">
                <i class="fas fa-camera"></i>
                <span>MÁY ẢNH FUJIFILM</span>
            </a>
            <div class="null">
                <ul class="sub-menu">
                    <li><a href="Fujifilm?cid=16" class="nav-item">MÁY ẢNH FUJIFILM COMPACT</a></li>
                    <li><a href="Fujifilm?cid=17" class="nav-item">MÁY ẢNH FUJIFILM MIRRORLESS</a></li>
                </ul>
            </div>
        </li>
    </ul>

    <ul>
        <li class="sub">
            <a href="#" class="sub-item">
                <i class="fas fa-camera"></i>
                <span>MÁY ẢNH HÃNG KHÁC</span>
            </a>
            <div class="null">
                <ul class="sub-menu">
                    <li><a href="#" class="nav-item">MÁY ẢNH LUMIX</a></li>
                    <li><a href="#" class="nav-item">MÁY ẢNH LEICA</a></li>
                    <li><a href="#" class="nav-item">MÁY ẢNH SIGMA</a></li>
                </ul>
            </div>
        </li>
    </ul>

    <ul>
        <li class="sub">
            <a href="#" class="sub-item">
                <i class="fas fa-glasses"></i>
                <span>ỐNG KÍNH</span>
            </a>
            <div class="null">
                <ul class="sub-menu">
                    <li><a href="OngKinh?cid=21" class="nav-item">ỐNG KÍNH CANON</a></li>
                    <li><a href="OnhKinh?cid=22" class="nav-item">ỐNG KÍNH SONY</a></li>
                    <li><a href="OnhKinh?cid=23" class="nav-item">ỐNG KÍNH NIKON</a></li>
                    <li><a href="OnhKinh?cid=24" class="nav-item">ỐNG KÍNH FUJIFILM</a></li>
                    <li><a href="OnhKinh?cid=25" class="nav-item">ỐNG KÍNH LUMIX</a></li>
                    <li><a href="OnhKinh?cid=26" class="nav-item">ỐNG KÍNH LEICA</a></li>
                    <li><a href="OnhKinh?cid=27" class="nav-item">ỐNG KÍNH SIGMA</a></li>

                </ul>
            </div>
        </li>
    </ul>

    <ul>
        <li class="sub">
            <a href="#" class="sub-item">
                <i class="fas fa-headphones"></i>
                <span>PHỤ KIỆN MÁY ẢNH</span>
            </a>
            <div class="null">
                <ul class="sub-menu">
                    <li><a href="PhuKien?cid=28" class="nav-item">BAO ĐỰNG MÁY ẢNH</a></li>
                    <li><a href="PhuKien?cid=29" class="nav-item">CHÂN MÁY ẢNH</a></li>
                    <li><a href="PhuKien?cid=30" class="nav-item">THẺ NHỚ MÁY ẢNH</a></li>
                    <li><a href="PhuKien?cid=31" class="nav-item">SẠC MÁY ẢNH</a></li>
                    <li><a href="PhuKien?cid=32" class="nav-item">TỦ CHỐNG ẨM</a></li>
                    <li><a href="PhuKien?cid=33" class="nav-item">ĐÈN CHỤP FLASH</a></li>
                </ul>
            </div>
        </li>
    </ul>
    <ul>
        <li class="sub">
            <a href="/Project/Quan%20Ly%20User/quanlyuser.html" class="sub-item">
                <i class="fas fa-camera"></i>
                <span>QUẢN LÝ USER</span>
            </a>
        </li>
    </ul>
</nav>
</body>
</html>
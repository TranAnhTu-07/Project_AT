<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%--<%--%>
<%--    if (request.getAttribute("list") == null) {--%>
<%--        response.sendRedirect("ListProduct");--%>
<%--        return;--%>
<%--    }--%>
<%--%>--%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Group 11</title>
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/footer.css">
    <link rel="stylesheet" href="css/Product.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
    <link rel="stylesheet" href="css/index.css">
    <link rel="stylesheet" href="css/spnoibat.css">
    <script src="js/search.js"></script>


</head>
<body>
<jsp:include page="/common/header.jsp"/>
<!--body-->
<div class="slider-container">
    <div class="slider-wrapper" id="sliderWrapper">
        <!-- Slide 1 -->
        <div class="slide">
            <div class="slide-content">
                <h2>Máy ảnh Canon EOS R50</h2>
                <div class="price">Giá từ 18.990.000 VNĐ</div>
                <div class="description">
                    <h3>Máy ảnh Canon EOS R50</h3>
                    <p>Canon R50 với cảm biến APS-C CMOS 24.2MP cùng trọng lượng nhẹ 375g giúp người dùng dễ dàng
                        mang theo và sử dụng. Đồng thời, Canon EOS R50 mang đến khả năng quay video ấn tượng với độ
                        phân giải cao 4K 30p và Full HD 120p, đáp ứng mọi nhu cầu sáng tạo của bạn. Cùng với đó là
                        khả năng chụp hình liên tục lên đến 15 khung hình mỗi giây trên máy ảnh Canon này.</p>
                </div>
                <button class="orders-btn">Đặt hàng ngay</button>
            </div>
            <div class="slide-image">
                <img src="https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/m/a/may-anh-canon-eos-r50_4_.png"
                     alt="Camera Lens">
            </div>
        </div>

        <!-- Slide 2 -->
        <div class="slide">
            <div class="slide-content">
                <h2>Sony Alpha A7 IV</h2>
                <div class="price">Giá từ 52.990.000 VNĐ</div>
                <div class="description">
                    <h3>Sony Alpha A7 IV - Máy ảnh Full-frame</h3>
                    <p>Máy ảnh mirrorless full-frame với cảm biến 33MP, khả năng quay video 4K 60fps, hệ thống lấy
                        nét tự động thông minh với AI, và thời lượng pin vượt trội. Thiết bị hoàn hảo cho cả nhiếp
                        ảnh và quay phim chuyên nghiệp.</p>
                </div>
                <button class="orders-btn">Đặt hàng ngay</button>
            </div>
            <div class="slide-image">
                <img src="https://d1ncau8tqf99kp.cloudfront.net/converted/92650_original_local_1200x1050_v3_converted.webp"
                     alt="Sony Camera">
            </div>
        </div>

        <!-- Slide 3 -->
        <div class="slide">
            <div class="slide-content">
                <h2>Nikon ZR 6K Cinema Camera</h2>
                <div class="price">Giá từ 58.790.000 VNĐ</div>
                <div class="description">
                    <h3>Nikon ZR 6K Cinema Camera</h3>
                    <p>Máy ảnh full-frame 24.2MP với khả năng chụp liên tiếp 40fps, ổn định hình ảnh 8 stops, quay
                        video 6K oversampled 4K. Thiết kế chắc chắn, thời lượng pin tuyệt vời, hoàn hảo cho nhiếp
                        ảnh thể thao và động vật hoang dã.</p>
                </div>
                <button class="orders-btn">Đặt hàng ngay</button>
            </div>
            <div class="slide-image">
                <img src="https://giangduydat.vn/product/nikon-zr-6k-cinema-camera.jpg" alt="Canon Camera">
            </div>
        </div>

        <!-- slide 4 -->
        <div class="slide">
            <div class="slide-content">
                <h2>Fujifilm X-E5</h2>
                <div class="price">Giá từ 58.790.000 VNĐ</div>
                <div class="description">
                    <h3>Fujifilm X-E5</h3>
                    <p>Máy ảnh Fujifilm X-E5 Với cảm biến APS-C X-Trans CMOS 5 HR 40.2MP, khả năng quay video 6.2K,
                        hệ thống chống rung 5 trục tích hợp (IBIS), lấy nét tự động hỗ trợ AI cùng thiết kế
                        rangefinder thanh lịch, X-E5 mang đến trải nghiệm nhiếp ảnh chất lượng cao trong một thân
                        máy nhỏ gọn, tinh tế</p>
                </div>
                <button class="orders-btn">Đặt hàng ngay</button>
            </div>
            <div class="slide-image">
                <img src="https://giangduydat.vn/product/fujifilm-x-e5.jpg" alt="Canon Camera">
            </div>
        </div>
    </div>

    <!-- Dots Navigation -->
    <div class="dots-container" id="dotsContainer">
        <div class="dot active" data-index="0"></div>
        <div class="dot" data-index="1"></div>
        <div class="dot" data-index="2"></div>
        <div class="dot" data-index="3"></div>
    </div>
</div>

<!-- Main Content -->
<main id="main-page-content">
    <div class="container">
        <div class="brand-container">

            <div class="brand-item"> <a href="ListProduct?brand=Canon"> <img src="https://mayanhvietnam.com/asset/imgs/icon/hang-san-xuat/canon.svg" alt="Canon">
            </a>
            </div>

            <div class="brand-item">
                <a href="ListProduct?brand=Sony">
                    <img src="https://mayanhvietnam.com/asset/imgs/icon/hang-san-xuat/sony.svg" alt="Sony">
                </a>
            </div>

            <div class="brand-item">
                <a href="ListProduct?brand=Nikon">
                    <img src="https://mayanhvietnam.com/asset/imgs/icon/hang-san-xuat/nikon.svg" alt="Nikon">
                </a>
            </div>

            <div class="brand-item">
                <a href="ListProduct?brand=Fujifilm">
                    <img src="https://mayanhvietnam.com/asset/imgs/icon/hang-san-xuat/fujifilm.svg" alt="Fujifilm">
                </a>
            </div>

            <div class="brand-item">
                <a href="ListProduct?brand=Lumix">
                    <img src="https://mayanhvietnam.com/asset/imgs/icon/hang-san-xuat/olympus.svg" alt="Lumix">
                </a>
            </div>

            <div class="brand-item">
                <a href="ListProduct?brand=Leica">
                    <img src="https://mayanhvietnam.com/asset/imgs/icon/hang-san-xuat/leica.svg" alt="Leica">
                </a>
            </div>

            <div class="brand-item">
                <a href="ListProduct?brand=Sigma">
                    <img src="https://mayanhvietnam.com/asset/imgs/icon/hang-san-xuat/sigma.svg" alt="Sigma">
                </a>
            </div>

        </div>
        <div class="cover-product-item">
            <c:if test="${empty list}">
                <div style="text-align: center; width: 100%;">
                    <h3 style="color: red;">Không có sản phẩm nào!</h3>
                </div>
            </c:if>

            <c:forEach var="p" items="${list}">
                <div class="product-card-item">

                    <a href="detail?id=${p.productID}" style="text-decoration: none; color: inherit;">

                        <img src="${p.img}" class="product-img" alt="${p.productName}">
                        <h3 class="product-name">${p.productName}</h3>

                        <p class="product-price">
                            <fmt:formatNumber value="${p.price}" type="number" groupingUsed="true"/>₫
                        </p>
                    </a>
                </div>
            </c:forEach>
        </div>
    </div>
</main>
<!-- category-->
<section class="category-slider">
    <h2>Danh mục nổi bật</h2>
    <div class="slider-container">
        <button class="slider-btn prev">&#10094;</button>

        <div class="slider-track">
            <div class="category-item">
                <img src="https://mayanhvietnam.com/asset/imgs/img/danhMuc_MayAnh.webp" alt="Máy ảnh">
                <p>Máy ảnh Canon</p>
            </div>
            <div class="category-item">
                <img src="https://mayanhvietnam.com/asset/imgs/img/danhMuc_ongkinh.webp" alt="Ống kính">
                <p>Ống kính</p>
            </div>
            <div class="category-item">
                <img src="https://mayanhvietnam.com/image-data/san-pham/23-04/23-04-18/230418180440520/avatar/nikon-z6-ii-500x500_may-anh-nikon-z6-ii-body-only-chinh-hang.jpg" alt="Máy ảnh Nikon">
                <p>Máy ảnh Nikon</p>
            </div>
            <div class="category-item">
                <img src="https://bizweb.dktcdn.net/100/107/650/products/8783339-sony-rx1riii-16.jpg?v=1752639489140" alt="Máy ảnh Sony">
                <p>Máy ảnh Sony</p>
            </div>
            <div class="category-item">
                <img src="https://mayanhvietnam.com/image-data/san-pham/23-02/23-02-10/230210224805083/avatar/01_may-anh-compact-ricoh-gr-iii-standard-chinh-hang.jpg" alt="Máy ảnh hãng khác">
                <p>Máy ảnh hãng khác</p>
            </div>
            <div class="category-item">
                <img src="https://mayanhvietnam.com/asset/imgs/img/danhMuc_phuKien.webp" alt="Phụ kiện máy ảnh">
                <p>Phụ kiện máy ảnh</p>
            </div>
        </div>

        <button class="slider-btn next">&#10095;</button>
    </div>
</section>
<!--footer-->
<jsp:include page="/common/footer.jsp"/>
<script src="js/javascript.js"></script>
</body>

</html>
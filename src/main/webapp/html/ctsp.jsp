<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi tiết sản phẩm</title>
    <!-- THÊM DÒNG NÀY ĐỂ LẤY CONTEXT PATH -->
    <c:set var="contextPath" value="${pageContext.request.contextPath}" />

    <!-- SỬA TẤT CẢ ĐƯỜNG DẪN CSS -->
    <link rel="stylesheet" href="${contextPath}/css/header.css">
    <link rel="stylesheet" href="${contextPath}/css/footer.css">
    <link rel="stylesheet" href="${contextPath}/css/Product.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
    <link rel="stylesheet" href="${contextPath}/css/index.css">
    <link rel="stylesheet" href="${contextPath}/css/spnoibat.css">
    <link rel="stylesheet" href="${contextPath}/css/ctsp.css">

</head>
<body>
<!--header-->
<jsp:include page="/common/header.jsp"/>
<!--body-->
<div class="product">
    <div class="product-left">
        <div class="main-box">
            <!-- Hiển thị ảnh chính của sản phẩm -->
            <img id="mainImage" src="${product.img}" alt="${product.productName}">
        </div>

    </div>

    <div class="product-right">
        <!-- Hiển thị tên sản phẩm -->
        <h1>${product.productName}</h1>

        <!-- Hiển thị giá -->
        <p class="label">Giá chính hãng:</p>
        <p class="price">
            <c:choose>
                <c:when test="${product.newPrice > 0 && product.newPrice < product.price}">
                    <span style="text-decoration: line-through; color: #777; margin-right: 10px;">
                        <fmt:formatNumber value="${product.price}" type="number" groupingUsed="true"/>₫
                    </span>
                    <span style="color: #ff0000;">
                        <fmt:formatNumber value="${product.newPrice}" type="number" groupingUsed="true"/>₫
                    </span>
                </c:when>
                <c:otherwise>
                    <fmt:formatNumber value="${product.price}" type="number" groupingUsed="true"/>₫
                </c:otherwise>
            </c:choose>
        </p>

        <div class="info-row">
            <p>Thương hiệu: <span class="brand">${product.brand}</span></p>
            <p>Mã sản phẩm: <span class="code">${product.productID}</span></p>
        </div>

        <div class="button-group">
            <!-- Link MUA HÀNG với ID sản phẩm -->
            <a href="/Project/ThanhToan&DatHang/ttdh.html?id=${product.productID}" class="buy-now">MUA HÀNG</a>

            <!-- Nút thêm vào giỏ hàng -->
            <button class="add-cart" onclick="addToCart(${product.productID})">THÊM VÀO GIỎ</button>

            <!-- Nút đánh giá -->
            <a href="/Project/Product%20Review/ProductReview.html?id=${product.productID}" class="review-button">
                <i class="fas fa-star"></i> Đánh giá
            </a>
        </div>

        <!-- Thông số kỹ thuật -->
        <h2 style="margin-top: 30px;">Thông số kỹ thuật</h2>
        <div class="product-specs">
            <table>
                <tr><td>Mã sản phẩm</td><td>${product.productID}</td></tr>
                <tr><td>Tên sản phẩm</td><td>${product.productName}</td></tr>
                <tr><td>Thương hiệu</td><td>${product.brand}</td></tr>
                <tr><td>Giá</td><td><fmt:formatNumber value="${product.price}" type="number" groupingUsed="true"/>₫</td></tr>
                <tr><td>Loại</td><td>Máy ảnh</td></tr>
            </table>
        </div>
    </div>
</div>
<section class="cover-all-feedback">
    <div class="container-sp">
        <div class="title-feedback">
            <p class="title">Đánh giá sản phẩm</p>
            <p>Xem tất cả ></p>
        </div>
        <div class="cover-feedback">
            <div class="left-feedback">
                <fmt:formatNumber value="${((star5 * 5) + (star4 * 4) + (star3 * 3) + (star2 * 2) + (star1)) * 1.0 / totalReview }" pattern="#.#"/>
                <p class="stars">★★★★★</p>
            </div>
            <div class="right-feedback">
                <div class="cover-line">5 <div class="line-feedback star5"></div></div>
                <div class="cover-line">4 <div class="line-feedback star4"></div></div>
                <div class="cover-line">3 <div class="line-feedback star3"></div></div>
                <div class="cover-line">2 <div class="line-feedback star2"></div></div>
                <div class="cover-line">1 <div class="line-feedback star1"></div></div>
            </div>
        </div>
        <div class="cover-comment">
            <c:forEach var="h" items="${rw}">
                <div class="cmt">
                    <div class="cover-user">
                        <img src="https://www.svgrepo.com/show/452030/avatar-default.svg" alt="">
                        <div class="feedback-user">
                            <span>${h.name}</span>
                            <div class="cover-feedback-date">
                                <p class="stars-comment">
                                    <c:forEach begin="1" end="${h.stars}">★</c:forEach><c:forEach begin="1" end="${5 - h.stars}">☆</c:forEach>
                                </p>
                                <span class="date">${h.created_at}</span>
                            </div>
                        </div>
                    </div>
                    <div class="cmt-user">${h.content}</div>
                </div>
            </c:forEach>
        </div>
    </div>
</section>
<!--</div>-->
<%--footer--%>
<jsp:include page="/common/footer.jsp"/>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Đánh giá sản phẩm</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ProductReview.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
</head>
<body>
<!--header-->
<jsp:include page="/common/header.jsp"/>
<!--body-->
<section class="hero" id="home">
    <div class="hero-content">
        <h2>Đánh Giá Máy Ảnh</h2>
        <p>Khám phá những đánh giá chi tiết và chân thực về các dòng máy ảnh phổ biến trên thị trường</p>
        <a href="#products" class="btn">Xem Sản Phẩm</a>
    </div>
</section>

<div class="container">
    <section id="submit-review">
        <h2 class="section-title">Gửi Đánh Giá Của Bạn</h2>
        <div class="review-form">
            <form action="reviews" method="post">

                <div class="form-group">
                    <label for="productId">Chọn sản phẩm:</label>
                    <select id="productId" name="productId" required>
                        <option value="">-- Chọn máy ảnh --</option>
                        <c:forEach var="p" items="${products}">
                            <option value="${p.id}">${p.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="name">Tên của bạn:</label>
                    <input type="text" id="name" name="name" required>
                </div>

                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" required>
                </div>

                <div class="form-group">
                    <label>Đánh giá:</label>
                    <div class="rating-input">
                        <input type="radio" id="star5" name="rating" value="5" required>
                        <label for="star5">★</label>
                        <input type="radio" id="star4" name="rating" value="4">
                        <label for="star4">★</label>
                        <input type="radio" id="star3" name="rating" value="3">
                        <label for="star3">★</label>
                        <input type="radio" id="star2" name="rating" value="2">
                        <label for="star2">★</label>
                        <input type="radio" id="star1" name="rating" value="1">
                        <label for="star1">★</label>
                    </div>
                </div>

                <div class="form-group">
                    <label for="content">Nội dung đánh giá:</label>
                    <textarea id="content" name="content"
                              placeholder="Chia sẻ trải nghiệm của bạn với sản phẩm này..."
                              required></textarea>
                </div>

                <button type="submit" class="btn">Gửi Đánh Giá</button>
            </form>
        </div>
    </section>

    <section id="reviews" class="reviews">
        <h2 class="section-title">Đánh Giá Gần Đây</h2>

        <c:forEach var="r" items="${reviews}">
            <div class="review-card">
                <div class="review-header">
                    <div>
                        <span class="reviewer-name">${r.name}</span>
                        <div class="review-rating">
                            <c:forEach begin="1" end="${r.rating}">★</c:forEach>
                        </div>
                    </div>
                    <span class="review-date">${r.createdAt}</span>
                </div>
                <div class="review-content">
                    <p>${r.content}</p>
                </div>
            </div>
        </c:forEach>
    </section>
</div>
<%--chưa gọi ra được sản phẩm để đánh giá--%>
<!--footer-->
<jsp:include page="/common/footer.jsp"/>
</body
</html>
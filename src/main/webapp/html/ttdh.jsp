<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đặt hàng - GROUP11</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ttdh.css">
    <link rel="stylesheet" href="../css/header.css">
    <link rel="stylesheet" href="../css/footer.css">
</head>
<body>
<jsp:include page="/common/header.jsp"/>

<main class="checkout-container">

    <form id="checkoutForm" action="${pageContext.request.contextPath}/CheckoutServlet" method="POST">

        <section class="checkout-block">
            <div class="page-title">
                <h1><i class="fa-solid fa-bag-shopping"></i> Thanh toán đơn hàng</h1>
                <p>Vui lòng điền thông tin giao hàng để đặt hàng</p>
            </div>
            <h2><i class="fa-solid fa-user"></i> Thông tin giao hàng</h2>

            <div class="form-group">
                <label>Họ và tên</label>
                <input type="text" name="customerName" placeholder="Nhập họ tên đầy đủ" required>
            </div>
            <div class="form-group">
                <label>Số điện thoại</label>
                <input type="tel" name="customerPhone" placeholder="VD: 0901234567" required pattern="[0-9]{10}">
            </div>
            <div class="form-group">
                <label>Email</label>
                <input type="email" name="customerEmail" placeholder="example@gmail.com" required>
            </div>
            <div class="form-group">
                <label>Địa chỉ nhận hàng</label>
                <textarea name="customerAddress" rows="3" placeholder="Nhập địa chỉ chi tiết" required></textarea>
            </div>
        </section>

        <section class="checkout-block">
            <h2><i class="fa-solid fa-box"></i> Đơn hàng của bạn</h2>

            <c:forEach var="item" items="${sessionScope.cart.items}">
                <div class="order-item">
                    <img src="${item.product.img}" alt="${item.product.productName}">
                    <div class="order-info">
                        <h4>${item.product.productName}</h4>
                        <p>Số lượng: <b>${item.quantity}</b></p>
                    </div>
                    <div class="price">
                        <fmt:formatNumber value="${item.price}" pattern="#,##0đ"/>
                    </div>
                </div>
            </c:forEach>

            <div class="total-section">
                <p>Tạm tính: <b><fmt:formatNumber value="${sessionScope.cart.total}" pattern="#,##0đ"/></b></p>
                <p>Phí vận chuyển: <b>30.000đ</b></p>
                <h3>Tổng cộng: <fmt:formatNumber value="${sessionScope.cart.total + 30000}" pattern="#,##0đ"/></h3>
            </div>
        </section>

        <section class="checkout-block">
            <div class="payment-method">
                <h2><i class="fa-solid fa-credit-card"></i> Phương thức thanh toán</h2>
                <label><input type="radio" name="paymentMethod" value="cod" checked> Thanh toán khi nhận hàng (COD)</label>
                <label><input type="radio" name="paymentMethod" value="bank"> Chuyển khoản ngân hàng</label>
                <label><input type="radio" name="paymentMethod" value="momo"> Thanh toán qua ví Momo</label>
            </div>

            <button type="submit" id="confirmOrder"><i class="fa-solid fa-check-double"></i> ĐẶT HÀNG</button>
        </section>

    </form>
</main>


<jsp:include page="/common/footer.jsp"/>
</body>
</html>
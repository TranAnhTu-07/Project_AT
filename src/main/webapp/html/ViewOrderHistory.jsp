<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="info-value">${sessionScope.auth.name}</div>
<div class="info-value">${sessionScope.auth.mail}</div>
<div class="info-value">${sessionScope.auth.phone}</div>
<div class="info-value">${sessionScope.auth.address}</div>
<div class="info-value">${sessionScope.auth.registrationDate}</div>
<div class="info-value">${sessionScope.auth.sex}</div>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Lịch sử đơn hàng</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
    <link rel="stylesheet" href="../css/footer.css">
    <link rel="stylesheet" href="../css/header.css">
    <link rel="stylesheet" href="../css/ViewOrderHistory.css">
</head>
<body>
<!--header-->
<jsp:include page="/common/header.jsp"/>
<!--body-->
<c:forEach var="o" items="${orders}">
    <div class="orders-item">

        <div class="orders-header">
            <div class="orders-id">Đơn hàng #${o.orderCode}</div>
            <div class="orders-date">${o.orderDate}</div>
            <div class="orders-status
                ${o.status eq 'Delivered' ? 'status-delivered' : 'status-cancelled'}">
                    ${o.status}
            </div>
        </div>

        <div class="orders-details">
            <c:forEach var="d" items="${o.orderDetails}">
                <div class="product-item">
                    <div class="product-name">${d.productName}</div>
                    <div class="product-price">${d.price} ₫</div>
                    <div class="product-quantity">x${d.quantity}</div>
                </div>
            </c:forEach>

            <div class="orders-total">
                Tổng cộng: ${o.totalAmount} ₫
            </div>
        </div>
    </div>
</c:forEach>
<!--footer-->
<jsp:include page="/common/footer.jsp"/>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <title>Chi tiết đơn hàng</title>
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/footer.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/order-detail.css">
</head>
<body>

<jsp:include page="/common/header.jsp"/>

<div class="detail-container">

    <h1 class="detail-title">Chi tiết đơn hàng <span>#DH${order.orderId}</span></h1>

    <div class="detail-grid">

        <div class="info-card">
            <h2 class="card-title"><i class="fa-solid fa-user"></i> Thông tin người nhận</h2>

            <div class="info-row">
                <span class="info-label">Họ tên</span>
                <span class="info-value">${order.customerName}</span>
            </div>

            <div class="info-row">
                <span class="info-label">SĐT</span>
                <span class="info-value">${order.phoneNumber}</span>
            </div>

            <div class="info-row">
                <span class="info-label">Địa chỉ</span>
                <span class="info-value">${order.shippingAddress}</span>
            </div>
        </div>

        <div class="info-card">
            <h2 class="card-title"><i class="fa-solid fa-box"></i> Thông tin đơn hàng</h2>

            <div class="info-row">
                <span class="info-label">Ngày đặt</span>
                <span class="info-value">
                    <fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy"/>
                </span>
            </div>

            <div class="info-row">
                <span class="info-label">Tổng tiền</span>
                <span class="info-value total-amount">
                    <fmt:formatNumber value="${order.totalAmount}" type="number" groupingUsed="true"/> ₫
                </span>
            </div>

            <div class="info-row">
                <span class="info-label">Trạng thái ký số</span>
                <span class="info-value">
                    <c:choose>
                        <c:when test="${order.signatureStatus == 1}">
                            <span class="badge badge-signed"><i class="fa-solid fa-check"></i> Đã ký</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge badge-unsigned"><i class="fa-solid fa-xmark"></i> Chưa ký</span>
                        </c:otherwise>
                    </c:choose>
                </span>
            </div>
        </div>

    </div>

    <div class="product-card">
        <h2 class="card-title"><i class="fa-solid fa-camera"></i> Sản phẩm trong đơn</h2>

        <table class="product-table">
            <thead>
            <tr>
                <th>Sản phẩm</th>
                <th>Số lượng</th>
                <th>Giá</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${details}" var="d">
                <tr>
                    <td>${d.product.productName}</td>
                    <td class="qty-cell">${d.quantity}</td>
                    <td class="price-cell">
                        <fmt:formatNumber value="${d.price}" type="number" groupingUsed="true"/> ₫
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="action-buttons">
        <button id="btnCancelOrder" class="btn-action btn-cancel" type="button">
            <i class="fa-solid fa-circle-xmark"></i> Hủy đơn hàng
        </button>

        <button id="btnSignOrder" class="btn-action btn-sign" type="button">
            <i class="fa-solid fa-signature"></i> Ký lại đơn hàng
        </button>
    </div>

</div>

<script>
    document.getElementById("btnSignOrder").addEventListener("click", function () {
        window.location.href = "${pageContext.request.contextPath}/sign-order?orderId=${order.orderId}";
    });

    document.getElementById("btnCancelOrder").addEventListener("click", function () {
        // TODO: gọi API/Servlet hủy đơn hàng tại đây, ví dụ:
        // fetch("${pageContext.request.contextPath}/cancel-order?id=${order.orderId}", { method: "POST" })
        console.log("Bấm Hủy đơn hàng - orderId = ${order.orderId}");
    });
</script>
<jsp:include page="/common/footer.jsp"/>
</body>
</html>

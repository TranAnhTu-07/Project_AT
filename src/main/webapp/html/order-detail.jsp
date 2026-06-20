<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi tiet don hang</title>
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/footer.css">
    <link rel="stylesheet" href="css/Product.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
    <script src="js/search.js"></script>
</head>
<jsp:include page="/common/header.jsp"/>
</head>
<body>
<h1>

    Chi tiết đơn hàng

    #DH${order.orderId}

</h1>
<div class="receiver-info">

    <h2>Thông tin người nhận</h2>

    <p>
        <b>Họ tên:</b>
        ${order.customerName}
    </p>

    <p>
        <b>SĐT:</b>
        ${order.phoneNumber}
    </p>

    <p>
        <b>Địa chỉ:</b>
        ${order.shippingAddress}
    </p>

</div>
<%--thông tin đơn hàng--%>
<div class="order-info">

    <p>

        <b>Ngày đặt:</b>

        ${order.orderDate}

    </p>

    <p>

        <b>Tổng tiền:</b>

        ${order.totalAmount}

    </p>

</div>
<%--trạng thái chữ ký--%>
<p>

    <b>Trạng thái ký số:</b>

    <c:choose>

        <c:when test="${order.signatureStatus==1}">

            <span class="signed">

                Đã ký

            </span>

        </c:when>

        <c:otherwise>

            <span class="unsigned">

                Chưa ký

            </span>

        </c:otherwise>

    </c:choose>

</p>
<%--danh sách sản phẩm--%>
<table>

    <tr>
        <th>Sản phẩm</th>
        <th>SL</th>
        <th>Giá</th>
    </tr>

    <c:forEach items="${details}" var="d">

        <tr>

            <td>
                    ${d.product.productName}
            </td>

            <td>
                    ${d.quantity}
            </td>

            <td>
                    ${d.price}
            </td>

        </tr>

    </c:forEach>

</table>

</body>
</html>

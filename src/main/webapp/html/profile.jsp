<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="vi">
<head>
    <title>Hồ sơ của tôi</title>
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">
</head>
<body>
<%--header--%>
<jsp:include page="/common/header.jsp"/>
<%--body--%>
<div class="container">

    <div class="box info-col">
        <h3 style="border-bottom: 2px solid #007bff; padding-bottom: 10px;">Hồ sơ cá nhân</h3>
        <p><strong>Họ tên:</strong> ${user.fullName != null ? user.fullName : 'Chưa cập nhật'}</p>
        <p><strong>Email:</strong> ${user.email}</p>
        <p><strong>SĐT:</strong> ${user.phone != null ? user.phone : 'Chưa thêm SĐT'}</p>
        <p><strong>Ngày sinh:</strong> ${user.date_of_birth}</p>
        <button>Cập nhật thông tin</button>
    </div>

    <div class="box order-col">
        <h3 style="border-bottom: 2px solid #28a745; padding-bottom: 10px;">Đơn hàng của tôi</h3>

        <c:if test="${empty orders}">
            <p>Bạn chưa mua gì cả. <a href="${pageContext.request.contextPath}/ListProduct">Đi mua sắm ngay!</a></p>
        </c:if>

        <c:if test="${not empty orders}">
            <table>
                <thead>
                <tr>
                    <th>Mã đơn</th>
                    <th>Ngày đặt</th>
                    <th>Tổng tiền</th>
                    <th>Trạng thái</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${orders}" var="o">
                    <tr>
                        <td>#${o.orderId}</td>
                        <td>${o.orderDate}</td>
                        <td style="color: red; font-weight: bold;">
                            <fmt:formatNumber value="${o.totalAmount}" type="currency" currencySymbol="₫"/>
                        </td>
                        <td>
                                    <span style="color: ${o.status == 'Đã giao' ? 'green' : 'orange'}">
                                            ${o.status}
                                    </span>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</div>
<%--footer--%>
<jsp:include page="/common/footer.jsp"/>
</body>
</html>
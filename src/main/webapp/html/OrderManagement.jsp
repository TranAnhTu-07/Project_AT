<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Quản lý Đơn Hàng</title>
    <link rel="stylesheet" href="../css/OrderManagement.css">
    <link rel="stylesheet" href="admin css2.css">
    <link rel="stylesheet" href="../css/quanlyuser.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
</head>
<body>
<div class="sidebar">
    <div class="sidebar-header">
        <h1><span>Admin Panel</span></h1>
    </div>
    <div class="menu">
        <a href="${pageContext.request.contextPath}/admin/users" class="menu-item">
            <span>Quản Lý User</span>
        </a>
        <a href="/Project/ThongKeDoanhThu/doanhthu.html" class="menu-item">
            <span>Thống kê</span>
        </a>
        <a href="${pageContext.request.contextPath}/kho" class="menu-item">
            <span>Quản Lý Kho</span>
        </a>
        <a href="/Project/productmanagement/productManagement.html" class="menu-item">
            <span>Quản Lý Sản phẩm</span>
        </a>
        <a href="${pageContext.request.contextPath}/OrderManagement" class="menu-item active">
            <span>Quản Lý Đơn Hàng</span>
        </a>
        <a href="${pageContext.request.contextPath}/Logout" class="menu-item">
            <span>Đăng Xuất</span>
        </a>
    </div>
</div>

<div class="main-content" style="margin-left: 260px; padding: 20px;">
    <h2>Danh sách đơn hàng</h2>

    <table class="order-table">
        <thead>
        <tr>
            <th>Mã Đơn</th>
            <th>Khách Hàng</th>
            <th>Ngày Đặt</th>
            <th>Tổng Tiền</th>
            <th>Trạng Thái</th>
            <th>Xác Thực Chữ Ký</th>
            <th>Hành Động</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="o" items="${orders}">
            <tr>
                <td class="orders-id">#ORD-${o.orderId}</td>
                <td>
                    <div class="customer-info">
                        <span class="customer-name"><strong>${o.fullName}</strong></span><br>
                        <span class="customer-email" style="font-size: 0.85em; color: #666;">${o.email}</span>
                    </div>
                </td>
                <td>${o.orderDate}</td>
                <td>
                    <strong><fmt:formatNumber value="${o.totalAmount}" type="number"/>₫</strong>
                </td>
                <td>
                        <span class="status status-${o.statusName}">
                                ${o.statusName}
                        </span>
                </td>

                <td>
                    <c:choose>
                        <c:when test="${o.signatureStatus == 'VALID'}">
                            <span class="badge-sig sig-valid"><i class="fa-solid fa-circle-check"></i> Hợp lệ</span>
                        </c:when>
                        <c:when test="${o.signatureStatus == 'INVALID'}">
                            <span class="badge-sig sig-invalid"><i class="fa-solid fa-triangle-exclamation"></i> Dữ liệu bị sửa!</span>
                        </c:when>
                        <c:when test="${o.signatureStatus == 'KEY_REVOKED'}">
                            <span class="badge-sig sig-revoked"><i class="fa-solid fa-user-shield"></i> Khóa đã hủy</span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge-sig sig-none"><i class="fa-solid fa-pen-nib"></i> Chưa ký số</span>
                        </c:otherwise>
                    </c:choose>
                </td>

                <td>
                    <div class="action-buttons">
                        <a href="OrderDetailAdmin?id=${o.orderId}" class="btn btn-warning btn-sm">Chi tiết</a>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
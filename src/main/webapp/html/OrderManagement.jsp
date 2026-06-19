<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Quản lý Đơn Hàng</title>
    <link rel="stylesheet" href="../css/OrderManagement.css">

</head>
<body>
<!-- Sidebar -->
<div class="sidebar">
    <div class="sidebar-header">
        <h1><span>Admin Panel</span></h1>
    </div>
    <div class="menu">

        <a href="/Project/Quan%20Ly%20User/quanlyuser.html" class="menu-item">
            <span>Quản Lý User</span>
        </a>
        <a href="/Project/ThongKeDoanhThu/doanhthu.html" class="menu-item">
            <span>Thống kê</span>
        </a>
        <a href="/Project/Warehouse/Warehouse.html" class="menu-item">
            <span>Quản Lý Kho</span>
        </a>
        <a href="/Project/productmanagement/productManagement.html" class="menu-item">
            <span>Quản Lý Sản Phẩm</span>
        </a>
        <a href="/Project/Order%20management/OrderManagement.html" class="menu-item active">
            <span>Quản Lý Đơn Hàng</span>
        </a>
        <a href="/Project/index.html" class="menu-item">
            <span>Đăng Xuất</span>
        </a>
    </div>
</div>
<!--Main content-->
<c:forEach var="o" items="${orders}">
    <tr>
        <td class="orders-id">#ORD-${o.orderId}</td>
        <td>
            <div class="customer-info">
                <span class="customer-name">${o.fullName}</span>
                <span class="customer-email">${o.email}</span>
            </div>
        </td>
        <td>${o.orderDate}</td>
        <td>
            <fmt:formatNumber value="${o.totalAmount}" type="number"/>₫
        </td>
        <td>
            <span class="status status-${o.statusName}">
                    ${o.statusName}
            </span>
        </td>
        <td>
            <div class="action-buttons">
                <a href="#" class="btn btn-warning btn-sm">Chi tiết</a>
            </div>
        </td>
    </tr>
</c:forEach>
</body>
</html>
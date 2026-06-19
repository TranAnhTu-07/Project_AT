
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin - Quản Lý User</title>
    <link rel="stylesheet" href="admin css2.css">
    <link rel="stylesheet" href="../css/quanlyuser.css">
</head>
<body>
<!-- Sidebar -->
<div class="sidebar">
    <div class="sidebar-header">
        <h1><span>Admin Panel</span></h1>
    </div>
    <div class="menu">

        <a href="${pageContext.request.contextPath}/admin/users" class="menu-item active">
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
        <a href="/Project/Order%20management/OrderManagement.html" class="menu-item">
            <span>Quản Lý Đơn Hàng</span>
        </a>
        <a href="${pageContext.request.contextPath}/Logout" class="menu-item">
            <span>Đăng Xuất</span>
        </a>
    </div>
</div>

<!-- Main Content -->
<div class="main-content">
    <div class="stats">
        <div class="stat-card">
            <h3>${totalUsers}</h3>
            <p>Tổng số người dùng</p>
        </div>
        <div class="stat-card">
            <h3>${activeUsers}</h3>
            <p>Đang hoạt động</p>
        </div>
        <div class="stat-card">
            <h3>${lockedUsers}</h3>
            <p>Bị khóa</p>
        </div>
    </div>

    <div class="controls">
        <div class="search-filter-group">
            <form action="users" method="get" style="display: flex; align-items: center; gap: 10px;">

                <div class="search-box" style="position: relative; display: flex; align-items: center;">
                    <input type="text" name="search" value="${param.search}"
                           placeholder="Tìm theo tên, email..."
                           style="padding-right: 30px; width: 300px;">

                    <c:if test="${not empty param.search}">
                        <a href="users"
                           title="Xóa tìm kiếm"
                           style="position: absolute; right: 10px; color: #888; text-decoration: none; font-weight: bold; cursor: pointer;">
                            ✕
                        </a>
                    </c:if>
                </div>

                <button type="submit" class="filter-btn">
                    Tìm kiếm
                </button>

            </form>
        </div>

        <button class="btn-add">Thêm người dùng</button>
    </div>

    <div class="section-header">
        <h2>Danh sách người dùng</h2>
    </div>

    <div class="table-container">
        <table>
            <thead>
            <tr>
                <th>Người dùng</th>
                <th>Email</th>
                <th>Vai trò</th>
                <th>Trạng thái</th>
                <th>Hành động</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${listUsers}" var="u">
                <tr> <td>
                    <div class="user-cell">
                        <span class="user-name">${u.fullName}</span>
                        <span class="user-id">ID: #${u.userId}</span>
                    </div>
                </td>

                    <td>${u.email}</td>

                    <td>
                        <c:if test="${u.roleId == 1}"><span class="badge badge-admin">Admin</span></c:if>
                        <c:if test="${u.roleId != 1}"><span class="badge badge-customer">Khách hàng</span></c:if>
                    </td>

                    <td>
                        <c:if test="${u.status == 1}"><span style="color: green; font-weight: bold;">Hoạt động</span></c:if>
                        <c:if test="${u.status == 0}"><span style="color: red; font-weight: bold;">Bị khóa</span></c:if>
                    </td>

                    <td>
                        <div class="table-actions">
                            <c:if test="${u.status == 1}">
                                <a href="users-status?id=${u.userId}&status=0"
                                   class="action-link lock"
                                   style="color: red; font-weight: bold;"
                                   onclick="return confirm('Bạn có chắc muốn khóa tài khoản này không?')">
                                    Khóa
                                </a>
                            </c:if>

                            <c:if test="${u.status == 0}">
                                <a href="users-status?id=${u.userId}&status=1"
                                   class="action-link unlock"
                                   style="color: green; font-weight: bold;"
                                   onclick="return confirm('Mở khóa cho tài khoản này nhé?')">
                                    Mở khóa
                                </a>
                            </c:if>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
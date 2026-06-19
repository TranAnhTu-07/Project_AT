<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản Lý Tồn Kho - Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Warehouse.css">
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
        <a href="/Project/Warehouse/Warehouse.html" class="menu-item active">
            <span>Quản Lý Kho</span>
        </a>
        <a href="/Project/productmanagement/productManagement.html" class="menu-item">
            <span>Quản Lý Sản phẩm</span>
        </a>
        <a href="/Project/Order%20management/OrderManagement.html" class="menu-item">
            <span>Quản Lý Đơn Hàng</span>
        </a>
        <a href="/Project/index.html" class="menu-item">
            <span>Đăng Xuất</span>
        </a>
    </div>
</div>

<div class="container">

    <div class="stats-container">
        <div class="stat-card success">
            <h3>Tổng Sản Phẩm</h3>
            <div class="number">${stats.totalProducts}</div>
        </div>
        <div class="stat-card">
            <h3>Tổng Giá Trị Kho</h3>
            <div class="number">
                <fmt:formatNumber value="${stats.totalValue}" groupingUsed="true"/> đ
            </div>

        </div>
        <div class="stat-card warning">
            <h3>Sắp Hết Hàng</h3>
            <div class="number">${stats.lowStock}</div>
        </div>
        <div class="stat-card danger">
            <h3>Hết Hàng</h3>
            <div class="number">${stats.outOfStock}</div>
        </div>
    </div>

    <div class="filters">
        <h2>🔍 Bộ Lọc</h2>
        <form class="filter-group" method="GET" action="${pageContext.request.contextPath}/kho">
            <div class="filter-item">
                <label for="category">Danh Mục</label>
                <select id="category" name="category">
                    <option value="">Tất cả danh mục</option>
                    <option value="electronics">Điện tử</option>
                    <option value="fashion">Thời trang</option>
                    <option value="food">Thực phẩm</option>
                    <option value="books">Sách</option>
                    <option value="toys">Đồ chơi</option>
                </select>
            </div>
            <div class="filter-item">
                <label for="status">Trạng Thái Kho</label>
                <select id="status" name="status">
                    <option value="">Tất cả trạng thái</option>
                    <option value="high">Còn nhiều (>100)</option>
                    <option value="medium">Trung bình (50-100)</option>
                    <option value="low">Sắp hết (<50)</option>
                    <option value="out">Hết hàng</option>
                </select>
            </div>
            <div class="filter-item">
                <label for="search">Tìm Kiếm</label>
                <input type="text" id="search" name="search" placeholder="Tên sản phẩm, mã SP...">
            </div>
            <div class="filter-item">
                <label for="sort">Sắp Xếp</label>
                <select id="sort" name="sort">
                    <option value="name">Tên A-Z</option>
                    <option value="stock_asc">Tồn kho tăng dần</option>
                    <option value="stock_desc">Tồn kho giảm dần</option>
                    <option value="price">Giá cao đến thấp</option>
                </select>
            </div>
        </form>
    </div>
<%--///////--%>
    <div class="content">

        <table>
            <thead>
            <tr>
                <th>Mã SP</th>
                <th>Sản Phẩm</th>
                <th>Mã Danh Mục</th>
                <th>Số Lượng</th>
                <th>Trạng Thái</th>
                <th>Giá Bán</th>
                <th>Thao Tác</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="p" items="${onSale}">
                <tr data-id="${p.productID}">

                    <td>${p.productID}</td>

                    <td class="product">
                        <img src="${p.img}">
<%--                        <span class="view">${p.productName}</span>--%>
                        <input class="edit" type="text" name="name" value="${p.productName}">
                    </td>

                    <td>${p.categoryID}</td>

                    <td>${p.quantity}</td>

                    <td class="status ${p.quantity == 0 ? 'out' : p.quantity < 10 ? 'low' : 'selling'}">
                        <c:choose>
                            <c:when test="${p.quantity == 0}">Hết hàng</c:when>
                            <c:when test="${p.quantity < 10}">Sắp hết</c:when>
                            <c:otherwise>Đang bán</c:otherwise>
                        </c:choose>
                    </td>

                    <td>
                        <fmt:formatNumber value="${p.newPrice}" groupingUsed="true"/> đ
                    </td>

                    <td>
                        <button>xóa</button>
                        <button>sửa</button>

                    </td>
                </tr>
            </c:forEach>

            </tbody>
        </table>
    </div>

    <div class="export-section">
        <a href="#" class="btn btn-primary">📥 Xuất Excel</a>
        <a href="#" class="btn btn-primary">📄 Xuất PDF</a>
        <a href="#" class="btn btn-warning">📊 Báo Cáo Tồn Kho</a>
    </div>
</div>
</body>
</html>
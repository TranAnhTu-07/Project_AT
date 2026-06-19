<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thống kê doanh thu</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <link rel="stylesheet" href="../css/doanhthu.css">
</head>

<body>

<div class="sidebar">
    <div class="sidebar-header">
        <h1><span>Admin Panel</span></h1>
    </div>
    <div class="menu">
        <a href="/Project/Quan%20Ly%20User/quanlyuser.html" class="menu-item">
            <span>Quản Lý User</span>
        </a>
        <a href="/Project/ThongKeDoanhThu/doanhthu.html" class="menu-item active">
            <span>Thống kê</span>
        </a>
        <a href="/Project/Warehouse/Warehouse.html" class="menu-item">
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
    <h1 class="page-title">📊 Thống kê doanh thu bán hàng</h1>

    <div class="filter-box">
        <input type="date" id="dateFilter">

    </div>

    <div class="stats-cards">
        <div class="card">
            <h2>Doanh thu hôm nay</h2>
            <p id="todayRevenue">40.000.000₫</p>
        </div>

        <div class="card">
            <h2>Tổng đơn hàng</h2>
            <p id="totalOrders">100</p>
        </div>

        <div class="card">
            <h2>Sản phẩm đã bán</h2>
            <p id="totalProducts">200</p>
        </div>
    </div>
    <div id="chartBox">
        <h2 class="chart-title">Biểu đồ doanh thu 12 tháng</h2>
        <div class="css-chart">
            <div class="bar" style="height: 40%;"><span>1</span></div>
            <div class="bar" style="height: 55%;"><span>2</span></div>
            <div class="bar" style="height: 30%;"><span>3</span></div>
            <div class="bar" style="height: 65%;"><span>4</span></div>
            <div class="bar" style="height: 70%;"><span>5</span></div>
            <div class="bar" style="height: 85%;"><span>6</span></div>
            <div class="bar" style="height: 90%;"><span>7</span></div>
            <div class="bar" style="height: 60%;"><span>8</span></div>
            <div class="bar" style="height: 75%;"><span>9</span></div>
            <div class="bar" style="height: 80%;"><span>10</span></div>
            <div class="bar" style="height: 95%;"><span>11</span></div>
            <div class="bar" style="height: 100%;"><span>12</span></div>

        </div>

    </div>


    <div class="table-box">
        <h2 class="table-title">📄 Chi tiết đơn hàng</h2>
        <table>
            <thead>
            <tr>
                <th>Mã đơn</th>
                <th>Khách hàng</th>
                <th>Ngày</th>
                <th>Số lượng</th>
                <th>Tổng tiền</th>
            </tr>
            </thead>

            <tbody id="orderTable">

            <tr>
                <td>DH001</td>
                <td>Nguyễn Văn A</td>
                <td>10-11-2025</td>
                <td>3</td>
                <td>4.500.000₫</td>
            </tr>

            <tr>
                <td>DH002</td>
                <td>Trần Thị B</td>
                <td>10-11-2025</td>
                <td>1</td>
                <td>1.250.000₫</td>
            </tr>

            <tr>
                <td>DH003</td>
                <td>Phạm Văn Hải</td>
                <td>10-11-2025</td>
                <td>2</td>
                <td>3.200.000₫</td>
            </tr>

            <tr>
                <td>DH004</td>
                <td>Lê Thị Minh</td>
                <td>10-11-2025</td>
                <td>5</td>
                <td>12.750.000₫</td>
            </tr>

            <tr>
                <td>DH005</td>
                <td>Hoàng Quốc Bảo</td>
                <td>10-11-2025</td>
                <td>1</td>
                <td>980.000₫</td>
            </tr>

            <tr>
                <td>DH006</td>
                <td>Võ Nhật Tân</td>
                <td>10-11-2025</td>
                <td>4</td>
                <td>8.450.000₫</td>
            </tr>

            <tr>
                <td>DH007</td>
                <td>Đinh Mỹ Tiên</td>
                <td>10-11-2025</td>
                <td>2</td>
                <td>5.600.000₫</td>
            </tr>

            <tr>
                <td>DH008</td>
                <td>Nguyễn Thanh Long</td>
                <td>10-11-2025</td>
                <td>1</td>
                <td>1.400.000₫</td>
            </tr>

            <tr>
                <td>DH009</td>
                <td>Trương Đình Khôi</td>
                <td>10-11-2025</td>
                <td>3</td>
                <td>6.750.000₫</td>
            </tr>

            <tr>
                <td>DH010</td>
                <td>Phạm Gia Hân</td>
                <td>10-11-2025</td>
                <td>2</td>
                <td>4.800.000₫</td>
            </tr>

            </tbody>
        </table>
    </div>
</div>

</body>
</html>

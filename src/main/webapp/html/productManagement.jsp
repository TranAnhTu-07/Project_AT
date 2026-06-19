<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Quản lý sản phẩm</title>
    <link rel="stylesheet" href="../css/productManagement.css">
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
        <a href="/Project/productmanagement/productManagement.html" class="menu-item active">
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
<!--Main content-->
<div class="main-content">
    <!-- Thống kê -->
    <div class="stats-container">
        <div class="stat-card success">
            <div class="stat-icon icon-primary">
                📷
            </div>
            <div class="stat-info">
                <h3>142</h3>
                <p>Tổng sản phẩm</p>
            </div>
        </div>
        <div class="stat-card">
            <div class="stat-icon icon-success">
                📦
            </div>
            <div class="stat-info">
                <h3>24</h3>
                <p>Sản phẩm mới</p>
            </div>
        </div>
        <div class="stat-card warning">
            <div class="stat-icon icon-warning">
                ⚠️
            </div>
            <div class="stat-info">
                <h3>8</h3>
                <p>Sắp hết hàng</p>
            </div>
        </div>
        <div class="stat-card danger">
            <div class="stat-icon icon-danger">
                🔄
            </div>
            <div class="stat-info">
                <h3>3</h3>
                <p>Đang chờ nhập</p>
            </div>
        </div>
    </div>

    <!-- Form thêm sản phẩm -->
    <div class="form-container">
        <h2 style="margin-bottom: 20px;">Thêm Sản Phẩm Mới</h2>
        <form>
            <div class="form-row">
                <div class="form-group">
                    <label for="productName">Tên sản phẩm</label>
                    <input type="text" id="productName" class="form-control" placeholder="Nhập tên sản phẩm">
                </div>
                <div class="form-group">
                    <label for="productBrand">Thương hiệu</label>
                    <select id="productBrand" class="form-control">
                        <option value="">Chọn thương hiệu</option>
                        <option value="canon">Canon</option>
                        <option value="nikon">Nikon</option>
                        <option value="sony">Sony</option>
                        <option value="fujifilm">Fujifilm</option>
                    </select>
                </div>
            </div>
            <div class="form-row">
                <div class="form-group">
                    <label for="productPrice">Giá bán</label>
                    <input type="number" id="productPrice" class="form-control" placeholder="Nhập giá bán">
                </div>
                <div class="form-group">
                    <label for="productStock">Số lượng tồn kho</label>
                    <input type="number" id="productStock" class="form-control" placeholder="Nhập số lượng">
                </div>
            </div>
            <div class="form-group">
                <label for="productDescription">Mô tả sản phẩm</label>
                <textarea id="productDescription" class="form-control" rows="3"
                          placeholder="Nhập mô tả sản phẩm"></textarea>
            </div>
            <div class="form-group">
                <label for="productImage">Hình ảnh sản phẩm</label>
                <input type="file" id="productImage" class="form-control">
            </div>
            <button type="submit" class="btn btn-success">Thêm Sản Phẩm</button>
        </form>
    </div>

    <!-- Bảng sản phẩm -->
    <div class="card">
        <div class="card-header">
            <h2>Danh Sách Sản Phẩm</h2>
            <div>
                <input type="text" class="form-control" placeholder="Tìm kiếm sản phẩm..."
                       style="width: 250px; display: inline-block;">
            </div>
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <table>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Hình ảnh</th>
                        <th>Tên sản phẩm</th>
                        <th>Thương hiệu</th>
                        <th>Giá</th>
                        <th>Tồn kho</th>
                        <th>Trạng thái</th>
                        <th>Thao tác</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>CAM001</td>
                        <td><img src="https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/m/a/may-anh-canon-eos-r50_4_.png" alt="Canon EOS R5"
                                 class="product-image"></td>
                        <td>Canon EOS R5</td>
                        <td>Canon</td>
                        <td>75.990.000₫</td>
                        <td>15</td>
                        <td><span class="status status-active">Đang bán</span></td>
                        <td>
                            <div class="action-buttons">
                                <a href="#" class="btn btn-primary btn-sm">Sửa</a>
                                <a href="#" class="btn btn-danger btn-sm">Xóa</a>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>CAM002</td>
                        <td><img src="https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/m/a/may-anh-canon-eos-r50_4_.png" alt="Nikon Z7 II"
                                 class="product-image"></td>
                        <td>Nikon Z7 II</td>
                        <td>Nikon</td>
                        <td>62.990.000₫</td>
                        <td>8</td>
                        <td><span class="status status-active">Đang bán</span></td>
                        <td>
                            <div class="action-buttons">
                                <a href="#" class="btn btn-primary btn-sm">Sửa</a>
                                <a href="#" class="btn btn-danger btn-sm">Xóa</a>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>CAM003</td>
                        <td><img src="https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/m/a/may-anh-canon-eos-r50_4_.png" alt="Sony A7 IV"
                                 class="product-image"></td>
                        <td>Sony A7 IV</td>
                        <td>Sony</td>
                        <td>52.990.000₫</td>
                        <td>22</td>
                        <td><span class="status status-active">Đang bán</span></td>
                        <td>
                            <div class="action-buttons">
                                <a href="#" class="btn btn-primary btn-sm">Sửa</a>
                                <a href="#" class="btn btn-danger btn-sm">Xóa</a>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>CAM004</td>
                        <td><img src="https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/m/a/may-anh-canon-eos-r50_4_.png" alt="Fujifilm X-T4"
                                 class="product-image"></td>
                        <td>Fujifilm X-T4</td>
                        <td>Fujifilm</td>
                        <td>29.990.000₫</td>
                        <td>3</td>
                        <td><span class="status status-warning">Sắp hết</span></td>
                        <td>
                            <div class="action-buttons">
                                <a href="#" class="btn btn-primary btn-sm">Sửa</a>
                                <a href="#" class="btn btn-danger btn-sm">Xóa</a>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>CAM005</td>
                        <td><img src="https://cdn2.cellphones.com.vn/insecure/rs:fill:0:358/q:90/plain/https://cellphones.com.vn/media/catalog/product/m/a/may-anh-canon-eos-r50_4_.png" alt="Panasonic Lumix S5"
                                 class="product-image"></td>
                        <td>Panasonic Lumix S5</td>
                        <td>Panasonic</td>
                        <td>34.990.000₫</td>
                        <td>0</td>
                        <td><span class="status status-inactive">Hết hàng</span></td>
                        <td>
                            <div class="action-buttons">
                                <a href="#" class="btn btn-primary btn-sm">Sửa</a>
                                <a href="#" class="btn btn-danger btn-sm">Xóa</a>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>
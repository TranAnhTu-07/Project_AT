<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đặt hàng - GROUP11</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
    <link rel="stylesheet" href="../css/ttdh.css">
    <link rel="stylesheet" href="../css/header.css">
    <link rel="stylesheet" href="../css/footer.css">

</head>
<body>
<!--header-->
<jsp:include page="/common/header.jsp"/>
<!--body-->
<main class="checkout-container">
    <div class="checkout-header">
        <h1><i class="fa-solid fa-bag-shopping"></i> Thanh toán & Đặt hàng</h1>
        <p>Hoàn tất thông tin để xác nhận đơn hàng của bạn</p>
    </div>

    <div class="checkout">
        <section class="checkout-left">
            <h2><i class="fa-solid fa-user"></i> Thông tin người nhận</h2>
            <form id="checkoutForm">
                <div class="form-group">
                    <label>Họ và tên</label>
                    <input type="text" id="name" placeholder="Nhập họ tên đầy đủ" required>
                </div>
                <div class="form-group">
                    <label>Số điện thoại</label>
                    <input type="tel" id="phone" placeholder="VD: 0901234567" required pattern="[0-9]{10}">
                </div>
                <div class="form-group">
                    <label>Email</label>
                    <input type="email" id="email" placeholder="example@gmail.com" required>
                </div>
                <div class="form-group">
                    <label>Địa chỉ nhận hàng</label>
                    <textarea id="address" rows="3" placeholder="Nhập địa chỉ chi tiết của bạn" required></textarea>
                </div>
            </form>
        </section>

        <aside class="checkout-right">
            <div class="orders-summary">
                <h2><i class="fa-solid fa-box"></i> Đơn hàng của bạn</h2>
                <div class="orders-item">
                    <img src="https://mayanhvietnam.com/image-data/san-pham/24-08/24-08-01/240801170455500/avatar/638692503741569353_may-anh-canon-eos-r100-hang.jpg" alt="">
                    <div class="orders-info">
                        <h4>Máy ảnh Canon EOS R100</h4>
                        <p>Số lượng: <span>1</span></p>
                        <p class="price">11.500.000đ</p>
                    </div>
                </div>
                <div class="orders-item">
                    <img src="https://mayanhvietnam.com/image-data/san-pham/23-02/23-02-11/230211000615918/avatar/01_ong-kinh-canon-ef-50mm-f-1-8-ii.jpg" alt="">
                    <div class="orders-info">
                        <h4>Ống kính Canon 50mm f/1.8</h4>
                        <p>Số lượng: <span>1</span></p>
                        <p class="price">2.500.000đ</p>
                    </div>
                </div>

                <div class="total-section">
                    <p>Tạm tính: <span>14.000.000đ</span></p>
                    <p>Phí vận chuyển: <span>Miễn phí</span></p>
                    <h3>Tổng cộng: <span id="totalPrice">14.000.000đ</span></h3>
                </div>
            </div>

            <div class="payment-method">
                <h2><i class="fa-solid fa-credit-card"></i> Phương thức thanh toán</h2>
                <label><input type="radio" name="payment" value="cod" checked> Thanh toán khi nhận hàng (COD)</label>
                <label><input type="radio" name="payment" value="bank"> Chuyển khoản ngân hàng</label>
                <label><input type="radio" name="payment" value="momo"> Thanh toán qua ví Momo</label>
            </div>

            <button id="confirmOrder"><i class="fa-solid fa-check"></i> Xác nhận đặt hàng</button>
        </aside>
    </div>
</main>
<!--footer-->
<jsp:include page="/common/footer.jsp"/>
</body>
</html>

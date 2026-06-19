<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đặt hàng - GROUP11</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ttdh.css">
    <link rel="stylesheet" href="../css/header.css">
    <link rel="stylesheet" href="../css/footer.css">
</head>
<body>
<jsp:include page="/common/header.jsp"/>

<main class="checkout-container">
    <div class="checkout-header">
        <h1><i class="fa-solid fa-bag-shopping"></i> Thanh toán & Ký điện tử</h1>
        <p>Xác thực chữ ký số để hoàn tất đơn hàng của bạn</p>
    </div>

    <div class="checkout">
        <section class="checkout-left">
            <h2><i class="fa-solid fa-user"></i> Thông tin giao dịch</h2>

            <form id="checkoutForm" action="${pageContext.request.contextPath}/CheckoutServlet" method="POST">

                <div class="form-group">
                    <label>Họ và tên</label>
                    <input type="text" id="name" name="customerName" placeholder="Nhập họ tên đầy đủ" required>
                </div>
                <div class="form-group">
                    <label>Số điện thoại</label>
                    <input type="tel" id="phone" name="customerPhone" placeholder="VD: 0901234567" required pattern="[0-9]{10}">
                </div>
                <div class="form-group">
                    <label>Email</label>
                    <input type="email" id="email" name="customerEmail" placeholder="example@gmail.com" required>
                </div>
                <div class="form-group">
                    <label>Địa chỉ nhận hàng</label>
                    <textarea id="address" name="customerAddress" rows="3" placeholder="Nhập địa chỉ chi tiết của bạn" required></textarea>
                </div>

                <div class="signature-section">
                    <h3><i class="fa-solid fa-file-signature"></i> Xác thực Chữ ký số</h3>
                    <p style="font-size: 14px; margin-bottom: 10px; color: #555;">
                        <b>Bước 1:</b> Copy chuỗi dữ liệu đơn hàng bên dưới.<br>
                        <b>Bước 2:</b> Mở Tool Chữ ký số của bạn, dán chuỗi này vào và dùng Private Key để ký.<br>
                        <b>Bước 3:</b> Dán mã Chữ ký (Signature) trả về vào ô bên dưới để hoàn tất.
                    </p>

                    <div class="form-group">
                        <label>Chuỗi dữ liệu đơn hàng (Data String):</label>
                        <div class="copy-box">
                            <textarea id="dataString" name="dataString" rows="3" readonly>DonHang:123|TongTien:14000000|ThoiGian:<%= new java.util.Date() %></textarea>
                            <button type="button" id="btnCopy" onclick="copyData()"><i class="fa-regular fa-copy"></i> Copy</button>
                        </div>
                    </div>

                    <div class="form-group">
                        <label>Chữ ký điện tử (Signature): <span style="color:red">*</span></label>
                        <textarea name="signature" id="signature" rows="4" placeholder="Dán mã chữ ký (Base64) từ Tool của bạn vào đây..." required></textarea>
                    </div>
                </div>
            </form>
        </section>

        <aside class="checkout-right">
            <div class="order-summary">
                <h2><i class="fa-solid fa-box"></i> Đơn hàng của bạn</h2>
                <c:forEach var="item" items="${sessionScope.cart.items}">
                    <div class="order-item">
                        <img src="${item.product.img}" alt="${item.product.productName}">
                        <div class="order-info">
                            <h4>${item.product.productName}</h4>
                            <p>Số lượng: <span>${item.quantity}</span></p>
                            <p class="price"><fmt:formatNumber value="${item.price}" pattern="#,##0đ"/></p>
                        </div>
                    </div>
                </c:forEach>

                <div class="total-section">
                    <p>Tạm tính: <span><fmt:formatNumber value="${sessionScope.cart.total}" pattern="#,##0đ"/></span></p>
                    <p>Phí vận chuyển: <span>30.000đ</span></p>
                    <h3>Tổng cộng: <span id="totalPrice"><fmt:formatNumber value="${sessionScope.cart.total + 30000}" pattern="#,##0đ"/></span></h3>
                </div>
            </div>

            <div class="payment-method">
                <h2><i class="fa-solid fa-credit-card"></i> Phương thức thanh toán</h2>
                <label><input type="radio" name="paymentMethod" form="checkoutForm" value="cod" checked> Thanh toán khi nhận hàng (COD)</label>
                <label><input type="radio" name="paymentMethod" form="checkoutForm" value="bank"> Chuyển khoản ngân hàng</label>
                <label><input type="radio" name="paymentMethod" form="checkoutForm" value="momo"> Thanh toán qua ví Momo</label>
            </div>

            <button type="submit" form="checkoutForm" id="confirmOrder"><i class="fa-solid fa-check-double"></i> Ký & Đặt hàng</button>
        </aside>
    </div>
</main>

<script>
    function copyData() {
        var copyText = document.getElementById("dataString");
        copyText.select();
        copyText.setSelectionRange(0, 99999); /* For mobile devices */
        navigator.clipboard.writeText(copyText.value);

        var btn = document.getElementById("btnCopy");
        btn.innerHTML = "<i class='fa-solid fa-check'></i> Copied!";
        btn.style.background = "#28a745";
        setTimeout(function(){
            btn.innerHTML = "<i class='fa-regular fa-copy'></i> Copy";
            btn.style.background = "#d63384";
        }, 2000);
    }
</script>

<jsp:include page="/common/footer.jsp"/>
</body>
</html>
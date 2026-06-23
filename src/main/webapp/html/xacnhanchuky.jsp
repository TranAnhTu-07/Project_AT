<%--
  Created by IntelliJ IDEA.
  User: Admin!
  Date: 6/20/2026
  Time: 14:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Xác nhận chữ ký số - GROUP11</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ttdh.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
</head>
<body>
<jsp:include page="/common/header.jsp"/>

<main class="checkout-container">
    <form id="verifyForm" action="${pageContext.request.contextPath}/VerifySignature" method="POST">
        <section class="checkout-block" style="padding: 40px 30px;">

            <div class="page-title" style="border-bottom: none; margin-bottom: 10px;">
                <h1 style="color: #000000;"><i class="fa-solid fa-file-signature"></i> Xác thực chữ ký số</h1>
                <p>Đơn hàng <b>#${orderId}</b> đã được ghi nhận. Vui lòng ký điện tử để hoàn tất!</p>
            </div>

            <hr style="border: 0; border-top: 2px dashed #eee; margin-bottom: 25px;">

            <div class="form-group">
                <label style="color: #e53935; font-size: 1.1em;"><i class="fa-solid fa-1"></i> Chuỗi dữ liệu cần ký:</label>
                <textarea class="form-control" rows="3" readonly style="background: #f8f9fa; font-family: monospace; color: #333; font-weight: bold; cursor: text;">${dataToSign}</textarea>
            </div>

            <input type="hidden" name="orderId" value="${orderId}">
            <div class="form-group" style="margin-top: 25px;">
                <label style="color: #28a745; font-size: 1.1em;"><i class="fa-solid fa-2"></i> Dán Chữ Ký (Signature) từ Tool vào đây:</label>
                <textarea name="signature" class="form-control" rows="5" placeholder="Dán đoạn mã chữ ký Base64 vào đây..." required style="border: 2px solid #28a745; background: #f4fdf6;"></textarea>
            </div>

            <button type="submit" id="confirmOrder" style="background: #28a745; margin-top: 30px; box-shadow: 0 4px 10px rgba(40, 167, 69, 0.3);">
                <i class="fa-solid fa-shield-check"></i> XÁC NHẬN CHỮ KÝ
            </button>

        </section>
    </form>
</main>

<jsp:include page="/common/footer.jsp"/>
</body>
</html>

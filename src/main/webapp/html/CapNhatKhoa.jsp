<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cập nhật Public Key mới</title>
    <link rel="stylesheet" href="css/header.css">
    <link rel="stylesheet" href="css/footer.css">
    <link rel="stylesheet" href="css/baomatkhoa.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">

    <style>
        .bmk-textarea {
            width: 100%;
            height: 160px;
            padding: 15px;
            border: 1px solid #ccc;
            border-radius: 6px;
            font-family: 'Courier New', Courier, monospace;
            font-size: 13px;
            resize: none;
            box-sizing: border-box;
            background: #fafafa;
            margin-bottom: 20px;
        }
        .bmk-textarea:focus {
            border-color: #28a745;
            outline: none;
            box-shadow: 0 0 5px rgba(40,167,69,0.2);
        }
        .bmk-btn-success {
            background-color: #28a745;
            color: #ffffff;
            border: none;
            padding: 12px 35px;
            font-size: 16px;
            font-weight: bold;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.2s;
        }
        .bmk-btn-success:hover {
            background-color: #218838;
        }
        .error-alert {
            color: #721c24;
            background-color: #f8d7da;
            border: 1px solid #f5c6cb;
            padding: 12px;
            border-radius: 5px;
            margin-bottom: 20px;
            font-size: 14.5px;
            text-align: left;
        }
    </style>
</head>
<body>

<jsp:include page="/common/header.jsp"/>

<main class="bmk-page-wrapper">
    <div class="bmk-container">

        <h2 class="bmk-main-title">Cập nhật Public Key mới</h2>

        <p class="bmk-description">
            Vui lòng sử dụng <strong>Tool Tạo Khóa RSA</strong> của nhóm để sinh ra cặp khóa mới.<br>
            Sau đó, hãy sao chép (Copy) đoạn mã <strong>Public Key</strong> và dán vào ô bên dưới.
        </p>

        <div class="bmk-warning-box" style="background-color: #e9ecef; border-color: #dee2e6;">
            <h3 class="bmk-warning-title" style="color: #495057;">Hướng dẫn nhập khóa</h3>
            <ul class="bmk-warning-list" style="color: #495057;">
                <li>Chuỗi Public Key hợp lệ thường bắt đầu bằng <code>-----BEGIN PUBLIC KEY-----</code></li>
                <li>Tuyệt đối <strong>không dán Private Key</strong> lên hệ thống để đảm bảo an toàn.</li>
                <li>Sau khi cập nhật thành công, bạn có thể tiếp tục mua hàng và ký số đơn hàng.</li>
            </ul>
        </div>

        <c:if test="${not empty errorMsg}">
            <div class="error-alert">
                <i class="fas fa-exclamation-circle"></i> ${errorMsg}
            </div>
        </c:if>

        <form action="CapNhatKhoa" method="POST">
            <textarea name="publicKeyText" class="bmk-textarea" placeholder="Dán nội dung Public Key của bạn vào đây..."></textarea>

            <button type="submit" class="bmk-btn-success">
                Xác nhận lưu khóa
            </button>
        </form>

    </div>
</main>

<jsp:include page="/common/footer.jsp"/>

</body>
</html>
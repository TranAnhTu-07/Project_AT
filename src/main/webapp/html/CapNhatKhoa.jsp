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
        /* CSS cho vùng chọn file đẹp mắt và chuyên nghiệp hơn */
        .file-upload-wrapper {
            position: relative;
            width: 100%;
            margin-bottom: 25px;
        }
        .file-upload-input {
            width: 100%;
            padding: 15px 20px;
            border: 2px dashed #28a745;
            border-radius: 6px;
            background-color: #fafafa;
            font-size: 15px;
            color: #495057;
            cursor: pointer;
            box-sizing: border-box;
            transition: all 0.3s ease;
        }
        .file-upload-input:hover {
            background-color: #f1f9f3;
            border-color: #218838;
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
            width: 100%; /* Kéo dài nút bằng form cho cân đối */
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
            Sau đó, hãy chọn và tải file <strong>public_key.txt</strong> lên hệ thống bên dưới.
        </p>

        <div class="bmk-warning-box" style="background-color: #e9ecef; border-color: #dee2e6;">
            <h3 class="bmk-warning-title" style="color: #495057;">Hướng dẫn tải khóa</h3>
            <ul class="bmk-warning-list" style="color: #495057;">
                <li>Hệ thống chỉ chấp nhận định dạng file văn bản chứa mã khóa dạng <code>.txt</code></li>
                <li>Tuyệt đối <strong>không tải file Private Key</strong> lên hệ thống để đảm bảo an toàn bảo mật.</li>
                <li>Sau khi cập nhật thành công, hệ thống tự động vô hiệu hóa khóa cũ (nếu có) và bạn có thể ký số đơn hàng.</li>
            </ul>
        </div>

        <c:if test="${not empty errorMsg}">
            <div class="error-alert">
                <i class="fas fa-exclamation-circle"></i> ${errorMsg}
            </div>
        </c:if>

        <form action="CapNhatKhoa" method="POST" enctype="multipart/form-data">
            <div class="file-upload-wrapper">
                <input type="file" name="keyFile" accept=".txt" class="file-upload-input" required>
            </div>

            <button type="submit" class="bmk-btn-success">
                <i class="fas fa-upload"></i> Xác nhận tải khóa lên
            </button>
        </form>

    </div>
</main>

<jsp:include page="/common/footer.jsp"/>

<c:if test="${not empty successAlert}">
    <script>
        alert("${successAlert}");
        window.location.href = "${pageContext.request.contextPath}/ListProduct";
    </script>
</c:if>

</body>
</html>
</body>
</html>
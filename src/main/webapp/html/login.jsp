<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%--
  Created by IntelliJ IDEA.
  User: Admin!
  Date: 12/14/2025
  Time: 14:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
</head>
<body>
<%--header--%>
<jsp:include page="/common/header.jsp"/>
<%--body--%>
<main class="login-page">
    <nav class="breadcrumb-nav">
        <ul class="breadcrumb">
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/ListProduct">Trang chủ</a></li>
            <li class="breadcrumb-item active"><a href="#">Đăng Nhập</a></li>
        </ul>
    </nav>

    <div class="login-box">
        <div class="form-login">
            <h2>Đăng nhập</h2>

            <c:if test="${not empty param.msg}">
                <p style="color: ${param.msg == 'success' || param.msg == 'activated' ? 'green' : 'red'}; text-align: center; font-weight: bold;">
                    <c:if test="${param.msg == 'success'}">Đăng ký thành công! (check mail or vào db sửa status=1)</c:if>
                    <c:if test="${param.msg == 'activated'}">Kích hoạt thành công! Đăng nhập ngay.</c:if>
                    <c:if test="${param.msg == 'error'}">Link kích hoạt bị lỗi hoặc hết hạn!</c:if>
                    </p>
            </c:if>

            <c:if test="${not empty error}">
                <p style="color: red; text-align: center; font-weight: bold;">${error}</p>
            </c:if>

            <c:if test="not empty message">
                <p style="color: green; text-align: center; font-weight: bold;">${message}</p>
            </c:if>

            <form action="${pageContext.request.contextPath}/Login" method="POST">
                <div class="input-group">
                    <input type="text" name="email" placeholder="Email hoặc Số điện thoại" required value="${email}">
                </div>

                <div class="input-group password-wrapper" style="position: relative;">
                    <input type="password" name="password" id="login-pass" placeholder="Nhập mật khẩu" required>
                    <span class="toggle-btn" onclick="togglePassword('login-pass', this)" style="position: absolute; right: 15px; top: 15px; cursor: pointer; color: #666;">
                        <i class="fa-solid fa-eye-slash"></i>
                    </span>
                </div>

                <div class="form-options">
                    <a href="${pageContext.request.contextPath}/html/forgot.jsp" class="forgot-password">Quên mật khẩu?</a>
                </div>

                <button type="submit" class="btn-login">Đăng Nhập</button>
            </form>

            <div class="divider"><span>Hoặc</span></div>

            <div class="social-icons">
                <a href="#" class="icons"><i class="fa-brands fa-google"></i>Google</a>
                <a href="#" class="icons"><i class="fa-brands fa-facebook"></i>Facebook</a>
            </div>

            <div class="register-link">
                <p>Chưa có tài khoản? <a href="${pageContext.request.contextPath}/html/register.jsp">Đăng ký ngay</a></p>
            </div>
        </div>
    </div>
</main>
<%--header--%>
<jsp:include page="/common/footer.jsp"/>
<script>
    function togglePassword(fieldId, iconSpan) {
        var passInput = document.getElementById(fieldId);
        var eyeIcon = iconSpan.querySelector("i");

        if (passInput.type === "password") {
            passInput.type = "text";
            eyeIcon.classList.remove("fa-eye-slash");
            eyeIcon.classList.add("fa-eye");
        } else {
            passInput.type = "password";
            eyeIcon.classList.remove("fa-eye");
            eyeIcon.classList.add("fa-eye-slash");
        }
    }
</script>
</body>
</html>

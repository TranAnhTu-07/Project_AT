<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Forgot Password</title>
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
            <li class="breadcrumb-item active"><a href="#">Quên mật khẩu</a></li>
        </ul>
    </nav>

    <div class="login-box">
        <div class="form-login">
            <h2>Khôi phục mật khẩu</h2>

            <c:if test="${not empty error}">
                <div style="color: red; text-align: center; margin-bottom: 15px; font-weight: bold;">
                    <i class="fas fa-exclamation-triangle"></i> ${error}
                </div>
            </c:if>
            <c:if test="${not empty message}">
                <div style="color: green; text-align: center; margin-bottom: 15px; font-weight: bold;">
                    <i class="fas fa-check-circle"></i> ${message}
                </div>
            </c:if>

            <div class="tabs">
                <button type="button" class="tab-btn active" onclick="switchTab(event, 'tab-email')">
                    <i class="fas fa-envelope"></i> Qua Email
                </button>
                <button type="button" class="tab-btn" onclick="switchTab(event, 'tab-phone')">
                    <i class="fas fa-mobile-alt"></i> Qua SĐT
                </button>
            </div>

            <div id="tab-email" class="tab-content active">
                <form action="${pageContext.request.contextPath}/Forgot" method="POST">
                    <p style="text-align: center; color: #555; margin-bottom: 20px; font-size: 14px;">
                        Nhập email đã đăng ký để nhận link đặt lại mật khẩu.
                    </p>
                    <div class="input-group">
                        <input type="email" name="email" value="${email}" placeholder="Nhập Email đã đăng ký" required>
                    </div>
                    <button type="submit" class="btn-login">Gửi Link Xác Nhận</button>
                </form>
            </div>

            <div id="tab-phone" class="tab-content">
                <form action="${pageContext.request.contextPath}/ResetPassword" method="POST" id="phoneForm">
                    <input type="hidden" name="type" value="phone">

                    <div class="input-group">
                        <div class="input-row"> <input type="text" id="phoneInput" name="phone" placeholder="Nhập SĐT (VD: 0912...)" required value="${phone}">
                            <button type="button" class="btn-otp" onclick="sendOTP()" id="btnGetCode">Lấy mã</button>
                        </div>
                    </div>

                    <div id="step-2-otp" style="display: none;">
                        <div class="input-group">
                            <div class="input-row">
                                <input type="text" id="otpInput" placeholder="Nhập mã OTP (123456)">
                                <button type="button" class="btn-otp" style="background-color: #ffc107; color: black; border-color: #ffc107;" onclick="verifyOTP()">Xác nhận</button>
                            </div>
                        </div>
                    </div>

                    <div id="step-3-reset" style="display: none; border-top: 1px dashed #666; padding-top: 20px; margin-top: 10px;">
                        <p style="text-align: center; color: green; font-weight: bold; margin-bottom: 15px;">
                            <i class="fas fa-check"></i> SĐT chính chủ! Mời đặt mật khẩu mới.
                        </p>

                        <div class="input-group password-wrapper" style="position: relative;">
                            <input type="password" id="new-pass" name="password" placeholder="Mật khẩu mới" required>
                            <span class="toggle-btn" onclick="togglePassword('new-pass', this)" style="position: absolute; right: 15px; top: 15px; cursor: pointer; color: #666;">
                                <i class="fa-solid fa-eye-slash"></i>
                            </span>
                        </div>

                        <div class="input-group password-wrapper" style="position: relative;">
                            <input type="password" id="confirm-pass" name="confirm_password" placeholder="Nhập lại mật khẩu" required>
                            <span class="toggle-btn" onclick="togglePassword('confirm-pass', this)" style="position: absolute; right: 15px; top: 15px; cursor: pointer; color: #666;">
                                <i class="fa-solid fa-eye-slash"></i>
                            </span>
                        </div>
                        <button type="submit" class="btn-login">Đổi Mật Khẩu Ngay</button>
                    </div>
                </form>
            </div>

            <div class="register-link">
                <p>Đã nhớ mật khẩu? <a href="${pageContext.request.contextPath}/html/login.jsp">Đăng nhập ngay</a></p>
            </div>
        </div>
    </div>
</main>

<%--fooer--%>
<jsp:include page="/common/footer.jsp"/>

<script>
    //0 con mắt
    function togglePassword(fieldId, iconSpan) {
        var passInput = document.getElementById(fieldId);
        var eyeIcon = iconSpan.querySelector("i");

        if (passInput.type === "password") {
            passInput.type = "text";
            // Giữ nguyên fa-solid, chỉ đổi icon
            eyeIcon.classList.remove("fa-eye-slash");
            eyeIcon.classList.add("fa-eye");
        } else {
            passInput.type = "password";
            eyeIcon.classList.remove("fa-eye");
            eyeIcon.classList.add("fa-eye-slash");
        }
    }
    // 1. Chuyển Tab
    function switchTab(evt, tabName) {
        var i, tabcontent, tablinks;
        // Ẩn hết nội dung tab
        tabcontent = document.getElementsByClassName("tab-content");
        for (i = 0; i < tabcontent.length; i++) {
            tabcontent[i].style.display = "none";
            tabcontent[i].classList.remove("active");
        }
        // Bỏ active ở các nút tab
        tablinks = document.getElementsByClassName("tab-btn");
        for (i = 0; i < tablinks.length; i++) {
            tablinks[i].className = tablinks[i].className.replace(" active", "");
        }
        // Hiện tab cần hiện
        document.getElementById(tabName).style.display = "block";
        document.getElementById(tabName).classList.add("active");
        evt.currentTarget.className += " active";
    }

    // 2. Fake Gửi OTP
    function sendOTP() {
        var phone = document.getElementById("phoneInput").value;
        var btn = document.getElementById("btnGetCode");

        if(phone.length < 9) {
            alert("Vui lòng nhập số điện thoại hợp lệ!");
            return;
        }

        // Hiệu ứng Loading
        btn.innerText = "Đang gửi...";
        btn.disabled = true;
        btn.style.cursor = "not-allowed";

        setTimeout(() => {
            alert("Mã OTP (Demo): 123456");
            document.getElementById("step-2-otp").style.display = "block";
            btn.innerText = "Gửi lại";
            btn.disabled = false;
            btn.style.cursor = "pointer";
        }, 1000); // Giả vờ đợi 1 giây
    }

    // 3. Fake Xác thực OTP
    function verifyOTP() {
        var code = document.getElementById("otpInput").value;
        if(code === "123456") {
            alert("Xác thực thành công!");
            // Ẩn OTP, khóa SĐT
            document.getElementById("step-2-otp").style.display = "none";
            document.getElementById("phoneInput").readOnly = true;
            document.getElementById("btnGetCode").style.display = "none";

            // Hiện form đổi pass
            document.getElementById("step-3-reset").style.display = "block";
        } else {
            alert("Mã OTP sai! Vui lòng nhập 123456");
        }
    }
</script>
</body>
</html>
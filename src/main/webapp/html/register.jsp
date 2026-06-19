<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Đăng Ký</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <script src="https://www.gstatic.com/firebasejs/9.6.1/firebase-app-compat.js"></script>
    <script src="https://www.gstatic.com/firebasejs/9.6.1/firebase-auth-compat.js"></script>
</head>
<body>
<%--header--%>
<jsp:include page="/common/header.jsp"/>
<%--body--%>
<main class="login-page">
    <nav class="breadcrumb-nav">
        <ul class="breadcrumb">
            <li class="breadcrumb-item"><a href="${pageContext.request.contextPath}/ListProduct">Trang chủ</a></li>
            <li class="breadcrumb-item active"><a href="#">Đăng Ký</a></li>
        </ul>
    </nav>

    <div class="login-box">
        <div class="form-login">
            <h2>Đăng ký</h2>

            <c:if test="${not empty error}">
                <p style="color: red; text-align: center; font-weight: bold; margin-bottom: 10px;">${error}</p>
            </c:if>
            <c:if test="${not empty message}">
                <p style="color: green; text-align: center; font-weight: bold; margin-bottom: 10px;">${message}</p>
            </c:if>

            <div class="tabs">
                <button type="button" class="tab-btn active" onclick="openTab(event, 'tab-email')">Email</button>
                <button type="button" class="tab-btn" onclick="openTab(event, 'tab-phone')">Số điện thoại</button>
            </div>

            <div id="tab-email" class="tab-content active">
                <form action="${pageContext.request.contextPath}/Register" method="POST">
                    <input type="hidden" name="action" value="register_email">

                    <div class="input-group">
                        <input type="text" name="fullname" value="${fullname}" placeholder="Họ và Tên" required>
                    </div>
                    <div class="input-group">
                        <input type="email" name="email" value="${email}" placeholder="Nhập Email" required>
                    </div>

                    <div class="input-group" style="position: relative;">
                        <input type="password" id="password-email" name="password"
                               placeholder="Mật khẩu (Có Hoa, Thường, Số, Ký tự ĐB)" required>
                        <span onclick="togglePassword('password-email', this)"
                              style="position: absolute; right: 15px; top: 15px; cursor: pointer; color: #666;">
                            <i class="fa-solid fa-eye-slash"></i>
                        </span>
                    </div>

                    <button type="submit" class="btn-login">Đăng Ký</button>
                </form>
            </div>

            <div id="tab-phone" class="tab-content">
                <form action="${pageContext.request.contextPath}/Register" method="POST" id="phoneForm">
                    <input type="hidden" name="action" value="register_phone">

                    <div class="input-group">
                        <input type="text" name="fullname" value="${fullname}" placeholder="Họ và Tên" required>
                    </div>

                    <div class="input-group input-row">
                        <input type="text" id="phoneNumber" name="phone" value="${phone}" placeholder="Số điện thoại"
                               required>
                        <button type="button" class="btn-otp" id="btn-get-otp" onclick="guiOTP()">Lấy mã</button>
                    </div>

                    <div class="input-group input-row" id="otp-group" style="display:none;">
                        <input type="text" id="otpInput" placeholder="Nhập mã 6 số">
                        <button type="button" class="btn-otp" onclick="xacThucOTP()" style="background-color: #28a745;">
                            Xác nhận
                        </button>
                    </div>

                    <p id="otp-error" style="color: red; font-size: 13px; margin-top: 5px;"></p>

                    <div id="recaptcha-container" style="margin-bottom: 15px;"></div>

                    <div class="input-group" style="position: relative;">
                        <input type="password" id="password-phone" name="password" placeholder="Mật khẩu" required>
                        <span onclick="togglePassword('password-phone', this)"
                              style="position: absolute; right: 15px; top: 15px; cursor: pointer; color: #666;">
                <i class="fa-solid fa-eye-slash"></i>
            </span>
                    </div>

                    <button type="submit" class="btn-login" id="btnPhoneRegister" disabled
                            style="opacity: 0.6; cursor: not-allowed;">
                        Đăng Ký
                    </button>
                </form>
            </div>

            <div class="register-link">
                <p>Đã có tài khoản? <a href="${pageContext.request.contextPath}/html/login.jsp">Đăng nhập ngay</a></p>
            </div>
        </div>
    </div>
</main>
<%--footer--%>
<jsp:include page="/common/footer.jsp"/>

<script>
    // --- 1. CONFIG FIREBASE CỦA MÀY ---
    const firebaseConfig = {
        apiKey: "AIzaSyBbm_HUjlCZ9low0_3qXE0LlkFL9V3ATOs",
        authDomain: "loginweb-9f978.firebaseapp.com",
        projectId: "loginweb-9f978",
        storageBucket: "loginweb-9f978.firebasestorage.app",
        messagingSenderId: "255337857766",
        appId: "1:255337857766:web:9c81a86c8ffdc264633127"
    };

    // Khởi tạo
    firebase.initializeApp(firebaseConfig);
    const auth = firebase.auth();
    auth.languageCode = 'vi';

    // Tạo Captcha ẩn (Invisible)
    window.recaptchaVerifier = new firebase.auth.RecaptchaVerifier('recaptcha-container', {
        'size': 'invisible'
    });

    // --- 2. HÀM GỬI OTP --//
    function guiOTP() {
        var phoneInput = document.getElementById("phoneNumber").value;
        var btnGetOtp = document.getElementById("btn-get-otp");

        // Validate sơ bộ
        if (!phoneInput || phoneInput.length < 9) {
            alert("Vui lòng nhập số điện thoại hợp lệ!");
            return;
        }

        // Xử lý đầu số (+84)
        var formatPhone = phoneInput;
        if (formatPhone.startsWith('0')) {
            formatPhone = '+84' + formatPhone.substring(1);
        } else if (!formatPhone.startsWith('+')) {
            formatPhone = '+84' + formatPhone;
        }

        // Hiệu ứng loading
        btnGetOtp.innerText = "Đang gửi...";
        btnGetOtp.disabled = true;

        const appVerifier = window.recaptchaVerifier;

        auth.signInWithPhoneNumber(formatPhone, appVerifier)
            .then((confirmationResult) => {
                // == Gửi thành công ==
                window.confirmationResult = confirmationResult;
                alert("Đã gửi mã OTP! (Check điện thoại hoặc dùng 123456 nếu test)");

                // Hiện ô nhập OTP
                document.getElementById("otp-group").style.display = "flex";
                document.getElementById("otpInput").focus();

                // Đổi nút thành "Gửi lại"
                btnGetOtp.innerText = "Gửi lại";
                btnGetOtp.disabled = false;
            }).catch((error) => {
            // == Lỗi ==
            console.error(error);
            alert("Lỗi gửi tin: " + error.message);
            btnGetOtp.innerText = "Lấy mã";
            btnGetOtp.disabled = false;
            // Reset captcha để thử lại
            grecaptcha.reset(window.recaptchaWidgetId);
        });
    }

    // --- 3. HÀM XÁC THỰC OTP ---
    function xacThucOTP() {
        const code = document.getElementById("otpInput").value;
        const errorMsg = document.getElementById("otp-error");

        window.confirmationResult.confirm(code).then((result) => {
            // == XÁC THỰC THÀNH CÔNG ==
            alert("Xác thực thành công!");
            errorMsg.innerText = "";

            // 1. Ẩn phần nhập OTP đi cho gọn
            document.getElementById("otp-group").style.display = "none";
            document.getElementById("btn-get-otp").style.display = "none"; // Ẩn luôn nút lấy mã

            // 2. KHÓA ô nhập SĐT (Không cho sửa thành số khác)
            var phoneField = document.getElementById("phoneNumber");
            phoneField.readOnly = true;
            phoneField.style.backgroundColor = "#e9ecef";
            phoneField.style.cursor = "not-allowed";

            // 3. MỞ KHÓA nút Đăng ký để submit form về Server
            var btnRegister = document.getElementById("btnPhoneRegister");
            btnRegister.disabled = false;
            btnRegister.style.opacity = "1";
            btnRegister.style.cursor = "pointer";
            btnRegister.innerText = "Hoàn tất đăng ký";

        }).catch((error) => {
            // == Sai mã ==
            errorMsg.innerText = "Mã OTP không đúng. Vui lòng thử lại.";
        });
    }

    // --- 4. CÁC HÀM CŨ (GIỮ LẠI) ---
    function openTab(evt, tabName) {
        var i, tabcontent, tablinks;
        tabcontent = document.getElementsByClassName("tab-content");
        for (i = 0; i < tabcontent.length; i++) {
            tabcontent[i].style.display = "none";
            tabcontent[i].classList.remove("active");
        }
        tablinks = document.getElementsByClassName("tab-btn");
        for (i = 0; i < tablinks.length; i++) {
            tablinks[i].className = tablinks[i].className.replace(" active", "");
        }
        document.getElementById(tabName).style.display = "block";
        document.getElementById(tabName).classList.add("active");
        evt.currentTarget.className += " active";
    }

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
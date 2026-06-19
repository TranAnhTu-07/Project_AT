<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>Báo mất khóa</title>
  <link rel="stylesheet" href="css/header.css">
  <link rel="stylesheet" href="css/footer.css">
  <link rel="stylesheet" href="css/baomatkhoa.css"> <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css">
</head>
<body>

<jsp:include page="/common/header.jsp"/>

<main class="bmk-page-wrapper">
  <div class="bmk-container">

    <h2 class="bmk-main-title">Báo mất khóa</h2>

    <p class="bmk-description">
      Nếu bạn nghi ngờ khóa bí mật đã bị lộ hoặc không còn quyền kiểm soát khóa,<br>
      hãy báo mất khóa để bảo vệ tài khoản của bạn.
    </p>

    <div class="bmk-warning-box">
      <h3 class="bmk-warning-title">Lưu ý quan trọng</h3>
      <ul class="bmk-warning-list">
        <li>Khi báo mất khóa, khóa hiện tại sẽ bị vô hiệu hóa ngay lập tức.</li>
        <li>Bạn sẽ không thể sử dụng khóa này để ký số hoặc thực hiện giao dịch.</li>
        <li>Sau khi báo mất khóa, bạn cần tạo cặp khóa mới để tiếp tục sử dụng.</li>
      </ul>
    </div>

    <p class="bmk-confirm-text">Bạn có chắc chắn muốn báo mất khóa?</p>

    <form action="BaoMatKhoa" method="POST">
      <button type="submit" class="bmk-btn-submit" onclick="return confirm('Xác nhận báo mất khóa? Khóa hiện tại sẽ bị vô hiệu hóa!');">
        Báo mất khóa
      </button>
    </form>

  </div>
</main>

<jsp:include page="/common/footer.jsp"/>

</body>
</html>
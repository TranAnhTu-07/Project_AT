<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
  <meta charset="UTF-8">
  <title>Kết quả xác thực chữ ký số</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/ttdh.css">
  <link rel="stylesheet" href="../css/header.css">
  <link rel="stylesheet" href="../css/footer.css">
</head>
<body>
<jsp:include page="/common/header.jsp"/>

<main class="checkout-container">
  <section class="checkout-block" style="padding: 40px 30px; text-align: center;">
    <c:choose>
      <c:when test="${not empty error}">
        <h1 style="color:#e53935;"><i class="fa-solid fa-circle-xmark"></i> Lỗi</h1>
        <p><c:out value="${error}"/></p>
      </c:when>
      <c:when test="${isValid == true}">
        <h1 style="color:#28a745;"><i class="fa-solid fa-circle-check"></i> Chữ ký hợp lệ!</h1>
        <p>Đơn hàng <b>#${orderId}</b> đã được xác thực chữ ký số thành công.</p>
      </c:when>
      <c:otherwise>
        <h1 style="color:#e53935;"><i class="fa-solid fa-circle-xmark"></i> Chữ ký không hợp lệ!</h1>
        <p>Chữ ký không khớp với dữ liệu đơn hàng <b>#<c:out value="${orderId}"/></b>.</p>
      </c:otherwise>
    </c:choose>
  </section>
</main>

<jsp:include page="/common/footer.jsp"/>
</body>
</html>
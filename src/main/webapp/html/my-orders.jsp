<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>Đơn hàng của tôi</title>
  <link rel="stylesheet" href="css/header.css">
  <link rel="stylesheet" href="css/footer.css">
  <link rel="stylesheet" href="css/Product.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/my-orders.css">
  <script src="js/search.js"></script>
</head>
<body>
<jsp:include page="/common/header.jsp"/>
<div class="orders-container">
  <h2 class="orders-title">Đơn hàng của tôi</h2>
    <table class="order-table">

      <thead>
      <tr>
        <th>Mã đơn hàng</th>
        <th>Ngày mua</th>
        <th>Sản phẩm</th>
        <th>Tổng tiền</th>
        <th>Trạng thái</th>
        <th>Chữ ký</th>
      </tr>
      </thead>

      <tbody>

      <c:forEach items="${orders}" var="o">

        <tr>

          <td>
            #DH${o.orderId}
          </td>

          <td>
            <fmt:formatDate value="${o.orderDate}" pattern="dd/MM/yyyy"/>
          </td>

          <td>
            <a class="btn-detail" href="${pageContext.request.contextPath}/order-detail?id=${o.orderId}">
              Xem chi tiết
            </a>

          </td>

          <td>
            <fmt:formatNumber value="${o.totalAmount}" type="number" groupingUsed="true"/> ₫
          </td>

          <td>
            <c:choose>
              <c:when test="${o.orderStatus == 'Chờ xác nhận'}">
                <span class="status-badge status-pending">Chờ xác nhận</span>
              </c:when>
              <c:when test="${o.orderStatus == 'Đang xử lý'}">
                <span class="status-badge status-processing">Đang xử lý</span>
              </c:when>
              <c:when test="${o.orderStatus == 'Đang giao hàng'}">
                <span class="status-badge status-shipping">Đang giao</span>
              </c:when>
              <c:when test="${o.orderStatus == 'Đã giao'}">
                <span class="status-badge status-delivered">Đã giao</span>
              </c:when>
              <c:otherwise>
                <span class="status-badge">${o.orderStatus}</span>
              </c:otherwise>
            </c:choose>
          </td>

          <td>

            <c:if test="${o.signatureStatus==0}">
              ❌
            </c:if>

            <c:if test="${o.signatureStatus==1}">
              ✔
            </c:if>

          </td>

        </tr>

      </c:forEach>

      </tbody>

    </table>
</div>
<%--footer--%>
<jsp:include page="/common/footer.jsp"/>
</body>
</html>

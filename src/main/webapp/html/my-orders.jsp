<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<p>Orders = ${orders}</p>
<p>Size = ${orders.size()}</p>
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
<jsp:include page="/common/header.jsp"/>

<body>
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
          ${o.orderDate}
      </td>

      <td>

        <a href="${pageContext.request.contextPath}
                    /order-detail?id=${o.orderId}">

          Xem chi tiết

        </a>

      </td>

      <td>
          ${o.totalAmount}
      </td>

      <td>

        <c:choose>

          <c:when test="${o.orderStatus==0}">
            Chờ xác nhận
          </c:when>

          <c:when test="${o.orderStatus==1}">
            Đang xử lý
          </c:when>

          <c:when test="${o.orderStatus==2}">
            Đang giao
          </c:when>

          <c:otherwise>
            Đã giao
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
<%--footer--%>
<jsp:include page="/common/footer.jsp"/>
</body>
</html>

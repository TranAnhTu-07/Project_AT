<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>CANON-DSLR</title>
  <link rel="stylesheet" href="css/Product.css">
  <link rel="stylesheet" href="css/header.css">
  <link rel="stylesheet" href="css/footer.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
</head>
<body>
<%--header--%>
<jsp:include page="/common/header.jsp"/>
<%--body--%>
<main id="main-page-content">
  <div class="container">
    <div class="brand-container">
      <div class="brand-item">
        <img src="https://mayanhvietnam.com/asset/imgs/icon/hang-san-xuat/canon.svg" alt="Canon">
      </div>
      <div class="brand-item">
        <img src="https://mayanhvietnam.com/asset/imgs/icon/hang-san-xuat/sony.svg" alt="Sony">
      </div>
      <div class="brand-item">
        <img src="https://mayanhvietnam.com/asset/imgs/icon/hang-san-xuat/nikon.svg" alt="Nikon">
      </div>

      <div class="brand-item">
        <img src="https://mayanhvietnam.com/asset/imgs/icon/hang-san-xuat/fujifilm.svg" alt="Fujifilm">
      </div>
      <div class="brand-item">
        <img src="https://mayanhvietnam.com/asset/imgs/icon/hang-san-xuat/olympus.svg" alt="Lumix">
      </div>
      <div class="brand-item">
        <img src="https://mayanhvietnam.com/asset/imgs/icon/hang-san-xuat/leica.svg" alt="Leica">
      </div>
      <div class="brand-item">
        <img src="https://mayanhvietnam.com/asset/imgs/icon/hang-san-xuat/sigma.svg" alt="Sigma">
      </div>
    </div>
    <div class="product-title" style="text-transform: uppercase;">
      ${catName}
    </div>
    <div class="cover-product-item">
      <c:forEach items="${listLens}" var="p">
        <div class="product-card-item">
          <img src="${p.img}" class="product-img" alt="${p.productName}">

          <h3 class="product-name">${p.productName}</h3>

          <p class="product-price">
            <fmt:formatNumber value="${p.price}" type="number" groupingUsed="true"/>₫
          </p>
        </div>
      </c:forEach>
    </div>
  </div>
</main>
<%--footer--%>
<jsp:include page="/common/footer.jsp"/>
</body>
</html>

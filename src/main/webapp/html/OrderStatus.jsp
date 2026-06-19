<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Theo dõi đơn hàng</title>
    <link rel="stylesheet" href="../css/OrderStatus.css">
    <link rel="stylesheet" href="../css/header.css">
    <link rel="stylesheet" href="../css/footer.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css">
</head>
<body>
<!--header-->
<jsp:include page="/common/header.jsp"/>
<!--body-->
<div class="container">
    <div class="header-body">
        <h1>🚚 Theo Dõi Đơn Hàng</h1>
        <p>Cập nhật trạng thái đơn hàng của bạn</p>
    </div>

<%--    Thông tin đơn hàng--%>

    <div class="orders">
        <div class="content">
            <div class="orders-info">
                <div class="orders-info-grid">
                    <div class="info-item">
                        <div class="info-label">Mã Đơn Hàng</div>
                        <div class="info-value">#${orders.orderCode}</div>
                    </div>
                    <div class="info-item">
                        <div class="info-label">Ngày Đặt Hàng</div>
                        <div class="info-value">${orders.orderDate}</div>
                    </div>
                    <div class="info-item">
                        <div class="info-label">Trạng Thái</div>
                        <div class="info-value">
                            <span class="badge badge-info">${orders.status}</span>
<%--                            <span class="badge ${orders.status == 'Đang Giao Hàng' ? 'badge-warning' : 'badge-success'}">--%>
<%--                                ${orders.status}--%>
<%--                            </span>--%>
                        </div>
                    </div>
                    <div class="info-item">
                        <div class="info-label">Dự Kiến Giao</div>
                        <div class="info-value">${orders.expectedDelivery}</div>
                    </div>
                </div>
            </div>

<%--            Danh sách sản phẩm--%>

            <div class="products">
                <h3 class="products-title">Sản Phẩm Trong Đơn Hàng</h3>

<%--                tính tổng tiền--%>
                <c:set var="total" value="0" />
                <c:forEach var="item" items="${items}">
                    <c:set var="total" value="${total + item.price * item.quantity}" />
                </c:forEach>

<%--                hiển thị danh sách--%>
                <c:forEach var="item" items="${items}">
                    <div class="product-item">
                        <div class="product-info">
                            <div class="product-name">${item.productName}</div>
<%--                            <img src="${item.image}" width="60">--%>
                            <div class="product-details">
                                Màu: ${item.color} | Số lượng: ${item.quantity}
                            </div>
                        </div>
                        <div class="product-price">
                            <fmt:formatNumber value="${item.price * item.quantity}" type="number" groupingUsed="true"/>đ
                        </div>
                    </div>
                </c:forEach>

<%--                tổng tiền--%>
                <div class="total">
                    <div class="total-label">Tổng Thanh Toán:</div>
                    <div class="total-amount">
                        <fmt:formatNumber value="${total}" type="number" groupingUsed="true"/>₫
                    </div>
                </div>

            </div>
        </div>

<%--        Timeline trạng thái--%>

        <div class="status-timeline">
            <h2 class="timeline-title">Tiến Trình Đơn Hàng</h2>

            <div class="timeline">
                <c:forEach var="st" items="${statuses}" varStatus="loop">

                    <!-- Mặc định: completed -->
                    <c:set var="itemClass" value="timeline-item completed"/>

                    <!-- Nếu là trạng thái cuối cùng -->
                    <c:if test="${loop.last}">
                        <c:set var="itemClass" value="timeline-item active"/>
                    </c:if>

                    <div class="${itemClass}">
                        <div class="timeline-dot"></div>

                        <div class="timeline-content">

                            <!-- ICON + TRẠNG THÁI -->
                            <div class="timeline-status">
                                <c:choose>
                                    <c:when test="${st.status == 'Đang Giao Hàng'}">🚚</c:when>
                                    <c:when test="${st.status == 'Giao Hàng Thành Công'}">✅</c:when>
                                    <c:otherwise>✓</c:otherwise>
                                </c:choose>
                                    ${st.status}
                            </div>

                            <!-- THỜI GIAN -->
                            <div class="timeline-date">
                                <fmt:formatDate value="${st.statusTime}" pattern="dd/MM/yyyy - HH:mm"/>
                            </div>

                            <!-- MÔ TẢ -->
                            <div class="timeline-description">
                                    ${st.description}
                            </div>

                        </div>
                    </div>

                </c:forEach>
            </div>
        </div>

    </div>
</div>
    <!--footer-->
<jsp:include page="/common/footer.jsp"/>
</div>
</body>
</html>
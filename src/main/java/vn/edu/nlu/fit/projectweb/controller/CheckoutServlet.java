package vn.edu.nlu.fit.projectweb.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.projectweb.dao.OrderDao;
import vn.edu.nlu.fit.projectweb.model.OrderDetail;
import vn.edu.nlu.fit.projectweb.model.Orders;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "CheckoutServlet", value = "/CheckoutServlet")
public class CheckoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String customerName = request.getParameter("customerName");
        String phoneNumber = request.getParameter("customerPhone");
        String email = request.getParameter("customerEmail");
        String shippingAddress = request.getParameter("customerAddress");
        String paymentMethod = request.getParameter("paymentMethod");

        HttpSession session = request.getSession();

        vn.edu.nlu.fit.projectweb.cart.Cart cart = (vn.edu.nlu.fit.projectweb.cart.Cart) session.getAttribute("cart");

        if (cart == null) {
            response.sendRedirect("cart.jsp");
            return;
        }

        double totalAmount = cart.getTotal();

        List<OrderDetail> cartItems = new java.util.ArrayList<>();
        for (vn.edu.nlu.fit.projectweb.cart.CartItem item : cart.getItems()) {
            OrderDetail detail = new OrderDetail();
            detail.setProductId(item.getProduct().getProductID());
            detail.setQuantity(item.getQuantity());
            detail.setPrice(item.getPrice());
            cartItems.add(detail);
        }

        if (cartItems.isEmpty()) {
            response.sendRedirect("cart.jsp");
            return;
        }
        vn.edu.nlu.fit.projectweb.model.User loginUser = (vn.edu.nlu.fit.projectweb.model.User) session.getAttribute("account");

        int userId = 0;
        if (loginUser != null) {
            userId = loginUser.getUserId();
        } else {
            response.sendRedirect(request.getContextPath() + "/Login");
            return;
        }

        Orders newOrder = new Orders();
        newOrder.setUserId(userId);
        newOrder.setCustomerName(customerName);
        newOrder.setPhoneNumber(phoneNumber);
        newOrder.setEmail(email);
        newOrder.setShippingAddress(shippingAddress);
        newOrder.setPaymentMethod(paymentMethod);
        newOrder.setTotalAmount(totalAmount);

        OrderDao orderDao = new OrderDao();
        int generatedOrderId = orderDao.insertOrder(newOrder, cartItems);

        if (generatedOrderId > 0) {
            StringBuilder itemsBuilder = new StringBuilder();
            for (vn.edu.nlu.fit.projectweb.cart.CartItem item : cart.getItems()) {
                itemsBuilder.append("|Item:")
                        .append(item.getProduct().getProductName())
                        .append("x")
                        .append(item.getQuantity());
            }
            // Chuỗi để băm ra ký: Mã đơn | Tên | SĐT | Tổng tiền
            String dataToSign = "OrderID:" + generatedOrderId +
                    "|Name:" + customerName +
                    "|Phone:" + phoneNumber +
                    "|Email:" + email +
                    "|Address:" + shippingAddress +
                    "|Total:" + totalAmount +
                    itemsBuilder.toString();

            session.removeAttribute("cart");

            request.setAttribute("orderId", generatedOrderId);
            request.setAttribute("dataToSign", dataToSign);
            request.getRequestDispatcher("/html/xacnhanchuky.jsp").forward(request, response);

        } else {
            request.setAttribute("error", "Lỗi hệ thống, không thể tạo đơn hàng");
            request.getRequestDispatcher("/html/ttdh.jsp").forward(request, response);
        }
    }
}
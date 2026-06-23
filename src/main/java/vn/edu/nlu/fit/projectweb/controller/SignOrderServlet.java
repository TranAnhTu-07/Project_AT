package vn.edu.nlu.fit.projectweb.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.projectweb.dao.OrderDao;
import vn.edu.nlu.fit.projectweb.model.OrderDetail;
import vn.edu.nlu.fit.projectweb.model.Orders;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "SignOrderServlet", value = "/sign-order")
public class SignOrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String orderIdParam = request.getParameter("orderId");
        int orderId = Integer.parseInt(orderIdParam);

        OrderDao orderDao = new OrderDao();
        Orders order = orderDao.getOrderById(orderId);
        List<OrderDetail> details = orderDao.getOrderDetailsByOrderId(orderId);

        if (order == null) {
            response.sendRedirect(request.getContextPath() + "/cart.jsp");
            return;
        }

        StringBuilder itemsBuilder = new StringBuilder();
        for (OrderDetail detail : details) {
            itemsBuilder.append("|Item:")
                    .append(detail.getProduct().getProductName())
                    .append("x")
                    .append(detail.getQuantity());
        }

        String dataToSign = "OrderID:" + order.getOrderId() +
                "|Name:" + order.getCustomerName() +
                "|Phone:" + order.getPhoneNumber() +
                "|Email:" + order.getEmail() +
                "|Address:" + order.getShippingAddress() +
                "|Total:" + order.getTotalAmount() +
                itemsBuilder.toString();

        request.setAttribute("orderId", orderId);
        request.setAttribute("dataToSign", dataToSign);
        request.getRequestDispatcher("/html/xacnhanchuky.jsp").forward(request, response);
    }
}
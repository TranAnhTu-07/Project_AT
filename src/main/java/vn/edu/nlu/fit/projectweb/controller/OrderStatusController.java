package vn.edu.nlu.fit.projectweb.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.projectweb.dao.OrderDao;
import vn.edu.nlu.fit.projectweb.dao.OrderItemDao;
import vn.edu.nlu.fit.projectweb.dao.OrderStatusDao;
import vn.edu.nlu.fit.projectweb.model.Orders;
import vn.edu.nlu.fit.projectweb.model.OrderDetail;
import vn.edu.nlu.fit.projectweb.model.OrderStatus;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "OrderStatusController", value = "/OrderStatus")
public class OrderStatusController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int orderId = Integer.parseInt(request.getParameter("id"));

        try {
            OrderDao orderDAO = new OrderDao();
            OrderItemDao itemDAO = new OrderItemDao();
            OrderStatusDao statusDAO = new OrderStatusDao();

            Orders orders = orderDAO.getOrderById(orderId);
            List<OrderDetail> items = itemDAO.getItemsByOrderId(orderId);
            List<OrderStatus> statuses = statusDAO.getStatusByOrderId(orderId);

            request.setAttribute("order", orders);
            request.setAttribute("items", items);
            request.setAttribute("statuses", statuses);

            request.getRequestDispatcher("/OrderStatus.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
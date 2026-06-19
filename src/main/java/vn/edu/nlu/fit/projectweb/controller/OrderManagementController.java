package vn.edu.nlu.fit.projectweb.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.projectweb.dao.OrderDao;
import vn.edu.nlu.fit.projectweb.model.OrderView;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "OrderManagementController", value = "/OrderManagement")
public class OrderManagementController extends HttpServlet {
    private OrderDao orderDAO = new OrderDao();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<OrderView> orders = orderDAO.getAllOrders();
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("OrderManagement.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
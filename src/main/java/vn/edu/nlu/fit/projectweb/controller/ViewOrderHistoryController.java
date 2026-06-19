package vn.edu.nlu.fit.projectweb.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.projectweb.dao.AccountDao;
import vn.edu.nlu.fit.projectweb.dao.CategoryDao;
import vn.edu.nlu.fit.projectweb.dao.OrderDao;
import vn.edu.nlu.fit.projectweb.model.Category;
import vn.edu.nlu.fit.projectweb.model.Orders;
import vn.edu.nlu.fit.projectweb.model.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@WebServlet(name = "ViewOrderHistoryController", value = "/ViewOrderHistory")
public class ViewOrderHistoryController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("auth");

        if (user == null) {
            response.sendRedirect("html/login.jsp");
            return;
        }

        OrderDao dao = new OrderDao();
        List<Orders> orders = dao.getOrdersByUserId(user.getUserId());

        request.setAttribute("orders", orders);
        request.getRequestDispatcher("html/ViewOrderHistory.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
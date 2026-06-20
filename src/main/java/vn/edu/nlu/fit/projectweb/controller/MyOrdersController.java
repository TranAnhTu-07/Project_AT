package vn.edu.nlu.fit.projectweb.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import vn.edu.nlu.fit.projectweb.model.User;
import vn.edu.nlu.fit.projectweb.model.Orders;
import vn.edu.nlu.fit.projectweb.dao.OrderDao;
import java.util.List;

import java.io.IOException;

@WebServlet(name = "MyOrdersController", value = "/my-orders")
public class MyOrdersController extends HttpServlet {

    public OrderDao OrderDao = new OrderDao();

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {

        User user =
                (User) req.getSession()
                        .getAttribute("account");

        int userId = user.getUserId();

        List<Orders> orders = OrderDao.getOrdersByUserId(userId);

//        request.setAttribute("orders", orders);
//        request.getRequestDispatcher("/my-orders.jsp").forward(request, resp);

        req.setAttribute("orders", orders);

        req.getRequestDispatcher(
                        "/html/my-orders.jsp")
                .forward(req,resp);
    }
}
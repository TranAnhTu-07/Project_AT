package vn.edu.nlu.fit.projectweb.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import vn.edu.nlu.fit.projectweb.dao.OrderDao;
import vn.edu.nlu.fit.projectweb.model.OrderDetail;
import vn.edu.nlu.fit.projectweb.model.Orders;
import vn.edu.nlu.fit.projectweb.dao.OrderDetailDAO;
import java.util.List;
import java.io.IOException;

@WebServlet(name = "OrderDetailController", value = "/order-detail")
public class OrderDetailController extends HttpServlet {

    private OrderDao orderDao = new OrderDao();
    private OrderDetailDAO detailDao = new OrderDetailDAO();

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp)
            throws ServletException, IOException {

        String idParam = req.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu mã đơn hàng");
            return;
        }

        int id = Integer.parseInt(idParam);

        Orders order = orderDao.getOrderById(id);
        List<OrderDetail> details = detailDao.getDetailsByOrder(id);

        req.setAttribute("order", order);
        req.setAttribute("details", details);

        req.getRequestDispatcher("/html/order-detail.jsp")
                .forward(req, resp);
    }
}
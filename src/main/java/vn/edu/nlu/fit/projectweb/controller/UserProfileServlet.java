package vn.edu.nlu.fit.projectweb.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.projectweb.dao.AccountDao;
import vn.edu.nlu.fit.projectweb.dao.OrderDao;
import vn.edu.nlu.fit.projectweb.model.Account;
import vn.edu.nlu.fit.projectweb.model.Orders;
import vn.edu.nlu.fit.projectweb.model.User;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserProfileServlet", value = "/profile")
public class UserProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Lấy USER từ Session ra (Sửa dòng này)
        HttpSession session = request.getSession();
        // Vì bên LoginController m lưu là User, nên lấy ra cũng phải là User
        User user = (User) session.getAttribute("account");

        // Nếu chưa đăng nhập -> Đuổi về trang login
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/html/login.jsp");
            return;
        }

        // 2. Lấy lịch sử mua hàng
        OrderDao orderDao = new OrderDao();

        // Lấy ID của User để tìm đơn hàng
        // Lưu ý: Mở file User.java xem hàm lấy ID là getId() hay getUserId() nhé.
        // Tao đang để mặc định là getId() (thường thấy nhất).
        int uid = user.getUserId();

        List<Orders> listOrders = orderDao.getOrdersByUserId(uid);

        // 3. Gửi dữ liệu sang JSP
        request.setAttribute("user", user); // Gửi thông tin user (để hiện tên, email, sđt...)
        request.setAttribute("orders", listOrders); // Gửi danh sách đơn hàng

        request.getRequestDispatcher("/html/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
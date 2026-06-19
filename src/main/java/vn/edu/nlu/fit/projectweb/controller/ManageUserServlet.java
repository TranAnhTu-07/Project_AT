package vn.edu.nlu.fit.projectweb.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.projectweb.dao.UserDao;
import vn.edu.nlu.fit.projectweb.model.User;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ManageUserServlet", value = "/admin/users")
public class ManageUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String search = request.getParameter("search");
        // 1. Gọi DAO lấy danh sách
        UserDao dao = new UserDao();
        List<User> list = dao.getAllUsers();

        // 2. Kiểm tra: Nếu có từ khóa thì tìm, không thì lấy hết
        if (search != null && !search.trim().isEmpty()) {
            list = dao.searchUsers(search.trim());
        } else {
            list = dao.getAllUsers();
        }

        // 2. Tính toán số liệu thống kê từ danh sách vừa lấy
        int total = list.size(); // Tổng số user
        int active = 0;          // Số người đang hoạt động
        int locked = 0;          // Số người bị khóa

        for (User u : list) {
            if (u.getStatus() == 1) {
                active++;
            } else {
                locked++;
            }
        }

        // 3. Gửi các con số này sang file JSP
        request.setAttribute("listUsers", list); // Gửi danh sách để hiện bảng
        request.setAttribute("totalUsers", total);
        request.setAttribute("activeUsers", active);
        request.setAttribute("lockedUsers", locked);

        // 4. Chuyển hướng
        request.getRequestDispatcher("/html/quanlyuser.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
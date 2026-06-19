package vn.edu.nlu.fit.projectweb.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "LogoutServlet", value = "/Logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Lấy session hiện tại
        HttpSession session = request.getSession(false);

        // 2. Nếu session tồn tại -> Xóa nó đi (Đăng xuất)
        if (session != null) {
            session.invalidate();
        }

        // 3. Đá về trang danh sách sản phẩm (Trang chủ)
        // Dùng request.getContextPath() để không bị lỗi đường dẫn
        response.sendRedirect(request.getContextPath() + "/ListProduct");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
package vn.edu.nlu.fit.projectweb.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.projectweb.dao.UserDao;

import java.io.IOException;

@WebServlet(name = "UpdateStatusServlet", value = "/admin/users-status")
public class UpdateStatusServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Lấy id và trạng thái muốn đổi từ link
        int id = Integer.parseInt(request.getParameter("id"));
        int status = Integer.parseInt(request.getParameter("status"));

        // 2. Gọi DAO thực hiện
        UserDao dao = new UserDao();
        dao.updateStatus(id, status);

        // 3. Xong việc thì load lại trang danh sách
        response.sendRedirect("users"); // Chuyển hướng về cái Servlet quản lý user lúc nãy

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
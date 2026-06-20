package vn.edu.nlu.fit.projectweb.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.projectweb.dao.UserDao;
import vn.edu.nlu.fit.projectweb.model.User;

import java.io.IOException;

@WebServlet(name = "BaoMatKhoaController", value = "/BaoMatKhoa")
public class BaoMatKhoaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("html/BaoMatKhoa.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        vn.edu.nlu.fit.projectweb.model.User currentUser = (vn.edu.nlu.fit.projectweb.model.User) session.getAttribute("account");

        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/html/login.jsp");
            return;
        }

        try {
            vn.edu.nlu.fit.projectweb.dao.UserDao userDao = new vn.edu.nlu.fit.projectweb.dao.UserDao();
            userDao.reportLostKey(currentUser.getUserId());
            request.setAttribute("successAlert", "Báo mất khóa thành công! Hệ thống sẽ chuyển về Trang chủ.");
            request.getRequestDispatcher("html/BaoMatKhoa.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "Lỗi hệ thống, vui lòng thử lại sau!");
            request.getRequestDispatcher("html/BaoMatKhoa.jsp").forward(request, response);
        }
    }
}
package vn.edu.nlu.fit.projectweb.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.projectweb.dao.UserDao;
import vn.edu.nlu.fit.projectweb.model.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "CapNhatKhoaController", value = "/CapNhatKhoa")
@MultipartConfig(
        fileSizeThreshold = 1024 * 10,
        maxFileSize = 1024 * 50,
        maxRequestSize = 1024 * 100
)
public class CapNhatKhoaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("account");
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/html/login.jsp");
            return;
        }
        request.getRequestDispatcher("html/CapNhatKhoa.jsp").forward(request, response);
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
            Part filePart = request.getPart("keyFile");

            if (filePart == null || filePart.getSize() == 0) {
                request.setAttribute("errorMsg", "Vui lòng chọn file public_key.txt hợp lệ!");
                request.getRequestDispatcher("html/CapNhatKhoa.jsp").forward(request, response);
                return;
            }

            StringBuilder sb = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(filePart.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line.trim());
                }
            }
            String newPublicKey = sb.toString();

            if (newPublicKey.isEmpty()) {
                request.setAttribute("errorMsg", "File đã chọn trống rỗng, không chứa mã khóa!");
                request.getRequestDispatcher("html/CapNhatKhoa.jsp").forward(request, response);
                return;
            }

            vn.edu.nlu.fit.projectweb.dao.UserDao userDao = new vn.edu.nlu.fit.projectweb.dao.UserDao();
            userDao.updateNewKey(currentUser.getUserId(), newPublicKey);

            request.setAttribute("successAlert", "Cập nhật khóa mới thành công! Hệ thống sẽ chuyển về Trang chủ.");
            request.getRequestDispatcher("html/CapNhatKhoa.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "Lỗi hệ thống khi cập nhật khóa, vui lòng thử lại!");
            request.getRequestDispatcher("html/CapNhatKhoa.jsp").forward(request, response);
        }
    }
}
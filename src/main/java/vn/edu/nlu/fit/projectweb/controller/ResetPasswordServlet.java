package vn.edu.nlu.fit.projectweb.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.projectweb.dao.UserDao;
import vn.edu.nlu.fit.projectweb.model.User;
import vn.edu.nlu.fit.projectweb.utils.MD5Utils;
import vn.edu.nlu.fit.projectweb.utils.Validator;

import java.io.IOException;

@WebServlet(name = "ResetPasswordServlet", value = "/ResetPassword")
public class ResetPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");
        UserDao dao = new UserDao();
        User u = dao.getUserByToken(token);

        if (u == null) {
            // Token sai hoặc hết hạn -> Về Login báo lỗi
            response.sendRedirect("html/login.jsp?error=Token không hợp lệ!");
        } else {

            request.setAttribute("email", u.getEmail());
            request.setAttribute("showResetForm", true); // Cờ để hiện form (nếu dùng chung JSP)
            request.getRequestDispatcher("html/reset.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type"); // Lấy loại: "phone" hay null (email)
        String newPass = request.getParameter("password");
        String confirmPass = request.getParameter("confirm_password");
        UserDao dao = new UserDao();

        //0 check
        if (!Validator.checkPassword(newPass)) {
            request.setAttribute("error", "Mật khẩu yếu! Cần 8 ký tự, Hoa, Thường, Số, Ký tự đặt biệt.");

            // Logic trả về y chang đoạn check khớp mật khẩu của mày
            if ("phone".equals(type)) {
                request.setAttribute("phone", request.getParameter("phone"));
                request.setAttribute("type", "phone"); // Nên gửi kèm cái type lại cho chắc
                request.getRequestDispatcher("html/forgot.jsp").forward(request, response);
            } else {
                request.setAttribute("email", request.getParameter("email"));
                request.getRequestDispatcher("html/reset.jsp").forward(request, response);
            }
            return; // Dừng ngay lập tức
        }
        // ==========================================================

        // 2. Check pass nhập lại
        if (!newPass.equals(confirmPass)) {
            request.setAttribute("error", "Mật khẩu xác nhận không khớp!");
            if ("phone".equals(type)) {
                request.setAttribute("phone", request.getParameter("phone"));
                request.getRequestDispatcher("html/forgot.jsp").forward(request, response);
            } else {
                request.setAttribute("email", request.getParameter("email"));
                request.getRequestDispatcher("html/reset.jsp").forward(request, response);
            }
            return;
        }

        String hashPass = MD5Utils.hash(newPass);

        // ============ TRƯỜNG HỢP 1: ĐỔI PASS QUA SĐT ============
        if ("phone".equals(type)) {
            String phone = request.getParameter("phone");

            // Check xem sdt có tồn tại không
            if (!dao.checkPhoneExist(phone)) {
                request.setAttribute("error", "Số điện thoại này chưa đăng ký!");
                request.getRequestDispatcher("html/forgot.jsp").forward(request, response);
                return;
            }
            // Đổi pass
            dao.changePasswordByPhone(phone, hashPass);
            // Về Login báo thành công
            response.sendRedirect("html/login.jsp?msg=reset_success");
        }

        // ============ ĐỔI PASS QUA EMAIL ============
        else {
            String email = request.getParameter("email");
            dao.changePassword(email, hashPass);
            response.sendRedirect("html/login.jsp?msg=reset_success");
        }
    }
}
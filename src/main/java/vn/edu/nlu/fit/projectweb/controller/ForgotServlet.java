package vn.edu.nlu.fit.projectweb.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.projectweb.dao.UserDao;
import vn.edu.nlu.fit.projectweb.utils.EmailUtils;

import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "ForgotServlet", value = "/Forgot")
public class ForgotServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        UserDao dao = new UserDao();

        // 1. Kiểm tra email có trong DB không
        if (!dao.checkEmailExist(email)) {
            request.setAttribute("error", "Email này chưa được đăng ký!");
            request.getRequestDispatcher("html/forgot.jsp").forward(request, response);
            return;
        }

        // 2. Tạo Token & Lưu vào DB
        String token = UUID.randomUUID().toString();
        dao.updateToken(email, token);

        // 3. Tạo đường dẫn (LƯU Ý: Sửa 'project_web_war' thành tên project của mày)
        // request.getContextPath() laasy tên project tự động
        String link = "http://localhost:8080" + request.getContextPath() + "/ResetPassword?token=" + token;

        // 4. Nội dung Email
        String content = "<p>Chào bạn,</p>"
                + "<p>Ai đó vừa yêu cầu đặt lại mật khẩu. Nếu là bạn, hãy bấm vào link dưới đây:</p>"
                + "<p><a href='" + link + "' style='padding: 10px; background: blue; color: white;'>ĐẶT LẠI MẬT KHẨU</a></p>";

        // 5. Gửi mail (Chạy ngầm)
        new Thread(() -> EmailUtils.send(email, "Yêu cầu khôi phục mật khẩu", content)).start();

        // 6. Thông báo ra màn hình
        request.setAttribute("message", "Link khôi phục đã được gửi vào email của bạn!");
        request.getRequestDispatcher("html/forgot.jsp").forward(request, response);
    }
}

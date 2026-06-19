package vn.edu.nlu.fit.projectweb.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.nlu.fit.projectweb.dao.UserDao;
import vn.edu.nlu.fit.projectweb.model.User;
import vn.edu.nlu.fit.projectweb.utils.EmailUtils;
import vn.edu.nlu.fit.projectweb.utils.MD5Utils;
import vn.edu.nlu.fit.projectweb.utils.Validator;

import java.io.IOException;
import java.util.UUID;

@WebServlet(name = "RegisterServlet", value = "/Register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // Lấy action để biết đang đăng ký tab nào (email hay phone)
        String action = request.getParameter("action");
        UserDao dao = new UserDao();

        // ================= TRƯỜNG HỢP 1: ĐĂNG KÝ BẰNG EMAIL =================
        if ("register_email".equals(action)) {
            String fullname = request.getParameter("fullname");
            String email = request.getParameter("email");
            String pass = request.getParameter("password");

            // --- Validate Mật khẩu ---
            if (!Validator.checkPassword(pass)) {
                request.setAttribute("error", "Mật khẩu yếu! Cần 8 ký tự, Hoa, Thường, Số, Ký tự đặt biệt.");
                request.setAttribute("fullname", fullname);
                request.setAttribute("email", email);
                request.getRequestDispatcher("html/register.jsp").forward(request, response);
                return;
            }

            // --- Check trùng Email ---
            if (dao.checkEmailExist(email)) {
                request.setAttribute("error", "Email đã tồn tại!");
                request.setAttribute("fullname", fullname);
                request.getRequestDispatcher("html/register.jsp").forward(request, response);
                return;
            }

            // --- Tạo User mới ---
            String hashPass = MD5Utils.hash(pass);
            String token = UUID.randomUUID().toString(); // Token xác thực

            User u = new User();
            u.setFullName(fullname);
            u.setEmail(email);
            u.setPassword(hashPass);
            u.setToken(token);
            u.setStatus(0); // QUAN TRỌNG: Status = 0 (Chưa kích hoạt)
            u.setRoleId(2);
            u.setPhone(null); // Đăng ký email thì không có sđt

            try {
                dao.registerUser(u);

                // --- GỬI MAIL KÍCH HOẠT (ĐÃ BẬT LẠI) ---
                // Lưu ý: Thay 'project_web_war' bằng tên đúng project của mày trên URL
                // Ví dụ: http://localhost:8080/Ten_Project_Cua_May/Verify...
                String link = "http://localhost:8080/project_web_war/Verify?token=" + token;

                String content = "<div style='font-family: Arial, sans-serif; padding: 20px; background-color: #f4f4f4;'>" +
                        "<div style='max-width: 600px; margin: 0 auto; background: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0,0,0,0.1);'>" +
                        "<h2 style='color: #333;'>Xác thực tài khoản</h2>" +
                        "<p>Chào <strong>" + fullname + "</strong>,</p>" +
                        "<p>Cảm ơn bạn đã đăng ký. Vui lòng nhấn vào nút bên dưới để kích hoạt tài khoản:</p>" +
                        "<div style='text-align: center; margin: 20px 0;'>" +
                        "<a href='" + link + "' style='background-color: #007bff; color: white; padding: 12px 24px; text-decoration: none; border-radius: 5px; font-weight: bold;'>KÍCH HOẠT NGAY</a>" +
                        "</div>" +
                        "<p>Hoặc copy link này: " + link + "</p>" +
                        "</div></div>";

                // Gửi mail trong luồng riêng (Thread) để web không bị đơ
                new Thread(() -> EmailUtils.send(email, "Kích hoạt tài khoản - Web Camera", content)).start();

                // Chuyển sang trang Login và báo check mail
                response.sendRedirect("html/login.jsp?msg=success");

            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("error", "Lỗi hệ thống! Không gửi được mail.");
                request.getRequestDispatcher("html/register.jsp").forward(request, response);
            }
        }

        // ================= TRƯỜNG HỢP 2: ĐĂNG KÝ BẰNG SỐ ĐIỆN THOẠI =================
        else if ("register_phone".equals(action)) {
            String fullname = request.getParameter("fullname");
            String phone = request.getParameter("phone");
            String pass = request.getParameter("password");

            //0.CHECK
            if (!Validator.checkPassword(pass)) {
                request.setAttribute("error", "Mật khẩu yếu! Cần 8 ký tự, Hoa, Thường, Số, Ký tự đặt biệt.");
                request.setAttribute("fullname", fullname);
                request.setAttribute("phone", phone);
                request.getRequestDispatcher("html/register.jsp").forward(request, response);
                return;
            }

            // 1. Hash mật khẩu
            String hashPass = MD5Utils.hash(pass);

            // 2. Tạo User object
            User u = new User();
            u.setFullName(fullname);
            u.setPhone(phone);
            u.setPassword(hashPass);

            // Vì đã xác thực OTP (fake) ở Front-end rồi nên cho Active luôn
            u.setStatus(1);
            u.setRoleId(2);
            u.setToken(null); // Không cần token kích hoạt

            // QUAN TRỌNG: Database thường yêu cầu cột Email không được để trống (NOT NULL).
            // Ta tạo một email giả từ số điện thoại để "lừa" Database.
            u.setEmail(phone + "@local.store");

            try {
                // Kiểm tra sơ bộ xem SĐT đã tồn tại chưa (nếu DAO chưa có hàm checkPhone thì thôi, để DB tự bắt lỗi Duplicate)
                // Lưu vào DB
                dao.registerUser(u);

                // Đăng ký xong chuyển thẳng về Login, báo "Kích hoạt thành công"
                response.sendRedirect("html/login.jsp?msg=activated");

            } catch (Exception e) {
                e.printStackTrace();
                // Lỗi này thường do trùng SĐT (vì SĐT giờ đóng vai trò như Email giả)
                request.setAttribute("error", "Đăng ký thất bại! Số điện thoại có thể đã tồn tại.");
                request.getRequestDispatcher("html/register.jsp").forward(request, response);
            }
        }
    }
}
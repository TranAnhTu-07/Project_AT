package vn.edu.nlu.fit.projectweb.dao;

import vn.edu.nlu.fit.projectweb.model.User;

public class UserDao extends BaseDao {
    // 1. Kiểm tra Email tồn tại
    public boolean checkEmailExist(String email) {
        return get().withHandle(h ->
                h.createQuery("SELECT COUNT(*) FROM Users WHERE email = :email")
                        .bind("email", email)
                        .mapTo(Integer.class)
                        .one() > 0
        );
    }

    // 2. Đăng ký User mới
    public void registerUser(User u) {
        get().useHandle(h ->
                h.createUpdate("INSERT INTO Users (full_name, email, password, role_id, status, token, phone) " +
                                "VALUES (:fullName, :email, :password, :roleId, :status, :token, :phone)")
                        .bindBean(u) // Tự động lấy các thuộc tính của User (fullName, email...) điền vào SQL
                        .execute()
        );
    }

    // 3. Login (Tìm user bằng email và pass đã mã hóa)
    public User login(String email, String password) {
        return get().withHandle(h ->
                h.createQuery("SELECT * FROM Users WHERE (email = :username OR phone = :username) AND password = :password")
                        .bind("username", email)
                        .bind("password", password)
                        .mapToBean(User.class) // Tự map cột SQL vào class User
                        .findFirst()
                        .orElse(null)
        );
    }

    // 4. Lấy User bằng Token (Để xác thực email)
    public User getUserByToken(String token) {
        return get().withHandle(h ->
                h.createQuery("SELECT * FROM Users WHERE token = :token")
                        .bind("token", token)
                        .mapToBean(User.class)
                        .findFirst()
                        .orElse(null)
        );
    }

    // 5. Kích hoạt tài khoản
    public void activateUser(String token) {
        get().useHandle(h ->
                h.createUpdate("UPDATE Users SET status = 1, token = NULL WHERE token = :token")
                        .bind("token", token)
                        .execute()
        );
    }

    // 6. Kiểm tra SĐT có tồn tại không
    public boolean checkPhoneExist(String phone) {
        return get().withHandle(h ->
                h.createQuery("SELECT COUNT(*) FROM Users WHERE phone = :phone")
                        .bind("phone", phone)
                        .mapTo(Integer.class)
                        .one() > 0
        );
    }

    // 7. Đổi mật khẩu dựa trên SĐT (Dùng cho luồng Fake OTP)
    public void changePasswordByPhone(String phone, String newHashPassword) {
        get().useHandle(h ->
                h.createUpdate("UPDATE Users SET password = :password WHERE phone = :phone")
                        .bind("password", newHashPassword)
                        .bind("phone", phone)
                        .execute()
        );
    }

    // 8. Đổi mật khẩu Email (Dùng cho luồng Email Link) - Nếu chưa có thì thêm vào
    public void changePassword(String email, String newHashPassword) {
        get().useHandle(h ->
                h.createUpdate("UPDATE Users SET password = :password, token = NULL WHERE email = :email")
                        .bind("password", newHashPassword)
                        .bind("email", email)
                        .execute()
        );
    }

    // 9. Update Token (Dùng lúc gửi mail) - Nếu chưa có thì thêm vào
    public void updateToken(String email, String token) {
        get().useHandle(h ->
                h.createUpdate("UPDATE Users SET token = :token WHERE email = :email")
                        .bind("token", token)
                        .bind("email", email)
                        .execute()
        );
    }

    // 10. Lấy danh sách tất cả User (Dành cho trang Admin Quản lý User)
    public java.util.List<User> getAllUsers() {
        return get().withHandle(handle -> {
            return handle.createQuery("SELECT * FROM users")
                    .map((rs, ctx) -> {
                        // Map thủ công cho chắc ăn, khớp 100% với cái hình DB m gửi
                        User u = new User();
                        u.setUserId(rs.getInt("user_id"));           // Cột DB là user_id
                        u.setFullName(rs.getString("full_name")); // Cột DB là full_name
                        u.setEmail(rs.getString("email"));
                        u.setPhone(rs.getString("phone"));
                        u.setRoleId(rs.getInt("role_id"));       // Cột DB là role_id
                        u.setStatus(rs.getInt("status"));
                        // M có thể map thêm password hoặc created_at nếu cần, nhưng hiển thị thì k cần pass

                        return u;
                    }).list();
        });
    }

    //11
    public void updateStatus(int userId, int status) {
        get().useHandle(handle ->
                handle.createUpdate("UPDATE users SET status = :status WHERE user_id = :id")
                        .bind("status", status)
                        .bind("id", userId)
                        .execute()
        );
    }

    //12
    public java.util.List<User> searchUsers(String keyword) {
        return get().withHandle(handle -> {
            String sql = "SELECT * FROM users WHERE full_name LIKE :key OR email LIKE :key OR phone LIKE :key";

            return handle.createQuery(sql)
                    .bind("key", "%" + keyword + "%")
                    .map((rs, ctx) -> {
                        vn.edu.nlu.fit.projectweb.model.User u = new vn.edu.nlu.fit.projectweb.model.User();
                        u.setUserId(rs.getInt("user_id"));
                        u.setFullName(rs.getString("full_name"));
                        u.setEmail(rs.getString("email"));
                        u.setPhone(rs.getString("phone"));
                        u.setRoleId(rs.getInt("role_id"));
                        u.setStatus(rs.getInt("status"));
                        return u;
                    }).list();
        });
    }
}


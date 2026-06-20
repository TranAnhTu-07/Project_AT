package vn.edu.nlu.fit.projectweb.dao;

import vn.edu.nlu.fit.projectweb.model.OrderView;
import vn.edu.nlu.fit.projectweb.model.Orders;
import vn.edu.nlu.fit.projectweb.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao extends BaseDao {

    // --- LẤY 1 ĐƠN HÀNG THEO ID (dùng cho trang detail) ---
    public Orders getOrderById(int orderId) {

        Orders o = null;

        String sql = """
            
                SELECT
                order_id, customer_name, phone_number, email,
                shipping_address, total_amount, payment_method,
                order_date, order_status, digital_signature, signature_status
            FROM orders
            WHERE order_id = ?
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    o = mapResultSetToOrder(rs);
                }
            }

        } catch (Exception e) {
            System.out.println("LỖI getOrderById: " + e.getMessage());
            e.printStackTrace();
        }

        return o;
    }

    // --- LẤY DANH SÁCH ĐƠN HÀNG THEO USER (tên cũ, giữ lại cho các class khác đang gọi) ---
    public List<Orders> getOrdersByUser(int userId) {
        return getOrdersByUserId(userId);
    }

    // --- LẤY TẤT CẢ ĐƠN HÀNG (CHO ADMIN) ---
    public List<OrderView> getAllOrders() {

        List<OrderView> list = new ArrayList<>();

        String sql = """
            SELECT o.order_id, o.order_date, o.total_amount,
                   u.full_name, u.email,
                   os.status_name
            FROM Orders o
            JOIN Users u ON o.user_id = u.user_id
            JOIN OrderStatus os ON o.status_id = os.order_status_id
            ORDER BY o.order_date DESC
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                OrderView o = new OrderView();

                o.setOrderId(rs.getInt("order_id"));
                o.setFullName(rs.getString("full_name"));
                o.setEmail(rs.getString("email"));
                o.setOrderDate(rs.getDate("order_date").toString());
                o.setTotalAmount(rs.getDouble("total_amount"));
                o.setStatusName(rs.getString("status_name"));

                list.add(o);
            }

        } catch (Exception e) {
            System.out.println("LỖI getAllOrders: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    // --- LẤY DANH SÁCH ĐƠN HÀNG THEO USER ID ---
    public List<Orders> getOrdersByUserId(int userId) {

        List<Orders> list = new ArrayList<>();

        String sql = """
            SELECT
                o.order_id, o.customer_name, o.phone_number, o.email,
                o.shipping_address, o.total_amount, o.payment_method,
                o.order_date, o.order_status, o.digital_signature, o.signature_status
            FROM orders o
            WHERE o.user_id = ?
            ORDER BY o.order_date DESC
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToOrder(rs));
                }
            }

        } catch (Exception e) {
            System.out.println("LỖI getOrdersByUserId: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    // --- HÀM PHỤ: map 1 dòng ResultSet thành Orders, tránh lặp code ---
    private Orders mapResultSetToOrder(ResultSet rs) throws SQLException {

        Orders o = new Orders();

        o.setOrderId(rs.getInt("order_id"));
        o.setCustomerName(rs.getString("customer_name"));
        o.setPhoneNumber(rs.getString("phone_number"));
        o.setEmail(rs.getString("email"));
        o.setShippingAddress(rs.getString("shipping_address"));
        o.setTotalAmount(rs.getDouble("total_amount"));
        o.setPaymentMethod(rs.getString("payment_method"));
        o.setOrderDate(rs.getDate("order_date"));
        o.setOrderStatus(rs.getString("order_status"));
        o.setDigitalSignature(rs.getString("digital_signature"));
        o.setSignatureStatus(rs.getInt("signature_status"));

        return o;
    }
}

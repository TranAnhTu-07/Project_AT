package vn.edu.nlu.fit.projectweb.dao;

import vn.edu.nlu.fit.projectweb.model.OrderView;
import vn.edu.nlu.fit.projectweb.model.Orders;
import vn.edu.nlu.fit.projectweb.model.OrderDetail;
import vn.edu.nlu.fit.projectweb.utils.DBConnection;
import vn.edu.nlu.fit.projectweb.utils.JDBIConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class OrderDao extends BaseDao{
    // --- HÀM LẤY CHI TIẾT ĐƠN HÀNG (GIỮ LẠI ĐỂ DÙNG CHO TRANG DETAIL) ---
    public class OrderDAO {

        public List<Orders> getOrdersByUser(int userId){

            String sql = """
            SELECT *
            FROM orders
            WHERE user_id = ?
            ORDER BY order_date DESC
        """;

            return get().withHandle(handle ->
                    handle.createQuery(sql)
                            .bind(0,userId)
                            .mapToBean(Orders.class)
                            .list()
            );
        }

        public Orders getOrderById(int id){

            String sql = """
            SELECT *
            FROM orders
            WHERE order_id = ?
        """;

            return get().withHandle(handle ->
                    handle.createQuery(sql)
                            .bind(0,id)
                            .mapToBean(Orders.class)
                            .findOne()
                            .orElse(null)
            );
        }
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
            e.printStackTrace();
        }

        return list;
    }

    public List<Orders> getOrdersByUserId(int userId) {

        List<Orders> list = new ArrayList<>();

        String sql = """
        SELECT
            o.order_id,
            o.customer_name,
            o.phone_number,
            o.email,
            o.shipping_address,
            o.total_amount,
            o.payment_method,
            o.order_date,
            o.order_status,
            o.digital_signature,
            o.signature_status
        FROM orders o
        WHERE o.user_id = ?
        ORDER BY o.order_date DESC
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Orders o = new Orders();

                    o.setOrderId(
                            rs.getInt("order_id"));

                    o.setCustomerName(
                            rs.getString("customer_name"));

                    o.setPhoneNumber(
                            rs.getString("phone_number"));

                    o.setEmail(
                            rs.getString("email"));

                    o.setShippingAddress(
                            rs.getString("shipping_address"));

                    o.setTotalAmount(
                            rs.getDouble("total_amount"));

                    o.setPaymentMethod(
                            rs.getString("payment_method"));

                    o.setOrderDate(
                            rs.getDate("order_date"));

                    o.setOrderStatus(
                            rs.getInt("order_status"));

                    o.setDigitalSignature(
                            rs.getString("digital_signature"));

                    o.setSignatureStatus(
                            rs.getInt("signature_status"));

                    list.add(o);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}

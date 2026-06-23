package vn.edu.nlu.fit.projectweb.dao;

import vn.edu.nlu.fit.projectweb.model.OrderDetail;
import vn.edu.nlu.fit.projectweb.model.OrderView;
import vn.edu.nlu.fit.projectweb.model.Orders;
import vn.edu.nlu.fit.projectweb.utils.DBConnection;
import vn.edu.nlu.fit.projectweb.model.Product;
import vn.edu.nlu.fit.projectweb.utils.EmailUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDao extends BaseDao {

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
                    int rawStatus = rs.getInt("signature_status");
                    o = mapResultSetToOrder(rs);

                    if (rawStatus == 2) {
                        Orders finalO = o;
                        new Thread(() -> {
                            sendAlert(finalO);
                            updateSignatureStatusToNotified(finalO.getOrderId());
                        }).start();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("LỖI getOrderById: " + e.getMessage());
            e.printStackTrace();
        }
        return o;
    }

    public List<Orders> getOrdersByUser(int userId) {
        return getOrdersByUserId(userId);
    }

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
                    int rawStatus = rs.getInt("signature_status");
                    Orders o = mapResultSetToOrder(rs);
                    list.add(o);

                    if (rawStatus == 2) {
                        new Thread(() -> {
                            sendAlert(o);
                            updateSignatureStatusToNotified(o.getOrderId());
                        }).start();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("LỖI getOrdersByUserId: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    private void sendAlert(Orders o) {
        String subject = "⚠️ CẢNH BÁO BẢO MẬT: Đơn hàng #" + o.getOrderId() + " bị chỉnh sửa trái phép!";

        String body = "<h3>Xin chào " + o.getCustomerName() + ",</h3>"
                + "<p>Hệ thống giám sát toàn vẹn dữ liệu vừa phát hiện đơn hàng số <b>#" + o.getOrderId() + "</b> của bạn "
                + "đã bị chỉnh sửa trái phép (Tên/SĐT/Email/Địa chỉ/Số tiền) trực tiếp từ Database.</p>"
                + "<p><b>Chi tiết đơn hàng hiện tại trong hệ thống:</b></p>"
                + "<ul>"
                + "<li>Mã đơn hàng: " + o.getOrderId() + "</li>"
                + "<li>Số tiền hiện tại: " + String.format("%,.0f", o.getTotalAmount()) + " VNĐ</li>"
                + "<li>Người nhận: " + o.getCustomerName() + "</li>"
                + "</ul>"
                + "<p style='color: red; font-weight: bold;'>⚠️ Cảnh báo: Chữ ký số RSA của đơn hàng này đã bị PHÁ VỠ hoàn toàn và không còn giá trị pháp lý.</p>"
                + "<p>Vui lòng liên hệ bộ phận hỗ trợ kỹ thuật để kiểm tra nhật ký chỉnh sửa hệ thống.</p>"
                + "<br><p>Trân trọng,<br>Hệ thống Giám sát An toàn Chữ ký số.</p>";

        EmailUtils.send(o.getEmail(), subject, body);
        System.out.println(o.getOrderId());
    }

    private void updateSignatureStatusToNotified(int orderId) {
        String sql = "UPDATE orders SET signature_status = 4 WHERE order_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("LỖI updateSignatureStatusToNotified: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<OrderDetail> getOrderDetailsByOrderId(int orderId) {
        String sql = "SELECT od.detail_id, od.order_id, od.product_id, od.quantity, od.price, " +
                "p.ProductName " +
                "FROM order_details od " +
                "JOIN products p ON od.product_id = p.ProductID " +
                "WHERE od.order_id = :orderId";

        try {
            return get().withHandle(handle ->
                    handle.createQuery(sql)
                            .bind("orderId", orderId)
                            .map((rs, ctx) -> {
                                OrderDetail detail = new OrderDetail();
                                detail.setDetailId(rs.getInt("detail_id"));
                                detail.setOrderId(rs.getInt("order_id"));
                                detail.setProductId(rs.getInt("product_id"));
                                detail.setQuantity(rs.getInt("quantity"));
                                detail.setPrice(rs.getDouble("price"));

                                Product product = new Product();
                                product.setProductName(rs.getString("ProductName"));
                                detail.setProduct(product);
                                return detail;
                            })
                            .list()
            );
        } catch (Exception e) {
            System.out.println("LỖI getOrderDetailsByOrderId: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

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

        int sigStatus = rs.getInt("signature_status");
        if (sigStatus == 4) {
            o.setSignatureStatus(2);
        } else {
            o.setSignatureStatus(sigStatus);
        }
        return o;
    }

    public int insertOrder(Orders order, List<OrderDetail> cartItems) {
        String sqlOrder = "INSERT INTO orders (user_id, customer_name, phone_number, email, shipping_address, " +
                "total_amount, payment_method, order_date, order_status, signature_status) " +
                "VALUES (:userId, :customerName, :phoneNumber, :email, :shippingAddress, " +
                ":totalAmount, :paymentMethod, NOW(), 1, 0)";

        String sqlDetail = "INSERT INTO order_details (order_id, product_id, quantity, price) " +
                "VALUES (:orderId, :productId, :quantity, :price)";

        try {
            Long generatedId = get().inTransaction(handle -> {
                org.jdbi.v3.core.statement.Update update = handle.createUpdate(sqlOrder);
                if (order.getUserId() > 0) {
                    update.bind("userId", order.getUserId());
                } else {
                    update.bindNull("userId", java.sql.Types.INTEGER);
                }

                update.bind("totalAmount",    order.getTotalAmount())
                        .bind("customerName",   order.getCustomerName())
                        .bind("phoneNumber",    order.getPhoneNumber())
                        .bind("email",          order.getEmail())
                        .bind("shippingAddress",order.getShippingAddress())
                        .bind("paymentMethod",  order.getPaymentMethod());

                Long orderId = update.executeAndReturnGeneratedKeys("order_id")
                        .mapTo(Long.class)
                        .one();

                if (orderId != null && orderId > 0 && cartItems != null && !cartItems.isEmpty()) {
                    org.jdbi.v3.core.statement.PreparedBatch batch = handle.prepareBatch(sqlDetail);
                    for (OrderDetail item : cartItems) {
                        batch.bind("orderId",    orderId)
                                .bind("productId",  item.getProductId())
                                .bind("quantity",   item.getQuantity())
                                .bind("price",      item.getPrice())
                                .add();
                    }
                    batch.execute();
                }
                return orderId;
            });
            return generatedId != null ? generatedId.intValue() : -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}

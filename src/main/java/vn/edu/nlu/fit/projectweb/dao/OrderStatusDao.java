package vn.edu.nlu.fit.projectweb.dao;

import vn.edu.nlu.fit.projectweb.model.OrderStatus;
import vn.edu.nlu.fit.projectweb.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderStatusDao {
    public List<OrderStatus> getStatusByOrderId(int orderId) throws Exception {
        List<OrderStatus> list = new ArrayList<>();

        String sql = "SELECT * FROM order_status_history WHERE order_id = ? ORDER BY status_time";

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, orderId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            OrderStatus status = new OrderStatus();
            status.setStatus(rs.getString("status"));
            status.setStatusTime(rs.getTimestamp("status_time"));
            status.setDescription(rs.getString("description"));

            list.add(status);
        }
        return list;
    }
}

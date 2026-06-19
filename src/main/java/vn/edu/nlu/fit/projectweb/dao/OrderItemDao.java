package vn.edu.nlu.fit.projectweb.dao;

import vn.edu.nlu.fit.projectweb.model.OrderDetail;
import vn.edu.nlu.fit.projectweb.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDao {
    public List<OrderDetail> getItemsByOrderId(int orderId) throws Exception {
        List<OrderDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM order_items WHERE order_id = ?";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, orderId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            OrderDetail item = new OrderDetail();
            item.setProductName(rs.getString("product_name"));
            item.setColor(rs.getString("color"));
            item.setQuantity(rs.getInt("quantity"));
            item.setPrice(rs.getDouble("price"));
            list.add(item);
        }
        return list;
    }

}

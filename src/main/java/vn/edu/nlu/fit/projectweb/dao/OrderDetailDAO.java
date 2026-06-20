package vn.edu.nlu.fit.projectweb.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import vn.edu.nlu.fit.projectweb.model.OrderDetail;
import vn.edu.nlu.fit.projectweb.model.Product;
import vn.edu.nlu.fit.projectweb.utils.DBConnection;

public class OrderDetailDAO {

    public List<OrderDetail> getDetailsByOrder(int orderId) {

        List<OrderDetail> list = new ArrayList<>();

        String sql = """
            SELECT od.*,
                   p.ProductName
            FROM order_details od
            JOIN products p
                ON od.product_id = p.ProductID
            WHERE od.order_id = ?
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Product p = new Product();
                    p.setProductID(rs.getInt("product_id"));
                    p.setProductName(rs.getString("ProductName"));

                    OrderDetail d = new OrderDetail();
                    d.setDetailId(rs.getInt("detail_id"));
                    d.setOrderId(rs.getInt("order_id"));
                    d.setProductId(rs.getInt("product_id"));
                    d.setQuantity(rs.getInt("quantity"));
                    d.setPrice(rs.getDouble("price"));
                    d.setProduct(p);

                    list.add(d);
                }
            }

        } catch (Exception e) {
            System.out.println("LỖI getDetailsByOrder: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }
}
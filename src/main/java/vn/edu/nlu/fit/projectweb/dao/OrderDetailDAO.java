package vn.edu.nlu.fit.projectweb.dao;

import java.util.List;
import java.util.ArrayList;
import vn.edu.nlu.fit.projectweb.model.OrderDetail;
import vn.edu.nlu.fit.projectweb.model.Product;
import vn.edu.nlu.fit.projectweb.utils.JDBIConnector;


public class OrderDetailDAO {

    public List<OrderDetail> getDetailsByOrder(int orderId){

        String sql = """
            SELECT od.*,
                   p.ProductName
            FROM order_details od
            JOIN products p
                ON od.product_id = p.ProductID
            WHERE od.order_id = ?
        """;

        return JDBIConnector.me().withHandle(handle ->
                handle.createQuery(sql)
                        .bind(0,orderId)
                        .map((rs,ctx)->{

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

                            return d;
                        })
                        .list()
        );
    }
}

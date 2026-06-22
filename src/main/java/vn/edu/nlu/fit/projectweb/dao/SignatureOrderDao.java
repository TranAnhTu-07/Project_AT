package vn.edu.nlu.fit.projectweb.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SignatureOrderDao extends BaseDao {

    public static class OrderSignInfo {
        public int orderId;
        public Integer userId;
        public String dataToSign;
        public String customerName;
    }

    public static Optional<OrderSignInfo> buildDataToSign(int orderId) {
        return get().withHandle(handle -> {

            Map<String, Object> order = handle.createQuery(
                            "SELECT order_id, customer_name, phone_number, email, shipping_address, " +
                                    "total_amount, user_id " +
                                    "FROM orders WHERE order_id = :orderId")
                    .bind("orderId", orderId)
                    .mapToMap()
                    .findFirst()
                    .orElse(null);

            if (order == null) return Optional.<OrderSignInfo>empty();

            List<Map<String, Object>> items = handle.createQuery(
                            "SELECT od.quantity AS quantity, p.ProductName AS product_name " +
                                    "FROM order_details od " +
                                    "JOIN products p ON od.product_id = p.ProductID " +
                                    "WHERE od.order_id = :orderId " +
                                    "ORDER BY od.detail_id ASC")
                    .bind("orderId", orderId)
                    .mapToMap()
                    .list();

            BigDecimal totalAmount = (BigDecimal) order.get("total_amount");
            double totalAmountDouble = totalAmount.doubleValue(); // để ra đúng dạng "1.599E7" như lúc ký

            StringBuilder itemsBuilder = new StringBuilder();
            for (Map<String, Object> item : items) {
                itemsBuilder.append("|Item:")
                        .append(item.get("product_name"))
                        .append("x")
                        .append(item.get("quantity"));
            }

            String dataToSign = "OrderID:" + order.get("order_id") +
                    "|Name:" + order.get("customer_name") +
                    "|Phone:" + order.get("phone_number") +
                    "|Email:" + order.get("email") +
                    "|Address:" + order.get("shipping_address") +
                    "|Total:" + totalAmountDouble +
                    itemsBuilder;

            OrderSignInfo info = new OrderSignInfo();
            info.orderId = (Integer) order.get("order_id");
            info.userId = (Integer) order.get("user_id");
            info.dataToSign = dataToSign;
            info.customerName = (String) order.get("customer_name");

            return Optional.of(info);
        });
    }

    public static void saveSignatureResult(int orderId, String signatureBase64, boolean isValid) {
        get().useHandle(handle ->
                handle.createUpdate(
                                "UPDATE orders SET digital_signature = :sig, signature_status = :status WHERE order_id = :orderId")
                        .bind("sig", signatureBase64)
                        .bind("status", isValid ? 1 : 2) // 1 = hợp lệ, 2 = không hợp lệ — bạn có thể đổi quy ước
                        .bind("orderId", orderId)
                        .execute()
        );
    }
}
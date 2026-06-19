package vn.edu.nlu.fit.projectweb.model;

import java.util.Date;
import java.util.List;

public class Orders {
    private int orderId;
    private Date orderDate;
    private String status;
    private double totalAmount;
    private Date expectedDelivery;

    private List<OrderDetail> orderDetails;

    public Orders() {}

    // getter & setter
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getExpectedDelivery(){return expectedDelivery; }
    public void setExpectedDelivery(Date expectedDelivery) {this.expectedDelivery = expectedDelivery; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }

    public List<OrderDetail> getOrderDetails() { return orderDetails; }
    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
}

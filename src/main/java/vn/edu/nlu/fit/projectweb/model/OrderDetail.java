package vn.edu.nlu.fit.projectweb.model;

public class OrderDetail {
    private int id;
    private String productName;
    private double price;
    private int quantity;
    private String color;

    // getter & setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getColor() {return color; }
    public void setColor(String color) {this.color = color; }
}

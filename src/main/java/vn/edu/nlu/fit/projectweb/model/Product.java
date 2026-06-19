package vn.edu.nlu.fit.projectweb.model;

import java.io.Serializable;

public class Product implements Serializable {
    private int productID;
    private String productName;
    private String img;
    private int price;
    private String brand;
    private int newPrice;
    private int categoryID;
    private String status;
    private int stock;
    private int quantity;

    public Product(int productID, String productName, String img, int price, String brand, int newPrice, int categoryID, String status, int quantity) {
        this.productID = productID;
        this.productName = productName;
        this.img = img;
        this.price = price;
        this.brand = brand;
        this.newPrice = newPrice;
        this.categoryID = categoryID;
        this.status = status;
        this.quantity = quantity;
    }

    public Product() {
    }

    public int getProductID() { return productID; }
    public void setProductID(int productID) { this.productID = productID; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getImg() { return img; }
    public void setImg(String img) { this.img = img; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public int getNewPrice() { return newPrice; }
    public void setNewPrice(int newPrice) { this.newPrice = newPrice; }

    public int getCategoryID() { return categoryID; }
    public void setCategoryID(int categoryID) { this.categoryID = categoryID; }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}

package vn.edu.nlu.fit.projectweb.model;

import java.time.LocalDate;

public class Reviews {
    private  int id;
    private int userId;
    private int productId;
    private int stars;
    private String email;
    private String content;
    private LocalDate created_at;
    private String name;
    private int tong;

    public Reviews(String content, int id, int userId, int stars, int productId, LocalDate date, String name, int tong) {
        this.content = content;
        this.id = id;
        this.userId = userId;
        this.email = email;
        this.stars = stars;
        this.productId = productId;
        this.created_at = created_at;
        this.name = name;
        this.tong = tong;
    }

    public int getTong() {
        return tong;
    }

    public void setTong(int tong) {
        this.tong = tong;
    }

    public Reviews() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String setEmail(String email) {
        return email;
    }

    public void getEmail(String email) {
        this.email = email;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Reviews{" +
                "id=" + id +
                ", userId=" + userId +
                ", productId=" + productId +
                ", stars=" + stars +
                ", content='" + content + '\'' +
                ", created_at=" + created_at +
                ", name='" + name + '\'' +
                ", tong=" + tong +
                '}';
    }
}

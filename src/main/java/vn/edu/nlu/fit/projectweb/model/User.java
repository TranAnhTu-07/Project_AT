package vn.edu.nlu.fit.projectweb.model;

import java.io.Serializable;
import java.time.LocalDate;

public class User implements Serializable {
    private int userId;
    private String fullName;
    private String email;
    private String password;
    private String phone;
    private String sex;
    private int roleId;
    private int status; // 0: Chưa active, 1: Active
    private String token;
    private LocalDate date_of_birth;

    public User() {}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone(){return phone; }

    public void setPhone(String phone){this.phone = phone; }

    public String getSex(){return sex;}

    public void setSex(String sex) {this.sex = sex;}

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }
}

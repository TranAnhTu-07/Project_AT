package vn.edu.nlu.fit.projectweb.model;

import java.sql.Timestamp;

public class OrderStatus {
    private String status;
    private Timestamp statusTime;
    private String description;

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getStatusTime() {
        return statusTime;
    }
    public void setStatusTime(Timestamp statusTime) {
        this.statusTime = statusTime;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}


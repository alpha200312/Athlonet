package com.project.athlo_app.DataModels;

import java.util.Date;

public class event {
    private String id;  // Event ID from MongoDB
    private String name;
    private String description;
    private String sport;
    private String organizerId;  // Reference to Organizer
    private String location;
    private boolean isPrivate;
    private boolean notifications;
    private String status;  // "Upcoming" or "Completed"
    private Date startDate;
    private Date endDate;
    private Date createdAt;

    public event(String id, String name, String description, String sport, String organizerId, String location, boolean isPrivate, boolean notifications, String status, Date startDate, Date endDate, Date createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sport = sport;
        this.organizerId = organizerId;
        this.location = location;
        this.isPrivate = isPrivate;
        this.notifications = notifications;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(String organizerId) {
        this.organizerId = organizerId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public boolean isNotifications() {
        return notifications;
    }

    public void setNotifications(boolean notifications) {
        this.notifications = notifications;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}

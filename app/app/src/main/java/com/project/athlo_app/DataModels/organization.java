package com.project.athlo_app.DataModels;

public class organization {
    private String id,Organization_name,address;

    public organization(String id, String organization_name, String address) {
        this.id = id;
        Organization_name = organization_name;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getOrganization_name() {
        return Organization_name;
    }

    public String getAddress() {
        return address;
    }
}

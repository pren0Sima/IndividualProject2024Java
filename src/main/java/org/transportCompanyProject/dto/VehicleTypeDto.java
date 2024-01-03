package org.transportCompanyProject.dto;

public class VehicleTypeDto {
    private long id;
    private String type;

    public VehicleTypeDto(long id, String type) {
        this.id = id;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "VehicleTypeDto{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}

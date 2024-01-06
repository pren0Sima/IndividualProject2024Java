package org.transportCompanyProject.dto;

import org.transportCompanyProject.entity.Company;
import org.transportCompanyProject.entity.VehicleType;

public class VehicleDto {
    private long id;

    private Company company;

    private VehicleType vehicleType;

    public VehicleDto(long id, Company company, VehicleType vehicleType) {
        this.id = id;
        this.company = company;
        this.vehicleType = vehicleType;
    }

    @Override
    public String toString() {
        return "VehicleDto{" +
                "id=" + id +
                ", company=" + company.getName() +
                ", vehicleType=" + vehicleType.getType() +
                '}';
    }
}

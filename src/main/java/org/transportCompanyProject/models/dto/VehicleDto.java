package org.transportCompanyProject.models.dto;

import org.transportCompanyProject.models.entity.Company;
import org.transportCompanyProject.models.entity.VehicleType;

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

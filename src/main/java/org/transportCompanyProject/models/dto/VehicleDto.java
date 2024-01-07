package org.transportCompanyProject.models.dto;

import org.transportCompanyProject.models.entity.Company;
import org.transportCompanyProject.models.entity.VehicleType;
/**
 * Data Transfer Object (DTO) class representing a Vehicle for use in data exchange between layers.
 */
public class VehicleDto {
    private long id;

    private Company company;

    private VehicleType vehicleType;

    public VehicleDto(long id, Company company, VehicleType vehicleType) {
        this.id = id;
        this.company = company;
        this.vehicleType = vehicleType;
    }
    /**
     * An overridden toString() method that displays a VehicleDto's id, company and vehicleType.
     * @return a String object.
     */
    @Override
    public String toString() {
        return "VehicleDto{" +
                "id=" + id +
                ", company=" + company.getName() +
                ", vehicleType=" + vehicleType.getType() +
                '}';
    }
}

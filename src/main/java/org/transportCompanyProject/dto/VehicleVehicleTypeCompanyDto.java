package org.transportCompanyProject.dto;

public class VehicleVehicleTypeCompanyDto {
    // from Vehicle
    private long vehicle_id;
    // from Company
    private long company_id;
    private String company_name;
    // from VehicleType
    private long vehicle_type_id;
    private String vehicle_type;

    // tymna india
    public VehicleVehicleTypeCompanyDto(long vehicle_id, long company_id, String company_name, long vehicle_type_id, String vehicle_type) {
        this.vehicle_id = vehicle_id;
        new CompanyDto(company_id, company_name);
        this.company_id = company_id;
        this.company_name = company_name;

        this.vehicle_type_id = vehicle_type_id;
        this.vehicle_type = vehicle_type;
    }
}

package org.transportCompanyProject.dto;

import org.transportCompanyProject.entity.Company;
import org.transportCompanyProject.Enumerations.PositionType;

public class EmployeeDto {
    private long id;
    private String name;
    private PositionType positionType;
    private Company company;

    public EmployeeDto(long id, String name, PositionType positionType, Company company) {
        this.id = id;
        this.name = name;
        this.positionType = positionType;
        this.company = company;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PositionType getPositionType() {
        return positionType;
    }

    public void setPositionType(PositionType positionType) {
        this.positionType = positionType;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "EmployeeDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", positionType=" + positionType +
                ", companyName=" + company.getName() +
                '}';
    }
}

package org.transportCompanyProject.models.dto;
/**
 * Data Transfer Object (DTO) class representing a Client for use in data exchange between layers.
 */
public class ClientDto {
    private long id;
    private String name;
    public ClientDto(long id, String name) {
        this.id = id;
        this.name = name;
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

    /**
     * An overridden toString() method that displays a ClientDto's id, name and balance.
     * @return a String object.
     */
    @Override
    public String toString() {
        return "ClientDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

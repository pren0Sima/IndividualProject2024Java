package org.transportCompanyProject.models.dto;

import org.transportCompanyProject.models.entity.Cargo;
import org.transportCompanyProject.models.entity.Itinerary;

import java.util.Set;

// TODO: mapping table?
public class ItineraryCargoDto {
    private Set<Itinerary> itineraries;
    private Set<Cargo> cargos;
}

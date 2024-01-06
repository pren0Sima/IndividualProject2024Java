package org.transportCompanyProject.models.reports;

import org.transportCompanyProject.models.dao.*;
import org.transportCompanyProject.models.entity.Itinerary;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ReportMaker {
    public static void writeReportTitle(String title, Report report) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(report.getReportName(), false));
        writer.write(title);
        writer.close();
    }
    public static void addItineraryToReport(Itinerary itinerary, Report report) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(report.getReportName(), true));
        writer.write("\nItinerary id: " + itinerary.getId());
        writer.write("\nStarting point: " + itinerary.getStartingPoint());
        writer.write("\nDestination: " + itinerary.getDestination());
        writer.write("\nDate of departure: " + itinerary.getDateOfDeparture());
        writer.write("\nDate of arrival: " + itinerary.getDateOfArrival());
        writer.write("\nCost without overcharge: " + itinerary.getCost());
        writer.write("\nCost with overcharge: " + ItineraryDao.calculatePriceWithOvercharge(itinerary.getCost(),
                ItineraryDao.getCompanyOverchargeThroughItinerary(itinerary)));
        writer.write("\nClient: " + ClientDao.getClientById(itinerary.getClient().getId()));
        writer.write("\nDriver: " + DriverDao.getDriverById(itinerary.getDriver().getId()));
        writer.write("\nVehicle id: " + VehicleDao.getVehicleById(itinerary.getVehicle().getId()).getId());
        writer.write("\nVehicle type: " + VehicleTypeDao.getVehicleTypeById(itinerary.getVehicle().getVehicleType().getId()));
        // TODO: cargo (make a getCargo method with join):

        writer.write("\n");
        writer.close();
    }
}

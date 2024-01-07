package org.transportCompanyProject.models.reports;

import org.transportCompanyProject.models.dao.*;
import org.transportCompanyProject.models.entity.Cargo;
import org.transportCompanyProject.models.entity.Itinerary;

import java.io.*;
import java.util.List;

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
        writer.write("\nClient: id: " + ClientDao.getClientById(itinerary.getClient().getId()).getId()
        + "\tname: " + ClientDao.getClientById(itinerary.getClient().getId()).getName());
        writer.write("\nDriver: " + DriverDao.getDriverById(itinerary.getDriver().getId()));
        writer.write("\nVehicle id: " + VehicleDao.getVehicleById(itinerary.getVehicle().getId()).getId());
        writer.write("\tVehicle type: " + VehicleTypeDao.getVehicleTypeById(itinerary.getVehicle().getVehicleType().getId()).getType());
        // getCargo()
        for (Cargo cargo : itinerary.getCargo()) {
            writer.write("\nCargo id: " + cargo.getId());
            writer.write("\t\tCargo type: " + cargo.getClass());
        }

        writer.write("\n");
        writer.close();
    }
    public static void addItineraryListToReport(List<Itinerary> itineraries, Report report) throws IOException {
        for(Itinerary itinerary : itineraries){
            addItineraryToReport(itinerary, report);
        }
    }
    public static void deleteReport(String reportFileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(reportFileName));
        writer.write("");
        writer.close();
    }
    public static void createSingleItineraryReport(String reportTitle, Report report, Itinerary itinerary) throws IOException {
        // if we have anything in the report file, delete it.
        deleteReport(report.getReportName());
        // adding the report title
        writeReportTitle(reportTitle, report);
        // adding the itinerary
        addItineraryToReport(itinerary, report);
    }
    public static void createItineraryListReport(String reportTitle, Report report, List<Itinerary> itineraries) throws IOException {
        // if we have anything in the report file, delete it.
        deleteReport(report.getReportName());
        // adding the report title
        writeReportTitle(reportTitle, report);
        // adding the itineraries
        addItineraryListToReport(itineraries, report);
    }
    public static void printReport(String reportFileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(reportFileName));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
    }
}

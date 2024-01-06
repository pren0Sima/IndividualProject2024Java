package org.transportCompanyProject.models.reports;

public class Report {

    private String reportFileName;
    public Report(String reportFileName) {
        this.reportFileName = reportFileName;
    }
    public String getReportName() {
        return reportFileName;
    }

    public void setReportName(String reportFileName) {
        this.reportFileName = reportFileName;
    }
}

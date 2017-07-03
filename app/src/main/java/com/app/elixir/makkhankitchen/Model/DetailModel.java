package com.app.elixir.makkhankitchen.Model;

/**
 * Created by NetSupport on 09-03-2017.
 */

public class DetailModel {

    String serNo;
    String compName;
    String date;
    String reportType;
    String def;
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSerNo() {
        return serNo;
    }

    public void setSerNo(String serNo) {
        this.serNo = serNo;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }
}

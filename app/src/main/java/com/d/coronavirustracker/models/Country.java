package com.d.coronavirustracker.models;

public class Country {
    private String updated;
    private String country;
    private String flag;
    private String cases;
    private String deaths;
    private String recovered;
    private String active;

    public Country() {
    }

    public Country(String updated, String country, String flag, String cases, String deaths, String recovered, String active) {
        this.updated = updated;
        this.country = country;
        this.flag = flag;
        this.cases = cases;
        this.deaths = deaths;
        this.recovered = recovered;
        this.active = active;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdate(String updated) {
        this.updated = updated;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCases() {
        return cases;
    }

    public void setCases(String cases) {
        this.cases = cases;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }
}

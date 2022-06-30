package com.daryll.webscrape.webscraping;

public class Company {
    private final String companyName;
    private final String website;

    public Company(String companyName, String website) {
        this.companyName = companyName;
        this.website = website;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getWebsite() {
        return website;
    }

    @Override
    public String toString() {
        return "Company{" +
                "companyName='" + companyName + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}
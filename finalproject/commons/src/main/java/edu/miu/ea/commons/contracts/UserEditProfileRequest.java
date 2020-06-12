package edu.miu.ea.commons.contracts;

public class UserEditProfileRequest {
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    private String company;
    private String website;
}

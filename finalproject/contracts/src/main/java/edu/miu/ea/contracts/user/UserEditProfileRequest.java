package edu.miu.ea.contracts.user;

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

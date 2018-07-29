package yelp.model;

public class PostalCode {
    private String code;
    private String neighborhood;
    private String city;
    private String state;

    public PostalCode(String code, String neighborhood, String city, String state) {
        this.code = code;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

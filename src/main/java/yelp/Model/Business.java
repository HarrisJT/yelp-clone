package yelp.Model;

public class Business {
    private String id;
    private String name;
    private String address;
    private boolean parking;
    private boolean creditCards;
    private boolean wheelchair;
    private String openTime;
    private String closeTime;
    private String postalCode;

    public Business(String id, String name, String address, boolean parking, boolean creditCards, boolean wheelchair, String openTime, String closeTime, String postalCode) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.parking = parking;
        this.creditCards = creditCards;
        this.wheelchair = wheelchair;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.postalCode = postalCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isParking() {
        return parking;
    }

    public void setParking(boolean parking) {
        this.parking = parking;
    }

    public boolean isCreditCards() {
        return creditCards;
    }

    public void setCreditCards(boolean creditCards) {
        this.creditCards = creditCards;
    }

    public boolean isWheelchair() {
        return wheelchair;
    }

    public void setWheelchair(boolean wheelchair) {
        this.wheelchair = wheelchair;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
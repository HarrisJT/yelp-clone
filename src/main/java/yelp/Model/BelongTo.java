package yelp.Model;

public class BelongTo {
    private String businessId;
    private String category;

    public BelongTo(String businessId, String category) {
        this.businessId = businessId;
        this.category = category;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

package yelp.Model;

public class Tip {
    private String userId;
    private String businessId;
    private String text;

    public Tip(String userId, String businessId, String text) {
        this.userId = userId;
        this.businessId = businessId;
        this.text = text;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

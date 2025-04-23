package au.com.telstra.simcardactivator;

public class SimCardActivationResponse {
    private String iccid;
    private String customerEmail;
    private boolean active;

    public SimCardActivationResponse(String iccid, String customerEmail, boolean active) {
        this.iccid = iccid;
        this.customerEmail = customerEmail;
        this.active = active;
    }

    public String getIccid() {
        return iccid;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public boolean isActive() {
        return active;
    }
}
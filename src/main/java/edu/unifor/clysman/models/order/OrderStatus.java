package edu.unifor.clysman.models.order;

public enum OrderStatus {
    PENDING("pending"), IN_PROGRESS("in_progress"), SENT("sent"), CANCELED("canceled");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static OrderStatus fromString(String status) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.getStatus().equals(status)) {
                return orderStatus;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return status;
    }
}

package edu.unifor.clysman.models.user;

public enum UserType {
    ADMIN("admin"), CLIENT("admin");

    private final String type;

    UserType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static UserType fromString(String type) {
        for (UserType userType : UserType.values()) {
            if (userType.getType().equals(type)) {
                return userType;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return type;
    }
}

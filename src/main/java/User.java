package src.main.java;

public class User {
    private String name;
    private String address;
    private String phoneNumber;
    private String licenseNumber;

    public User(String name, String address, String phoneNumber, String licenseNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.licenseNumber = licenseNumber;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }
}

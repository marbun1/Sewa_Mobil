package src.main.java;

public class Car {
    private String make;
    private String model;
    private String plateNumber;
    private double dailyRate;
    private boolean available;

    public Car(String make, String model, String plateNumber, double dailyRate) {
        this.make = make;
        this.model = model;
        this.plateNumber = plateNumber;
        this.dailyRate = dailyRate;
        this.available = true;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}

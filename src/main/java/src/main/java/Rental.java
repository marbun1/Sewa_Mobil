package src.main.java;

import java.time.LocalDate;

public class Rental {
    private User user;
    private Car car;
    private LocalDate startDate;
    private LocalDate endDate;

    public Rental(User user, Car car, LocalDate startDate, LocalDate endDate) {
        this.user = user;
        this.car = car;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public User getUser() {
        return user;
    }

    public Car getCar() {
        return car;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public long getTotalDays() {
        return startDate.until(endDate).getDays();
    }

    public double getTotalCost() {
        return getTotalDays() * car.getDailyRate();
    }
}

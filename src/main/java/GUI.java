import javafx.scene.control.*;
import javafx.scene.layout.*;
import src.main.java.Car;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GUI {
    private List<User> users = new ArrayList<>();
    private List<Car> cars = new ArrayList<>();
    private List<Rental> rentals = new ArrayList<>();

    private VBox mainPane;

    public GUI() {
        mainPane = new VBox();
        initUI();
    }

    public Pane getMainPane() {
        return mainPane;
    }

    private void initUI() {
        Label titleLabel = new Label("Car Rental System");

        Button registerUserBtn = new Button("Register User");
        Button addCarBtn = new Button("Add Car");
        Button searchCarBtn = new Button("Search Car");
        Button rentCarBtn = new Button("Rent Car");
        Button returnCarBtn = new Button("Return Car");
        Button viewAvailableCarsBtn = new Button("View Available Cars");
        Button viewUserRentalsBtn = new Button("View User Rentals");

        registerUserBtn.setOnAction(event -> showRegisterUserDialog());
        addCarBtn.setOnAction(event -> showAddCarDialog());
        searchCarBtn.setOnAction(event -> showSearchCarDialog());
        rentCarBtn.setOnAction(event -> showRentCarDialog());
        returnCarBtn.setOnAction(event -> showReturnCarDialog());
        viewAvailableCarsBtn.setOnAction(event -> showAvailableCars());
        viewUserRentalsBtn.setOnAction(event -> showUserRentals());

        mainPane.getChildren().addAll(
            titleLabel, registerUserBtn, addCarBtn, searchCarBtn,
            rentCarBtn, returnCarBtn, viewAvailableCarsBtn, viewUserRentalsBtn
        );
    }

    private void showRegisterUserDialog() {
        Dialog<User> dialog = new Dialog<>();
        dialog.setTitle("Register User");

        GridPane grid = new GridPane();
        TextField nameField = new TextField();
        TextField addressField = new TextField();
        TextField phoneNumberField = new TextField();
        TextField licenseNumberField = new TextField();

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Address:"), 0, 1);
        grid.add(addressField, 1, 1);
        grid.add(new Label("Phone Number:"), 0, 2);
        grid.add(phoneNumberField, 1, 2);
        grid.add(new Label("License Number:"), 0, 3);
        grid.add(licenseNumberField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        ButtonType registerButtonType = new ButtonType("Register", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(registerButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == registerButtonType) {
                User user = new User(
                    nameField.getText(),
                    addressField.getText(),
                    phoneNumberField.getText(),
                    licenseNumberField.getText()
                );
                users.add(user);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "User registered successfully!");
                alert.showAndWait();
                return user;
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void showAddCarDialog() {
        Dialog<Car> dialog = new Dialog<>();
        dialog.setTitle("Add Car");

        GridPane grid = new GridPane();
        TextField makeField = new TextField();
        TextField modelField = new TextField();
        TextField plateNumberField = new TextField();
        TextField dailyRateField = new TextField();

        grid.add(new Label("Make:"), 0, 0);
        grid.add(makeField, 1, 0);
        grid.add(new Label("Model:"), 0, 1);
        grid.add(modelField, 1, 1);
        grid.add(new Label("Plate Number:"), 0, 2);
        grid.add(plateNumberField, 1, 2);
        grid.add(new Label("Daily Rate:"), 0, 3);
        grid.add(dailyRateField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                Car car = new Car(
                    makeField.getText(),
                    modelField.getText(),
                    plateNumberField.getText(),
                    Double.parseDouble(dailyRateField.getText())
                );
                cars.add(car);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Car added successfully!");
                alert.showAndWait();
                return car;
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void showSearchCarDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Search Car");
        dialog.setHeaderText("Enter make or model to search");

        dialog.showAndWait().ifPresent(searchTerm -> {
            List<Car> results = new ArrayList<>();
            for (Car car : cars) {
                if (car.getMake().equalsIgnoreCase(searchTerm) || car.getModel().equalsIgnoreCase(searchTerm)) {
                    results.add(car);
                }
            }
            showCarResults(results);
        });
    }

    /**
     * 
     */
    private void showRentCarDialog() {
        Dialog<Rental> dialog = new Dialog<>();
        dialog.setTitle("Rent Car");

        GridPane grid = new GridPane();
        TextField userField = new TextField();
        TextField carField = new TextField();
        DatePicker startDatePicker = new DatePicker();
        DatePicker endDatePicker = new DatePicker();

        grid.add(new Label("User:"), 0, 0);
        grid.add(userField, 1, 0);
        grid.add(new Label("Car:"), 0, 1);
        grid.add(carField, 1, 1);
        grid.add(new Label("Start Date:"), 0, 2);
        grid.add(startDatePicker, 1, 2);
        grid.add(new Label("End Date:"), 0, 3);
        grid.add(endDatePicker, 1, 3);

        dialog.getDialogPane().setContent(grid);

        ButtonType rentButtonType = new ButtonType("Rent", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(rentButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> extracted(userField, carField, startDatePicker, endDatePicker, rentButtonType, dialogButton));

        dialog.showAndWait();
    }

    private Rental extracted(TextField userField, TextField carField, DatePicker startDatePicker,
            DatePicker endDatePicker, ButtonType rentButtonType, ButtonType dialogButton) {
        if (dialogButton == rentButtonType) {
            User user = null;
            for (User u : users) {
                if (u.getName().equalsIgnoreCase(userField.getText())) {
                    user = u;
                    break;
                }
            }
            Car car = null;
            for (Car c : cars) {
                if (c.getPlateNumber().equalsIgnoreCase(carField.getText()) && c.isAvailable()) {
                    car = c;
                    break;
                }
            }
            if (user != null && car != null) {
                final Rental rental = new Rental(
                    user,
                    car,
                    startDatePicker.getValue(),
                    endDatePicker.getValue()
                );
                rentals.add(rental);
                car.setAvailable(false);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Car rented successfully!");
                alert.showAndWait();
                return rental;
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid user or car details!");
                alert.showAndWait();
            }
        }
        return null;
    }

    private void showReturnCarDialog() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Return Car");
        dialog.setHeaderText("Enter plate number of car to return");

        dialog.showAndWait().ifPresent(plateNumber -> {
            Rental rental = null;
            for (Rental r : rentals) {
                if (r.getCar().getPlateNumber().equalsIgnoreCase(plateNumber) && !r.getCar().isAvailable()) {
                    rental = r;
                    break;
                }
            }
            if (rental != null) {
                rental.getCar().setAvailable(true);
                rentals.remove(rental);
                double totalCost = rental.getTotalCost();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Car returned successfully! Total cost: $" + totalCost);
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Car not found or not rented!");
                alert.showAndWait();
            }
        });
    }

    private void showAvailableCars() {
        List<Car> availableCars = new ArrayList<>();
        for (Car car : cars) {
            if (car.isAvailable()) {
                availableCars.add(car);
            }
        }
        showCarResults(availableCars);
    }

    private void showUserRentals() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("User Rentals");
        dialog.setHeaderText("Enter user name to view rentals");

        dialog.showAndWait().ifPresent(userName -> {
            List<Rental> userRentals = new ArrayList<>();
            for (Rental rental : rentals) {
                if (rental.getUser().getName().equalsIgnoreCase(userName)) {
                    userRentals.add(rental);
                }
            }
            showRentalResults(userRentals);
        });
    }

    private void showCarResults(List<Car> cars) {
        StringBuilder results = new StringBuilder();
        for (Car car : cars) {
            results.append("Make: ").append(car.getMake())
                .append(", Model: ").append(car.getModel())
                .append(", Plate Number: ").append(car.getPlateNumber())
                .append(", Daily Rate: $").append(car.getDailyRate())
                .append("\n");
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION, results.toString());
        alert.setTitle("Car Results");
        alert.setHeaderText("Search Results");
        alert.showAndWait();
    }

    private void showRentalResults(List<Rental> rentals) {
        StringBuilder results = new StringBuilder();
        for (Rental rental : rentals) {
            results.append("User: ").append(rental.getUser().getName())
                .append(", Car: ").append(rental.getCar().getMake()).append(" ").append(rental.getCar().getModel())
                .append(", Start Date: ").append(rental.getStartDate())
                .append(", End Date: ").append(rental.getEndDate())
                .append(", Total Cost: $").append(rental.getTotalCost())
                .append("\n");
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION, results.toString());
        alert.setTitle("Rental Results");
        alert.setHeaderText("User Rentals");
        alert.showAndWait();
    }
}

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CarRentalApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        GUI gui = new GUI();
        primaryStage.setTitle("Car Rental System");
        primaryStage.setScene(new Scene(gui.getMainPane(), 800, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

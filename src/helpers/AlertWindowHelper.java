package helpers;

import javafx.scene.control.Alert;

public class AlertWindowHelper {

    public static void error(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        push(alert, title, message);
    }

    public static void none(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        push(alert, title, message);
    }

    public static void information(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        push(alert, title, message);
    }

    public static void warning(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        push(alert, title, message);
    }

    public static void confirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        push(alert, title, message);
    }

    private static void push(Alert alert, String title, String msg) {
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

}

package presentation;

import businessLayer.controller.ClientViewController;
import businessLayer.controller.OrderViewController;
import businessLayer.controller.ProductViewController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * The ParentController class that has multiple methods used by all the Views
 */
public class ViewsController {

    public Scene scene = null;
    public Stage stage = null;

    public void displayFunction(List<TextField> textFields, Button insert, boolean visibility) {
        for (TextField textField : textFields) {
            textField.setVisible(visibility);
            if (!textField.isVisible())
                textField.setText("");
        }
        insert.setVisible(visibility);
    }

    /**
     * Changes the Scene to the Products Window
     */
    @FXML
    public void changeSceneToProducts(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/productView.fxml"));
            Parent root = loader.load();
            ProductView productView = loader.getController();
            ProductViewController productViewController = new ProductViewController();
            productView.setController(productViewController);
            setSceneAndStage(root, actionEvent);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Changes the Scene to the Orders Window
     */
    @FXML
    public void changeSceneToOrders(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/orderView.fxml"));
            Parent root = loader.load();
            OrderView orderView = loader.getController();
            OrderViewController orderViewController = new OrderViewController();
            orderView.setController(orderViewController);
            setSceneAndStage(root, actionEvent);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Changes the Scene to the Client Window
     */
    @FXML
    public void changeSceneToClients(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/clientView.fxml"));
            Parent root = loader.load();
            ClientView clientView = loader.getController();
            ClientViewController clientViewController = new ClientViewController();
            clientView.setController(clientViewController);
            setSceneAndStage(root, actionEvent);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Shows an Alert on the Screen
     *
     * @param title       the Title of the Alert
     * @param header      the Header of the Alert
     * @param contentText the Content of the Alert
     * @param alertType   the AlertType
     */
    public void showAlert(String title, String header, String contentText, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(contentText);

        alert.showAndWait();
    }

    public void setSceneAndStage(Parent root, ActionEvent actionEvent) {
        if (scene == null) {
            scene = new Scene(root);
            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else
            stage.getScene().setRoot(root);
    }
}

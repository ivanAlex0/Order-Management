package start;

import businessLayer.controller.ClientViewController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import presentation.ClientView;

import java.util.logging.Logger;

public class App extends Application {
    protected static final Logger LOGGER = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/clientView.fxml"));
        Parent root = loader.load();
        ClientView clientView = loader.getController();
        ClientViewController clientViewController = new ClientViewController();
        clientView.setController(clientViewController);
        primaryStage.setTitle("Order Management");
        primaryStage.setScene(new Scene(root, 985, 510));
        primaryStage.show();
    }
}

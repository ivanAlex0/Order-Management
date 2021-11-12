package presentation;

import businessLayer.controller.ClientViewController;
import businessLayer.reflection.ClientReflection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Client;
import start.App;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * This is the UI View class for the Client Window
 * It makes use of the ClientViewController to make changes to the DB and to update the results
 */
public class ClientView extends ViewsController {

    protected static final Logger LOGGER = Logger.getLogger(App.class.getName());
    private ClientViewController clientViewController;

    public void setController(ClientViewController c) {
        this.clientViewController = c;
    }

    @FXML
    TableView<Client> tableView;
    @FXML
    TextField nameInsert;
    @FXML
    TextField emailInsert;
    @FXML
    Button insert;
    @FXML
    TextField nameEdit;
    @FXML
    TextField emailEdit;
    @FXML
    Button edit;

    @FXML
    public void initialize() {
        List<TextField> textFields = Arrays.asList(nameInsert, nameEdit, emailEdit, emailInsert);
        for (TextField textField : textFields) {
            textField.setVisible(false);
        }

        insert.setVisible(false);
        edit.setVisible(false);
    }

    /**
     * Edits a client
     */
    @FXML
    public void editClient() {
        Client client = tableView.getSelectionModel().getSelectedItem();
        if (client != null) {
            if (!nameEdit.getText().isEmpty()) client.setName(nameEdit.getText());
            if (!emailEdit.getText().isEmpty()) client.setEmail(emailEdit.getText());
            //call edit from controller
            Client editedClient = clientViewController.editClient(client);
        } else {
            showAlert("You need to select a client!", "Select a client from the table",
                    "In order to edit a client, you must select it from the table", Alert.AlertType.WARNING);
        }
    }

    /**
     * Deletes a client
     */
    @FXML
    public void deleteClient() {
        Client client = tableView.getSelectionModel().getSelectedItem();
        if (client != null) {
            Client deletedClient = clientViewController.deleteClient(client);
        } else {
            showAlert("You need to select a client!", "Select a client from the table",
                    "In order to delete a client, you must select it from the table", Alert.AlertType.WARNING);
        }
    }

    /**
     * Fetches all the Clients and updates the JTable(TableView)
     */
    @FXML
    public void fetchAllClients() {
        List<Client> clients = clientViewController.updateClientsTable();
        ClientReflection clientReflection = new ClientReflection(Client.class);
        clientReflection.generateTable(tableView, clients);
    }

    @FXML
    public void showInsert() {
        displayFunction(Arrays.asList(nameInsert, emailInsert), insert, !insert.isVisible());
        displayFunction(Arrays.asList(nameEdit, emailEdit), edit, false);
    }

    @FXML
    public void showEdit() {
        displayFunction(Arrays.asList(nameEdit, emailEdit), edit, !edit.isVisible());
        displayFunction(Arrays.asList(nameInsert, emailInsert), insert, false);
    }

    /**
     * Inserts a client
     */
    @FXML
    public void insertClient() {
        if (!nameInsert.getText().isEmpty() && !emailInsert.getText().isEmpty()) {
            Client client = new Client(0, nameInsert.getText(), emailInsert.getText());
            if (clientViewController.insertClient(client) == -1) {
                showAlert("Input could not be validated!", "Email is incorrect",
                        "Email address is incorrect!", Alert.AlertType.WARNING);
            }
        } else {
            showAlert("You need to input all fields!", "Input the name and email for the new client",
                    "In order to insert a client, you must insert all necessary details", Alert.AlertType.WARNING);
            LOGGER.log(Level.WARNING, "No text in the textFields");
        }
    }

    @FXML
    public void setEditDetails() {
        Client client = tableView.getSelectionModel().getSelectedItem();
        if (client != null) {
            nameEdit.setText(client.getName());
            emailEdit.setText(client.getEmail());
        }
    }

    /**
     * Changes the scene to the ProductView
     */
    @FXML
    public void goToProducts(ActionEvent actionEvent) {
        changeSceneToProducts(actionEvent);
    }

    /**
     * Changes the scene to the OrderView
     */
    @FXML
    public void goToOrders(ActionEvent actionEvent) {
        changeSceneToOrders(actionEvent);
    }


}

package presentation;

import businessLayer.controller.ProductViewController;
import businessLayer.reflection.ProductReflection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Product;
import start.App;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the UI View class for the Client Window
 * It makes use of the ClientViewController to make changes to the DB and to update the results
 */
public class ProductView extends ViewsController {

    @FXML
    TableView<Product> tableView;
    @FXML
    TextField nameEdit;
    @FXML
    TextField quantityEdit;
    @FXML
    TextField nameInsert;
    @FXML
    TextField quantityInsert;
    @FXML
    Button insert;
    @FXML
    Button edit;

    protected static final Logger LOGGER = Logger.getLogger(App.class.getName());
    private ProductViewController productViewController;

    public void setController(ProductViewController c) {
        this.productViewController = c;
    }

    @FXML
    public void initialize() {
        List<TextField> textFields = Arrays.asList(nameInsert, nameEdit, quantityEdit, quantityInsert);
        for (TextField textField : textFields) {
            textField.setVisible(false);
        }

        insert.setVisible(false);
        edit.setVisible(false);
    }

    /**
     * Edits a Product
     */
    @FXML
    public void editProduct() {
        Product product = tableView.getSelectionModel().getSelectedItem();
        if (product != null) {
            if (!nameEdit.getText().isEmpty()) product.setName(nameEdit.getText());
            if (!quantityEdit.getText().isEmpty()) {
                try {
                    int quantity = Integer.parseInt(quantityEdit.getText());
                    product.setQuantity(Integer.parseInt(quantityEdit.getText()));
                } catch (NumberFormatException numberFormatException) {
                    showAlert("Quantity is not an integer!", "Quantity must be an integer!", "Don't mess with integers, boys!", Alert.AlertType.ERROR);
                    return;
                }
            }
            //call edit from controller
            Product editedProduct = productViewController.editProduct(product);
        } else {
            showAlert("You need to select a product!", "Select a product from the table",
                    "In order to edit a product, you need to select it from the table", Alert.AlertType.WARNING);
        }
    }

    /**
     * Deletes a Product
     */
    @FXML
    public void deleteProduct() {
        Product product = tableView.getSelectionModel().getSelectedItem();
        if (product != null) {
            Product deletedProduct = productViewController.deleteProduct(product);
        } else {
            showAlert("You need to select a product!!", "Select a product from the table",
                    "In order to delete a product, you need to select it from the table", Alert.AlertType.WARNING);
        }
    }

    /**
     * Fecthes alll Products and updates the JTable
     */
    @FXML
    public void fetchAllProduct() {
        List<Product> products = productViewController.updateProductsTable();
        ProductReflection productReflection = new ProductReflection(Product.class);
        productReflection.generateTable(tableView, products);
    }

    @FXML
    public void showInsert() {
        displayFunction(Arrays.asList(nameInsert, quantityInsert), insert, !insert.isVisible());
        displayFunction(Arrays.asList(nameEdit, quantityEdit), edit, false);
    }

    @FXML
    public void showEdit() {
        displayFunction(Arrays.asList(nameEdit, quantityEdit), edit, !edit.isVisible());
        displayFunction(Arrays.asList(nameInsert, quantityInsert), insert, false);
    }

    @FXML
    public void setEditDetails() {
        Product product = tableView.getSelectionModel().getSelectedItem();
        if (product != null) {
            nameEdit.setText(product.getName());
            quantityEdit.setText(product.getQuantity().toString());
        }
    }

    /**
     * Inserts a Product
     */
    @FXML
    public void insertProduct() {
        if (!nameInsert.getText().isEmpty() && !quantityInsert.getText().isEmpty()) {
            try {
                int x = Integer.parseInt(quantityInsert.getText());
            } catch (NumberFormatException numberFormatException) {
                showAlert("Quantity not an integer!", "The quantity must be an integer",
                        "Don't mess with numbers, boy..", Alert.AlertType.WARNING);
                return;
            }
            Product product = new Product(0, nameInsert.getText(), Integer.parseInt(quantityInsert.getText()));
            productViewController.insertProduct(product);
        } else {
            showAlert("You need to input all fields!", "Input the name and quantity for the new product",
                    "In order to insert a product, you must insert all necessary details", Alert.AlertType.WARNING);
            LOGGER.log(Level.WARNING, "No text in the textFields");
        }
    }

    /**
     * Changes the scene to the Clients Window
     */
    @FXML
    public void goToClients(ActionEvent actionEvent) {
        changeSceneToClients(actionEvent);
    }

    /**
     * Changes the scene to the Order Window
     */
    @FXML
    public void goToOrders(ActionEvent actionEvent) {
        changeSceneToOrders(actionEvent);
    }

}

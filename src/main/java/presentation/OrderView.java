package presentation;

import businessLayer.controller.OrderViewController;
import businessLayer.reflection.ClientReflection;
import businessLayer.reflection.OrderReflection;
import businessLayer.reflection.ProductReflection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.Client;
import model.OrderT;
import model.Product;

import java.util.Arrays;
import java.util.List;

/**
 * This is the UI View class for the Order Window
 * It makes use of the OrderViewController to make changes to the DB and to update the results
 */
public class OrderView extends ViewsController {

    private Client client;
    private Product product;
    private OrderViewController orderViewController;
    private ClientReflection clientReflection;
    private ProductReflection productReflection;
    private OrderReflection orderReflection;

    @FXML
    TableView<Object> tableView;
    @FXML
    TextField quantity;
    @FXML
    TextField clientIdEdit;
    @FXML
    TextField productIdEdit;
    @FXML
    TextField quantityEdit;
    @FXML
    TextField clientId;
    @FXML
    TextField productId;
    @FXML
    Button edit;

    public void setController(OrderViewController o) {
        this.orderViewController = o;
        clientReflection = new ClientReflection(Client.class);
        productReflection = new ProductReflection(Product.class);
        orderReflection = new OrderReflection(OrderT.class);
    }

    /**
     * Fetches all the Clients and updates the JTable
     */
    @FXML
    public void fetchAllClients() {
        List<Client> clients = orderViewController.updateTableWithClients();
        clientReflection.generateTable(tableView, clients);
    }

    /**
     * Fetches all the Products and updates the JTable
     */
    @FXML
    public void fetchAllProducts() {
        List<Product> products = orderViewController.updateTableWithProducts();
        productReflection.generateTable(tableView, products);
    }

    /**
     * Fetches all the Orders and updates the JTable
     */
    @FXML
    public void fetchAllOrders() {
        List<OrderT> orders = orderViewController.updateTableWithOrders();
        orderReflection.generateTable(tableView, orders);
    }

    @FXML
    public void showEdit() {
        displayFunction(Arrays.asList(clientIdEdit, productIdEdit, quantityEdit), edit, !edit.isVisible());
    }

    /**
     * Edits an order
     */
    public void editOrder(ActionEvent actionEvent) {
        OrderT order = (OrderT) tableView.getSelectionModel().getSelectedItem();
        if (order != null) {
            Integer newQuantity;
            try {
                newQuantity = Integer.parseInt(quantityEdit.getText());
            } catch (NumberFormatException e) {
                showAlert("Quantity not an integer!", "The quantity field must be an integer.", "Don't mess with numbers, boy..", Alert.AlertType.WARNING);
                return;
            }
            if (!clientIdEdit.getText().isEmpty()) order.setClientId(Integer.parseInt(clientIdEdit.getText()));
            if (!productIdEdit.getText().isEmpty()) order.setProductId(Integer.parseInt(productIdEdit.getText()));
            if (!quantityEdit.getText().isEmpty()) {
                Product product = orderViewController.getProductById(order.getProductId());
                if (newQuantity - product.getQuantity() > product.getQuantity()) {
                    showAlert("UNDER STOCK!", "You have requested a larger quantity than available",
                            "The quantity you have requested is: " + newQuantity + " , stock quantity: + " + product.getQuantity(), Alert.AlertType.WARNING);
                    return;
                } else {
                    product.setQuantity(product.getQuantity() + (order.getQuantity() - newQuantity));
                    System.out.println(product.getQuantity());
                    order.setQuantity(newQuantity);
                    Product editedProduct = orderViewController.editProduct(product);
                }
            }

            OrderT editedOrder = orderViewController.editOrder(order);
        } else {
            showAlert("You need to select an order!", "Select and order from the table",
                    "In order to edit an order, you must select it from the table", Alert.AlertType.WARNING);
        }
    }

    /**
     * Deletes an order
     */
    public void deleteOrder(ActionEvent actionEvent) {
        OrderT order = (OrderT) tableView.getSelectionModel().getSelectedItem();
        if (order != null) {
            OrderT deletedOrder = orderViewController.deleteOrder(order);
        } else {
            showAlert("You need to select an order!", "Select an order from the table",
                    "In order to delete an order, you must select it from the table", Alert.AlertType.WARNING);
        }
    }

    /**
     * Creates an order
     */
    public void placeOrder(ActionEvent actionEvent) {
        if (!quantity.getText().isEmpty() && !clientId.getText().isEmpty() && !productId.getText().isEmpty()) {
            try {
                int x = Integer.parseInt(quantity.getText());
            } catch (NumberFormatException e) {
                showAlert("Quantity not an integer!", "The quantity field must be an integer.",
                        "Don't mess with numbers, boy..", Alert.AlertType.ERROR);
            }
            if (Integer.parseInt(quantity.getText()) > product.getQuantity()) {
                showAlert("UNDER STOCK", "You have requested a larger quantity than available",
                        "The quantity you have requested is: " + quantity.getText() + " , stock quantity: + " + product.getQuantity(), Alert.AlertType.WARNING);
            } else {
                //insert the order into the database
                OrderT order = new OrderT(0, client.getId(), product.getId(), Integer.parseInt(quantity.getText()));
                product.setQuantity(product.getQuantity() - order.getQuantity());
                Product editedProduct = orderViewController.editProduct(product);
                OrderT placedOrder = orderViewController.insertOrder(order);

                //here we should create a .txt file and output the order
            }
        } else {
            showAlert("You need to input all fields!", "Please select an existing client, product and input a quantity",
                    "Some input for your order is missing, please provide it.", Alert.AlertType.WARNING);
        }
    }

    /**
     * Generates the txt files
     */
    public void createTxtOrders() {
        orderViewController.createTxtOrders();
    }

    @FXML
    public void setEditDetails() {
        Object object = tableView.getSelectionModel().getSelectedItem();
        if (object instanceof OrderT) {
            clientIdEdit.setText(((OrderT) object).getClientId().toString());
            productIdEdit.setText(((OrderT) object).getProductId().toString());
            quantityEdit.setText(((OrderT) object).getQuantity().toString());
        } else if (object instanceof Client) {
            client = (Client) object;
            clientId.setText(client.getId().toString());
        } else if (object instanceof Product) {
            product = (Product) object;
            productId.setText(product.getId().toString());
        }
    }

    /**
     * Changes the scene to the Products Window
     */
    @FXML
    public void goToProducts(ActionEvent actionEvent) {
        changeSceneToProducts(actionEvent);
    }

    /**
     * Changes the scene to the Clients Window
     */
    @FXML
    public void goToClients(ActionEvent actionEvent) {
        changeSceneToClients(actionEvent);
    }
}

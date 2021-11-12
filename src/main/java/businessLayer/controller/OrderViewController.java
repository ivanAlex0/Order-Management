package businessLayer.controller;

import dataAccessLayer.bll.ClientBLL;
import dataAccessLayer.bll.OrderBLL;
import dataAccessLayer.bll.ProductBLL;
import model.Client;
import model.OrderT;
import model.Product;
import start.App;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Controller of the OrderView class, that controls the Order Window in the UI
 */
public class OrderViewController {

    /**
     * The simple LOGGER that is used to log the warnings
     */
    protected static final Logger LOGGER = Logger.getLogger(App.class.getName());

    /**
     * The ClientBLL, which will resolve the requests by getting data from the DB
     */
    private final ClientBLL clientBLL;
    /**
     * The ProductBLLBLL, which will resolve the requests by getting data from the DB
     */
    private final ProductBLL productBLL;
    /**
     * The OrderBLL, which will resolve the requests by getting data from the DB
     */
    private final OrderBLL orderBLL;

    /**
     * Constructor which initializes the clientBLL, productBLL and orderBLL
     */
    public OrderViewController() {
        clientBLL = new ClientBLL();
        orderBLL = new OrderBLL();
        productBLL = new ProductBLL();
    }

    /**
     * The method inserts an Order into the Database with the help of the BLL
     *
     * @param order the Order to be inserted
     * @return the Order that was inserted
     */
    public OrderT insertOrder(OrderT order) {
        try {
            orderBLL.insertOrder(order);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
        return order;
    }

    /**
     * The method edits an Order from the Database with the help of the BLL
     *
     * @param order the Order to be edited (it exists in the Database)
     * @return the Order that was edited
     */
    public OrderT editOrder(OrderT order) {
        try {
            orderBLL.editOrder(order);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
        return order;
    }

    /**
     * Methods simply gets the Product by id
     *
     * @param id id of the Product to be fetched
     * @return the Product with id = id from the Database
     */
    public Product getProductById(int id) {
        try {
            return productBLL.findProductById(id);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
        return null;
    }

    /**
     * The method edits a Product from the Database with the help of the BLL
     *
     * @param product the product to be edited (it exists in the Database)
     * @return the product that was edited
     */
    public Product editProduct(Product product) {
        try {
            productBLL.editProduct(product);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
        return product;
    }

    /**
     * The method deletes an Order into the Database with the help of the BLL
     *
     * @param order the Order to be deleted (it exists in the Database)
     * @return the Order that was deleted
     */
    public OrderT deleteOrder(OrderT order) {
        try {
            orderBLL.deleteOrder(order);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
        return order;
    }

    /**
     * Method fetches the List of Clients from the Database
     *
     * @return the List of Clients
     */
    public List<Client> updateTableWithClients() {
        List<Client> clients = new ArrayList<>();
        try {
            clients = clientBLL.findAllClients();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
        return clients;
    }

    /**
     * Method fetches the List of Products from the Database
     *
     * @return the List of Products
     */
    public List<Product> updateTableWithProducts() {
        List<Product> products = new ArrayList<>();
        try {
            products = productBLL.findAllProducts();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
        return products;
    }

    /**
     * Method fetches the List of Orders from the Database
     *
     * @return the List of Orders
     */
    public List<OrderT> updateTableWithOrders() {
        List<OrderT> orders = new ArrayList<>();
        try {
            orders = orderBLL.findAllOrders();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
        return orders;
    }

    /**
     * The method creates a .txt file for every Order in the Database and outputs the specific information
     */
    public void createTxtOrders() {
        List<OrderT> orders = orderBLL.findAllOrders();

        for (OrderT order : orders) {
            String orderName = "Order #" + order.getId() + ".txt";
            try {
                PrintWriter printWriter = new PrintWriter(orderName);
                Client client = clientBLL.findClientById(order.getClientId());
                Product product = productBLL.findProductById(order.getProductId());
                printWriter.println("Order #" + order.getId());
                printWriter.println("Client: " + client.getName());
                printWriter.println("Product: " + product.getName());
                printWriter.println("Quantity: " + order.getQuantity());
                printWriter.close();
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }
    }

}

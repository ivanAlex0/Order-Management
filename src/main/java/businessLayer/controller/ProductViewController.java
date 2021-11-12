package businessLayer.controller;

import dataAccessLayer.bll.ProductBLL;
import model.Product;
import presentation.ProductView;
import start.App;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Controller of the ProductView class, that controls the Product Window in the UI
 */
public class ProductViewController {

    /**
     * The simple LOGGER that is used to log the warnings
     */
    protected static final Logger LOGGER = Logger.getLogger(App.class.getName());

    /**
     * The ProductBLL, which will resolve the requests by getting data from the DB
     */
    private final ProductBLL productBLL;

    /**
     * Constructor which initializes the productBLL
     */
    public ProductViewController() {
        productBLL = new ProductBLL();
    }

    /**
     * The method fetches the products from the BLL and sends them to the View
     *
     * @return the List of Products from the Database
     */
    public List<Product> updateProductsTable() {
        List<Product> products = new ArrayList<>();
        try {
            products = productBLL.findAllProducts();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
        return products;
    }

    /**
     * The method edits a Product into the Database with the help of the BLL
     *
     * @param product the Product to be edited
     * @return the id of the edited product
     */
    public Product editProduct(Product product) {
        try {
            return productBLL.editProduct(product);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
        return null;
    }

    /**
     * The method inserts a Product into the Database with the help of the BLL
     *
     * @param product the Product to be inserted
     * @return the id of the inserted product
     */
    public int insertProduct(Product product) {
        try {
            return productBLL.insertProduct(product);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
        return -1;
    }

    /**
     * The method deletes a Product into the Database with the help of the BLL
     *
     * @param product the Product to be deleted
     * @return the id of the deleted product
     */
    public Product deleteProduct(Product product) {
        try {
            return productBLL.deleteProduct(product);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
        return null;
    }
}

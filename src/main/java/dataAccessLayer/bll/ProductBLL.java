package dataAccessLayer.bll;

import dataAccessLayer.dao.ProductDAO;
import model.Product;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * The ProductBLL class that sends requests to the DB using the DAO
 */
public class ProductBLL {

    /**
     * ProductDAO through which the BLL sends/gets data to/from the DB
     */
    private ProductDAO productDAO = null;

    /**
     * Constructor initializes the DAO
     */
    public ProductBLL() {
        productDAO = new ProductDAO(Product.class);
    }

    /**
     * Finds a Product by its id
     *
     * @param id the id of the searched Product
     * @return the found Product
     */
    public Product findProductById(int id) {
        Product product = productDAO.findById(id);
        if (product == null) {
            throw new NoSuchElementException("The product with id = " + id + " could not be found.");
        }
        return product;
    }

    /**
     * Fetches all the Products from the DB
     *
     * @return the List with all the Products in the DB
     */
    public List<Product> findAllProducts() {
        return productDAO.findAll();
    }

    /**
     * Inserts a Product in the DB
     *
     * @param product the Product to be inserted
     * @return the id of the newly inserted Product
     */
    public int insertProduct(Product product) {
        return productDAO.insert(product);
    }

    /**
     * Edits an existing Product from the DB
     *
     * @param product the Product to be edited
     * @return the edited Product
     */
    public Product editProduct(Product product) {
        return productDAO.edit(product);
    }

    /**
     * Deletes a Product from the DB
     *
     * @param product the Product to be deleted
     * @return the deleted Product
     */
    public Product deleteProduct(Product product) {
        return productDAO.delete(product);
    }

}

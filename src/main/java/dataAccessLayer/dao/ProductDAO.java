package dataAccessLayer.dao;

import model.Product;

import java.util.List;

public class ProductDAO extends AbstractDAO<Product> {

    public ProductDAO(Class<Product> type) {
        super(type);
    }

    public Product findById(int id) {
        return super.findById(id);
    }

    public List<Product> findAll() {
        return super.findAll();
    }

    public int insert(Product product) {
        return super.insert(product);
    }
}

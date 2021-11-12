package businessLayer.reflection;

import javafx.scene.control.TableView;
import model.Product;

import java.util.List;

/**
 * The ProductReflection class that extends the AbstractReflection class and implements the generateTable method with respect to the Product Class
 */
public class ProductReflection extends AbstractReflection<Product> {

    /**
     * simple Constructor that calls super
     *
     * @param type Class Type
     */
    public ProductReflection(Class<Product> type) {
        super(type);
    }

    /**
     * the inherited generateTable method
     *
     * @param tableView the JTable or TableView that can be viewed on the UI
     * @param tList     the List of T Objects
     */
    public void generateTable(TableView tableView, List<Product> tList) {
        super.generateTable(tableView, tList);
    }
}

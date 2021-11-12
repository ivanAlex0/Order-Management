package businessLayer.reflection;

import javafx.scene.control.TableView;
import model.OrderT;

import java.util.List;

/**
 * The OrderReflection class that extends the AbstractReflection class and implements the generateTable method with respect to the Order Class
 */
public class OrderReflection extends AbstractReflection<OrderT> {

    /**
     * simple Constructor that calls super
     *
     * @param type Class Type
     */
    public OrderReflection(Class<OrderT> type) {
        super(type);
    }

    /**
     * the inherited generateTable method
     *
     * @param tableView the JTable or TableView that can be viewed on the UI
     * @param tList     the List of T Objects
     */
    public void generateTable(TableView tableView, List<OrderT> tList) {
        super.generateTable(tableView, tList);
    }
}

package businessLayer.reflection;

import javafx.scene.control.TableView;
import model.Client;

import java.util.List;

/**
 * The ClientReflection that extends the AbstractReflection class and implements the generateTable method with respect to the Client Class
 */
public class ClientReflection extends AbstractReflection<Client> {

    /**
     * simple Constructor that calls super
     *
     * @param type Class Type
     */
    public ClientReflection(Class<Client> type) {
        super(type);
    }

    /**
     * the inherited generateTable method
     *
     * @param tableView the JTable or TableView that can be viewed on the UI
     * @param tList     the List of T Objects
     */
    public void generateTable(TableView tableView, List<Client> tList) {
        super.generateTable(tableView, tList);
    }
}

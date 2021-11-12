package businessLayer.reflection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * The AbstractReflection class that generates the JTables
 *
 * @param <T> class type T
 */
public class AbstractReflection<T> {

    private final Class<T> type;

    /**
     * Simple Constructor that gets the class type
     *
     * @param type the Class T type
     */
    @SuppressWarnings("unchecked")
    public AbstractReflection(Class<T> type) {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * The generic generateTable methods that generates the header of the JTable (TableView) through reflection and populates the table with the List of Objects
     *
     * @param tableView the JTable or TableView that can be viewed on the UI
     * @param tList     the List of T Objects
     */
    public void generateTable(TableView tableView, List<T> tList) {
        tableView.getColumns().clear();
        tableView.getItems().clear();
        for (Field field : type.getDeclaredFields()) {
            TableColumn<T, String> tableColumn = new TableColumn<T, String>(field.getName());
            tableColumn.setCellValueFactory(new PropertyValueFactory<T, String>(field.getName()));
            tableView.getColumns().add(tableColumn);
        }

        ObservableList<T> observableList = FXCollections.observableArrayList(tList);
        tableView.setItems(observableList);
    }
}

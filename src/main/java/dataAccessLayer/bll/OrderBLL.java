package dataAccessLayer.bll;

import dataAccessLayer.dao.OrderDAO;
import model.OrderT;

import java.util.List;

/**
 * The OrderBALL class that sends requests to the DB using the DAO
 */
public class OrderBLL {

    /**
     * OrderDAO through which the BLL sends/gets data to/from the DB
     */
    private final OrderDAO orderDAO;

    /**
     * Constructor initializes the DAO
     */
    public OrderBLL() {
        orderDAO = new OrderDAO(OrderT.class);
    }

    /**
     * Finds an Order by its ID
     *
     * @param id the id of the Order
     * @return the found Order
     */
    public OrderT findById(int id) {
        return orderDAO.findById(id);
    }

    /**
     * Fetches all Orders from the DB
     *
     * @return the List with all the Orders
     */
    public List<OrderT> findAllOrders() {
        return orderDAO.findAll();
    }

    /**
     * Inserts an Order into the DB
     *
     * @param order the Order to be inserted
     * @return the id of the newly inserted Order
     */
    public int insertOrder(OrderT order) {
        return orderDAO.insertOrder(order);
    }

    /**
     * Edits an existing order from the DB
     *
     * @param order the Order to be edited/updated
     * @return the edited Order
     */
    public OrderT editOrder(OrderT order) {
        return orderDAO.editOrder(order);
    }

    /**
     * Deleted an order from the DB.
     *
     * @param order the Order to be deleted
     * @return the deleted Order
     */
    public OrderT deleteOrder(OrderT order) {
        return orderDAO.deleteOrder(order);
    }
}

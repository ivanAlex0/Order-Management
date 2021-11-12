package dataAccessLayer.dao;

import model.OrderT;

import java.util.List;

public class OrderDAO extends AbstractDAO<OrderT> {

    public OrderDAO(Class<OrderT> type) {
        super(type);
    }

    public OrderT findById(int id) {
        return super.findById(id);
    }

    public List<OrderT> findAll() {
        return super.findAll();
    }

    public int insertOrder(OrderT order) {
        return super.insert(order);
    }

    public OrderT editOrder(OrderT order) {
        return super.edit(order);
    }

    public OrderT deleteOrder(OrderT order) {
        return super.delete(order);
    }
}

package dataAccessLayer.dao;

import model.Client;

import java.util.List;

public class ClientDAO extends AbstractDAO<Client> {

    public ClientDAO(Class<Client> type) {
        super(type);
    }

    public Client findById(int id) {
        return super.findById(id);
    }

    public List<Client> findAll() {
        return super.findAll();
    }

    public int insert(Client client) {
        return super.insert(client);
    }

    public Client edit(Client client) {
        return super.edit(client);
    }

    public Client delete(Client client) {
        return super.delete(client);
    }
}

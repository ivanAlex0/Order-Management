package dataAccessLayer.bll;

import dataAccessLayer.bll.validators.EmailValidator;
import dataAccessLayer.bll.validators.Validator;
import dataAccessLayer.dao.ClientDAO;
import model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * The ClientBLL class that sends requests to the DB using the DAO
 */
public class ClientBLL {

    /**
     * list of Validators
     */
    private final List<Validator<Client>> validators;
    /**
     * the ClientDAO through which the BLL sends/gets data to/from the DB
     */
    private final ClientDAO clientDAO;

    /**
     * Constructor initializes the Validators and the DAO
     */
    public ClientBLL() {
        validators = new ArrayList<Validator<Client>>();
        validators.add(new EmailValidator());

        clientDAO = new ClientDAO(Client.class);
    }

    /**
     * Finds a Client by Id
     *
     * @param id id of the Client
     * @return the Client with the id = id
     */
    public Client findClientById(int id) {
        Client client = clientDAO.findById(id);
        if (client == null) {
            throw new NoSuchElementException("The client with id = " + id + " could not be found.");
        }
        return client;
    }

    /**
     * Fetches all clients from the DB
     *
     * @return List of all Clients from the DB
     */
    public List<Client> findAllClients() {
        return clientDAO.findAll();
    }

    /**
     * Inserts a new Client in the DB
     *
     * @param client the Client to be inserted
     * @return the id of the newly inserted Client
     */
    public int insertClient(Client client) {
        for (Validator<Client> validator : validators) {
            validator.validate(client);
        }
        return clientDAO.insert(client);
    }

    /**
     * Edits an existing Client from the DB
     *
     * @param client the Client to be edited
     * @return the edited Client
     */
    public Client editClient(Client client) {
        for (Validator<Client> validator : validators) {
            validator.validate(client);
        }
        return clientDAO.edit(client);
    }

    /**
     * Deletes a client from the DB
     *
     * @param client the Client to be deleted
     * @return the deleted Client
     */
    public Client deleteClient(Client client) {
        return clientDAO.delete(client);
    }
}

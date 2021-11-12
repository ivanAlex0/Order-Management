package businessLayer.controller;

import dataAccessLayer.bll.ClientBLL;
import model.Client;
import presentation.ClientView;
import start.App;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Controller of the ClientView class, that controls the Client Window in the UI
 */
public class ClientViewController {
    /**
     * The simple LOGGER that is used to log the warnings
     */
    protected static final Logger LOGGER = Logger.getLogger(App.class.getName());

    /**
     * The ClientBLL, which will resolve the requests by getting data from the DB
     */
    private final ClientBLL clientBLL;

    /**
     * Constructor which initializes the clientBLL
     */
    public ClientViewController() {
        clientBLL = new ClientBLL();
    }

    /**
     * The method fetches the clients from the BLL and sends them to the View
     *
     * @return the List of Clients from the Database
     */
    public List<Client> updateClientsTable() {
        List<Client> clients = new ArrayList<>();
        try {
            clients = clientBLL.findAllClients();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
        return clients;
    }

    /**
     * The method inserts a Client into the Database with the help of the BLL
     *
     * @param client the Client to be inserted
     * @return the id of the inserted client
     */
    public int insertClient(Client client) {
        try {
            return clientBLL.insertClient(client);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
        return -1;
    }

    /**
     * The method edits a Client from the Database with the help of the BLL
     *
     * @param client the Client to be edited (it exists in the Database)
     * @return the Client that was edited
     */
    public Client editClient(Client client) {
        try {
            return clientBLL.editClient(client);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
        return null;
    }

    /**
     * The method deletes a Client from the Database with the help of the BLL
     *
     * @param client the Client to be deleted (it exists in the Database)
     * @return the Client that was deleted
     */
    public Client deleteClient(Client client) {
        try {
            return clientBLL.deleteClient(client);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
        return null;
    }

    //here, everywhere we manipulate requests from View and manage models.
    //also we'll have the Data Access here.
}

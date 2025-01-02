import org.example.DBConnection;
import org.example.dao.ClientDao;
import org.example.mapper.*;
import org.example.model.Admin;
import org.example.model.Client;
import org.example.model.Standard;
import org.junit.jupiter.api.*;

public class ClientTests {
    DBConnection db = DBConnection.getInstance();
    @BeforeEach
    public void init() {
        db.initSession();
        db.createClientTable();
    }

    @AfterEach
    public void clean() {
        db.dropClientTable();
    }

    @Test
    public void addClientTest(){
        ClientMapper clientMapper = new ClientMapperBuilder(db.getSession()).build();
        ClientDao clientDao = clientMapper.clientDao();
        Client client = new Client("Matthew", "Tar", "m.tar@isrp.ias.p.lodz.pl", new Admin());
        clientDao.create(client);
        Client client2 = clientDao.findById(client.getClientID());
        Assertions.assertEquals(client2.toString(), client.toString());
    }

    @Test
    public void getClientTest(){
        ClientMapper clientMapper = new ClientMapperBuilder(db.getSession()).build();
        ClientDao clientDao = clientMapper.clientDao();
        Client client1 = new Client("Matthew", "Tar", "m.tar@isrp.ias.p.lodz.pl", new Admin());
        Client client2 = new Client("Michael", "Corrugated", "don.ias@corrugated.vs.tar", new Admin());
        Client client3 = new Client("Adam", "Notknowing", "i.dont.know@p.lodz.pl", new Standard());
        clientDao.create(client1);
        clientDao.create(client2);
        clientDao.create(client3);
        Client test = clientDao.findById(client1.getClientID());
        Assertions.assertEquals(test.toString(), client1.toString());
        test = clientDao.findById(client2.getClientID());
        Assertions.assertEquals(test.toString(), client2.toString());
        test = clientDao.findById(client3.getClientID());
        Assertions.assertEquals(test.toString(), client3.toString());
    }

    @Test
    public void removeClientTest(){
        ClientMapper clientMapper = new ClientMapperBuilder(db.getSession()).build();
        ClientDao clientDao = clientMapper.clientDao();
        Client client = new Client("Matthew", "Tar", "m.tar@isrp.ias.p.lodz.pl", new Admin());
        clientDao.create(client);
        Client client2 = clientDao.findById(client.getClientID());
        Assertions.assertEquals(client2.toString(), client.toString());
        clientDao.delete(client);
        client2 = clientDao.findById(client.getClientID());
        Assertions.assertNull(client2);
    }

    @Test
    public void updateClientTest(){
        ClientMapper clientMapper = new ClientMapperBuilder(db.getSession()).build();
        ClientDao clientDao = clientMapper.clientDao();
        Client client = new Client("Matthew", "Tar", "m.tar@isrp.ias.p.lodz.pl", new Admin());
        String newName = "Michael";
        clientDao.create(client);
        Client client2 = clientDao.findById(client.getClientID());
        Assertions.assertEquals(client2.toString(), client.toString());
        client.setFirstName(newName);
        clientDao.update(client);
        client2 = clientDao.findById(client.getClientID());
        Assertions.assertEquals(client2.toString(), client.toString());
    }

}

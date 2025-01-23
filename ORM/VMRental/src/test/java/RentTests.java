import org.example.DBConnection;
import org.example.dao.ClientDao;
import org.example.dao.RentDao;
import org.example.dao.VMachineDao;
import org.example.mapper.*;
import org.example.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

public class RentTests {
    DBConnection db = DBConnection.getInstance();
    @BeforeEach
    public void init() {
        db.initSession();
        db.createClientTable();
        db.createVMachineTable();
        db.createRentTables();
    }

    @AfterEach
    public void clean() {
        db.dropRentTables();
        db.dropVMachineTable();
        db.dropClientTable();
    }

    @Test
    public void addRentTest(){
        ClientMapper clientMapper = new ClientMapperBuilder(db.getSession()).build();
        VMachineMapper vMachineMapper = new VMachineMapperBuilder(db.getSession()).build();
        RentMapper rentMapper = new RentMapperBuilder(db.getSession()).build();
        ClientDao clientDao = clientMapper.clientDao();
        VMachineDao vMachineDao = vMachineMapper.vMachineDao();
        RentDao rentDao = rentMapper.rentDao();
        Client client = new Client("Matthew", "Tar", "m.tar@isrp.ias.p.lodz.pl", new Admin());
        VMachine vMachine = new x86("Intel", 8, "16GiB");
        Rent rent = new Rent(client, vMachine, LocalDateTime.now());
        clientDao.create(client);
        vMachineDao.create(vMachine);
        rentDao.create(rent);

        Rent rent2 = rentDao.findByVMachineId(vMachine.getUuid()).getFirst();
        rent2.setVMachine(vMachine);
        rent2.setClient(client);
        Assertions.assertEquals(rent2.toString(), rent.toString());
        rent2 = rentDao.findByClientId(client.getClientID()).getFirst();
        rent2.setVMachine(vMachine);
        rent2.setClient(client);
        Assertions.assertEquals(rent2.toString(), rent.toString());
    }

    @Test
    public void getRentTest(){
        ClientMapper clientMapper = new ClientMapperBuilder(db.getSession()).build();
        VMachineMapper vMachineMapper = new VMachineMapperBuilder(db.getSession()).build();
        RentMapper rentMapper = new RentMapperBuilder(db.getSession()).build();
        ClientDao clientDao = clientMapper.clientDao();
        VMachineDao vMachineDao = vMachineMapper.vMachineDao();
        RentDao rentDao = rentMapper.rentDao();
        Client client1 = new Client("Matthew", "Tar", "m.tar@isrp.ias.p.lodz.pl", new Admin());
        VMachine vMachine1 = new x86("Intel", 8, "16GiB");
        Rent rent1 = new Rent(client1, vMachine1, LocalDateTime.now());
        Client client2 = new Client("Michael", "Corrugated", "don.ias@pas.ias.p.lodz.pl", new Admin());
        VMachine vMachine2 = new x86("AMD", 16, "16GiB");
        Rent rent2 = new Rent(client2, vMachine2, LocalDateTime.now());
        Client client3 = new Client("Martin", "Bricky", "intel.enjoyer@architecture.assembly.com", new Standard());
        VMachine vMachine3 = new x86("Intel", 2, "1GiB");
        Rent rent3 = new Rent(client3, vMachine3, LocalDateTime.now());

        clientDao.create(client1);
        clientDao.create(client2);
        clientDao.create(client3);
        vMachineDao.create(vMachine1);
        vMachineDao.create(vMachine2);
        vMachineDao.create(vMachine3);
        rentDao.create(rent1);
        rentDao.create(rent2);
        rentDao.create(rent3);

        Rent test = rentDao.findByVMachineId(vMachine1.getUuid()).getFirst();
        test.setVMachine(vMachine1);
        test.setClient(client1);
        Assertions.assertEquals(rent1.toString(), test.toString());
        test = rentDao.findByClientId(client1.getClientID()).getFirst();
        test.setVMachine(vMachine1);
        test.setClient(client1);
        Assertions.assertEquals(rent1.toString(), test.toString());

        test = rentDao.findByVMachineId(vMachine2.getUuid()).getFirst();
        test.setVMachine(vMachine2);
        test.setClient(client2);
        Assertions.assertEquals(rent2.toString(), test.toString());
        test = rentDao.findByClientId(client2.getClientID()).getFirst();
        test.setVMachine(vMachine2);
        test.setClient(client2);
        Assertions.assertEquals(rent2.toString(), test.toString());

        test = rentDao.findByVMachineId(vMachine3.getUuid()).getFirst();
        test.setVMachine(vMachine3);
        test.setClient(client3);
        Assertions.assertEquals(rent3.toString(), test.toString());
        test = rentDao.findByClientId(client3.getClientID()).getFirst();
        test.setVMachine(vMachine3);
        test.setClient(client3);
        Assertions.assertEquals(rent3.toString(), test.toString());
    }

    @Test
    public void endRentTest(){
        ClientMapper clientMapper = new ClientMapperBuilder(db.getSession()).build();
        VMachineMapper vMachineMapper = new VMachineMapperBuilder(db.getSession()).build();
        RentMapper rentMapper = new RentMapperBuilder(db.getSession()).build();
        ClientDao clientDao = clientMapper.clientDao();
        VMachineDao vMachineDao = vMachineMapper.vMachineDao();
        RentDao rentDao = rentMapper.rentDao();
        Client client = new Client("Matthew", "Tar", "m.tar@isrp.ias.p.lodz.pl", new Admin());
        VMachine vMachine = new x86("Intel", 8, "16GiB");
        Rent rent = new Rent(client, vMachine, LocalDateTime.now());
        clientDao.create(client);
        vMachineDao.create(vMachine);
        rentDao.create(rent);
        Rent rent2 = rentDao.findByClientId(client.getClientID()).getFirst();
        rent2.setVMachine(vMachine);
        rent2.setClient(client);
        Assertions.assertEquals(rent2.toString(), rent.toString());
        Assertions.assertNull(rent2.getEndTime());
        rentDao.endRent(rent);
        rent2 = rentDao.findByClientId(client.getClientID()).getFirst();
        rent2.setVMachine(vMachine);
        rent2.setClient(client);
        Assertions.assertNotNull(rent2.getEndTime());
    }

}

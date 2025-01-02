import org.example.DBConnection;
import org.example.dao.RentDao;
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
        RentMapper rentMapper = new RentMapperBuilder(db.getSession()).build();
        RentDao rentDao = rentMapper.rentDao();
        Client client = new Client("Matthew", "Tar", "m.tar@isrp.ias.p.lodz.pl", new Admin());
        VMachine vMachine = new x86("Intel", 8, "16GiB");
        Rent rent = new Rent(client, vMachine, LocalDateTime.now());
        rentDao.create(rent);
        List<Rent> rents = rentDao.findByClientId(client.getClientID());
//        Assertions.assertEquals(rent2.toString(), rent.toString());
        int i = 1;
    }

//    @Test
//    public void getRentTest(){
//        RentMapper rentMapper = new RentMapperBuilder(db.getSession()).build();
//        RentDao rentDao = rentMapper.rentDao();
//        Rent rent1 = new Rent("Matthew", "Tar", "m.tar@isrp.ias.p.lodz.pl", new Admin());
//        Rent rent2 = new Rent("Michael", "Corrugated", "don.ias@corrugated.vs.tar", new Admin());
//        Rent rent3 = new Rent("Adam", "Notknowing", "i.dont.know@p.lodz.pl", new Standard());
//        rentDao.create(rent1);
//        rentDao.create(rent2);
//        rentDao.create(rent3);
//        Rent test = rentDao.findById(rent1.getRentID());
//        Assertions.assertEquals(test.toString(), rent1.toString());
//        test = rentDao.findById(rent2.getRentID());
//        Assertions.assertEquals(test.toString(), rent2.toString());
//        test = rentDao.findById(rent3.getRentID());
//        Assertions.assertEquals(test.toString(), rent3.toString());
//    }
//
//    @Test
//    public void removeRentTest(){
//        RentMapper rentMapper = new RentMapperBuilder(db.getSession()).build();
//        RentDao rentDao = rentMapper.rentDao();
//        Rent rent = new Rent("Matthew", "Tar", "m.tar@isrp.ias.p.lodz.pl", new Admin());
//        rentDao.create(rent);
//        Rent rent2 = rentDao.findById(rent.getRentID());
//        Assertions.assertEquals(rent2.toString(), rent.toString());
//        rentDao.delete(rent);
//        rent2 = rentDao.findById(rent.getRentID());
//        Assertions.assertNull(rent2);
//    }
//
//    @Test
//    public void updateRentTest(){
//        RentMapper rentMapper = new RentMapperBuilder(db.getSession()).build();
//        RentDao rentDao = rentMapper.rentDao();
//        Rent rent = new Rent("Matthew", "Tar", "m.tar@isrp.ias.p.lodz.pl", new Admin());
//        String newName = "Michael";
//        rentDao.create(rent);
//        Rent rent2 = rentDao.findById(rent.getRentID());
//        Assertions.assertEquals(rent2.toString(), rent.toString());
//        rent.setFirstName(newName);
//        rentDao.update(rent);
//        rent2 = rentDao.findById(rent.getRentID());
//        Assertions.assertEquals(rent2.toString(), rent.toString());
//    }
}

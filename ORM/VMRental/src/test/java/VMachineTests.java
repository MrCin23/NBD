import org.example.DBConnection;
import org.example.dao.VMachineDao;
import org.example.mapper.*;
import org.example.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VMachineTests {
    DBConnection db = DBConnection.getInstance();
    @BeforeEach
    public void init() {
        db.initSession();
        db.createVMachineTable();
    }

    @AfterEach
    public void clean() {
        db.dropVMachineTable();
    }

    @Test
    public void addVMachineTest(){
        VMachineMapper vMachineMapper = new VMachineMapperBuilder(db.getSession()).build();
        VMachineDao vMachineDao = vMachineMapper.vMachineDao();
        VMachine vMachine = new x86("Intel", 8, "16GiB");
        vMachineDao.create(vMachine);
        VMachine vMachine2 = vMachineDao.findById(vMachine.getUuid());
        Assertions.assertEquals(vMachine2.toString(), vMachine.toString());
    }

    @Test
    public void getVMachineTest(){
        VMachineMapper vMachineMapper = new VMachineMapperBuilder(db.getSession()).build();
        VMachineDao vMachineDao = vMachineMapper.vMachineDao();
        VMachine vMachine1 = new x86("Intel", 8, "16GiB");
        VMachine vMachine2 = new x86("AMD", 16, "32GiB");
        VMachine vMachine3 = new AppleArch(6, "12GiB");
        vMachineDao.create(vMachine1);
        vMachineDao.create(vMachine2);
        vMachineDao.create(vMachine3);
        VMachine test = vMachineDao.findById(vMachine1.getUuid());
        Assertions.assertEquals(test.toString(), vMachine1.toString());
        test = vMachineDao.findById(vMachine2.getUuid());
        Assertions.assertEquals(test.toString(), vMachine2.toString());
        test = vMachineDao.findById(vMachine3.getUuid());
        Assertions.assertEquals(test.toString(), vMachine3.toString());
    }

    @Test
    public void removeVMachineTest(){
        VMachineMapper vMachineMapper = new VMachineMapperBuilder(db.getSession()).build();
        VMachineDao vMachineDao = vMachineMapper.vMachineDao();
        VMachine vMachine = new x86("Intel", 8, "16GiB");
        vMachineDao.create(vMachine);
        VMachine vMachine2 = vMachineDao.findById(vMachine.getUuid());
        Assertions.assertEquals(vMachine2.toString(), vMachine.toString());
        vMachineDao.delete(vMachine);
        Assertions.assertThrows(AssertionError.class, () -> {
            vMachineDao.findById(vMachine.getUuid());
        });
    }

    @Test
    public void updateVMachineTest(){
        VMachineMapper vMachineMapper = new VMachineMapperBuilder(db.getSession()).build();
        VMachineDao vMachineDao = vMachineMapper.vMachineDao();
        VMachine vMachine = new x86("Intel", 8, "16GiB");
        String newRamSize = "32GiB";
        vMachineDao.create(vMachine);
        VMachine vMachine2 = vMachineDao.findById(vMachine.getUuid());
        Assertions.assertEquals(vMachine2.toString(), vMachine.toString());
        vMachine.setRamSize(newRamSize);
        vMachineDao.update(vMachine);
        VMachine vMachine3 = vMachineDao.findById(vMachine.getUuid());
        Assertions.assertEquals(vMachine.getRamSize(), vMachine3.getRamSize()); //todo nie działa dla zmian liczb
    }
}

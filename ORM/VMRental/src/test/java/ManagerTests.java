import org.example.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ManagerTests {
    //Managers
    ClientManager cm = new ClientManager();
    VMachineManager vmm = new VMachineManager();
    RentManager rm = new RentManager();
    ClientType standardAccount = new Standard();
    ClientType admin = new Admin();
    Client c = new Client(3L, "Adam", "Nowak", "cti@p.lodz.pl", admin);

    @Test
    public void addElementTest() {
        //ClientManager
        Assertions.assertEquals(cm.getElementsAmount(), 0);
        cm.registerExsistingClient(c);
        Assertions.assertEquals(cm.getElementsAmount(), 1);
        Client cc = cm.getClient(c.getID());
        Assertions.assertEquals(c, cc);
        //VMachineManager
        VMachine normalVMachine = new x86(1L, 8, "16GiB", "AMD");
        VMachine appleVMachine = new AppleArch(2L, 8, "16GiB");
        Assertions.assertEquals(vmm.getElementsAmount(), 0);
        vmm.registerExsistingVMachine(normalVMachine);
        Assertions.assertEquals(vmm.getElementsAmount(), 1);
        vmm.registerExsistingVMachine(appleVMachine);
        Assertions.assertEquals(vmm.getElementsAmount(), 2);
        VMachine vMachine = vmm.getVMachine(normalVMachine.getID());
        Assertions.assertEquals(vMachine, normalVMachine);
    }

    @Test
    public void removeElementTest() {
        cm.unregisterClient(cm.getClient(c.getID()));
        Assertions.assertEquals(cm.getElementsAmount(), 0);
    }


}

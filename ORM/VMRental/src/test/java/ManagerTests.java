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
    Client c = new Client("Adam", "Nowak", "cti@p.lodz.pl", admin);

    @Test
    public void addElementTest() {
        //ClientManager
        Assertions.assertEquals(cm.getClientsAmount(), 0);
        cm.registerExistingClient(c);
        Assertions.assertEquals(cm.getClientsAmount(), 1);
        //VMachineManager
        VMachine normalVMachine = new x86(8, "16GiB", "AMD");
        VMachine appleVMachine = new AppleArch(8, "16GiB");
        Assertions.assertEquals(vmm.getVMachinesAmount(), 0);
        vmm.registerExistingVMachine(normalVMachine);
        Assertions.assertEquals(vmm.getVMachinesAmount(), 1);
        vmm.registerExistingVMachine(appleVMachine);
        Assertions.assertEquals(vmm.getVMachinesAmount(), 2);
    }

    @Test
    public void removeElementTest() {
        cm.registerExistingClient(c);
        VMachine normalVMachine = new x86(8, "16GiB", "AMD");
        VMachine appleVMachine = new AppleArch(8, "16GiB");
        vmm.registerExistingVMachine(normalVMachine);
        vmm.registerExistingVMachine(appleVMachine);
        cm.unregisterClient(1);
        Assertions.assertEquals(cm.getClientsAmount(), 0);
        Assertions.assertEquals(vmm.getVMachinesAmount(), 2);
        vmm.unregisterVMachine(1);
        Assertions.assertEquals(vmm.getVMachinesAmount(), 1);
        vmm.unregisterVMachine(2);
        Assertions.assertEquals(vmm.getVMachinesAmount(), 0);
    }
}

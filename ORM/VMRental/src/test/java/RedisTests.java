import org.example.exception.RedisConnectionError;
import org.example.manager.ClientManager;
import org.example.manager.RentManager;
import org.example.manager.VMachineManager;
import org.example.model.*;
import org.example.repository.RentRedisRepository;
import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RedisTests {
    RentManager rm = RentManager.getInstance();
    ClientManager cm = ClientManager.getInstance();
    VMachineManager vmm = VMachineManager.getInstance();
    List<Client> clients = new ArrayList<>();
    List<VMachine> vms = new ArrayList<>();

    @BeforeEach
    void setup(){
        clients.add(new Client("Bart", "Fox", "BFox@tul.com", new Admin()));
        clients.add(new Client("Michael", "Corrugated", "MCorrugated@ias.pas.p.lodz.pl", new Admin()));
        clients.add(new Client("Matthew", "Tar", "MTar@TarVSCorrugated.com", new Admin()));
        clients.add(new Client("Martin", "Bricky", "IntelEnjoyer@whatisonpage4035.com", new Standard()));
        clients.add(new Client("Adam", "Notknowning", "Idontknow@notknowning.com", new Standard()));
        vms.add(new AppleArch(4, "4GB"));
        vms.add(new AppleArch(24, "128GB"));
        vms.add(new x86(8, "8GB", "AMD"));
        vms.add(new x86(16, "32GB", "Intel"));
        vms.add(new x86(128, "256GB", "Other"));
        vms.add(new x86(128, "256GB", "Other"));
        cm.registerExistingClient(clients.get(0));
        cm.registerExistingClient(clients.get(1));
        cm.registerExistingClient(clients.get(2));
        cm.registerExistingClient(clients.get(3));
        cm.registerExistingClient(clients.get(4));
        vmm.registerExistingVMachine(vms.get(0));
        vmm.registerExistingVMachine(vms.get(1));
        vmm.registerExistingVMachine(vms.get(2));
        vmm.registerExistingVMachine(vms.get(3));
        vmm.registerExistingVMachine(vms.get(4));
        vmm.registerExistingVMachine(vms.get(5));
        rm.clearAllCache();
    }

    @Test
    public void addTest(){
        Rent rent1 = new Rent(clients.get(0), vms.get(0), LocalDateTime.of(2024,11,11,11,11));
        Rent rent2 = new Rent(clients.get(1), vms.get(1), LocalDateTime.of(2024,12,12,12,12));
        try {
            RentRedisRepository rrr = new RentRedisRepository();
            rrr.add(rent1);
            rrr.add(rent2);
            rrr.getRentByID(rent1.getEntityId());
            rrr.getRentByID(rent2.getEntityId());
            Assertions.assertEquals(rent1.toString(), rrr.getRentByID(rent1.getEntityId()).toString());
        } catch (RedisConnectionError ignored) {}
    }
    @Test
    public void removeTest(){
        Rent rent1 = new Rent(clients.get(0), vms.get(0), LocalDateTime.of(2024,11,11,11,11));
        Rent rent2 = new Rent(clients.get(1), vms.get(1), LocalDateTime.of(2024,12,12,12,12));
        try {
        RentRedisRepository rrr = new RentRedisRepository();
        rrr.add(rent1);
        rrr.add(rent2);
        Assertions.assertEquals(2,rrr.size());
        rrr.endRent(rent1.getEntityId(), LocalDateTime.of(2024,11,11,11,12));
        Assertions.assertEquals(1,rrr.size());
        } catch (RedisConnectionError ignored) {}
    }
}
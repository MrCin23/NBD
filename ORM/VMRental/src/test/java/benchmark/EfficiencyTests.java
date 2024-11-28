package benchmark;

import org.example.manager.ClientManager;
import org.example.manager.RentManager;
import org.example.manager.VMachineManager;
import org.example.model.*;
import org.example.repository.RentRedisRepository;
import org.example.repository.RentRepository;
import org.example.repository.RentRepositoryDecorator;
import org.junit.jupiter.api.*;
import org.openjdk.jmh.annotations.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
public class EfficiencyTests {
    ClientManager cm = ClientManager.getInstance();
    VMachineManager vmm = VMachineManager.getInstance();
    List<Client> clients = new ArrayList<>();
    List<VMachine> vms = new ArrayList<>();
    RentRedisRepository rrr = new RentRedisRepository();
    RentRepository rr = new RentRepository();
    RentRepositoryDecorator rrd = new RentRepositoryDecorator(rr, rrr);
    Rent rent1;
    Rent rent2;
    Rent rent3;
    Rent rent4;
    Rent rent5;
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
        rent1 = new Rent(clients.get(0), vms.get(0), LocalDateTime.of(2024,11,11,11,11));
        rent2 = new Rent(clients.get(1), vms.get(1), LocalDateTime.of(2024,11,11,11,11));
        rent3 = new Rent(clients.get(2), vms.get(2), LocalDateTime.of(2024,11,11,11,11));
        rent4 = new Rent(clients.get(3), vms.get(3), LocalDateTime.of(2024,11,11,11,11));
        rent5 = new Rent(clients.get(4), vms.get(4), LocalDateTime.of(2024,11,11,11,11));
        rrr.add(rent1);
        rrr.add(rent2);
        rr.add(rent3);
        rr.add(rent4);
    }

    @Test
    @Benchmark
    @Fork(value=10, warmups=0)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void cacheHitTest(){
        rrd.getRentByID(rent1.getEntityId());
    }
    @Test
    @Benchmark
    @Fork(value=10, warmups=0)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void cacheMissTest(){
        rrd.getRentByID(rent3.getEntityId());
    }
    @Test
    @Benchmark
    @Fork(value=10, warmups=0)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void mongoTest(){
        rr.getRentByID(rent3.getEntityId());
    }
    @Test
    @Benchmark
    @Fork(value=10, warmups=0)
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void mongoMissingTest(){
        rr.getRentByID(rent5.getEntityId());
    }
}

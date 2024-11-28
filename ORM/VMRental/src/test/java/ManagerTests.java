import org.example.manager.ClientManager;
import org.example.manager.RentManager;
import org.example.manager.VMachineManager;
import org.example.model.*;
import org.example.repository.RentRedisRepository;
import org.example.repository.RentRepository;
import org.example.repository.RentRepositoryDecorator;
import org.junit.jupiter.api.*;
import org.openjdk.jmh.annotations.Setup;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ManagerTests {
    ClientManager cm = ClientManager.getInstance();
    VMachineManager vmm = VMachineManager.getInstance();
    RentManager rm = RentManager.getInstance();
    List<Client> clients = new ArrayList<>();
    List<VMachine> vms = new ArrayList<>();
    RentRedisRepository rrr = new RentRedisRepository();
    RentRepository rr = new RentRepository();
    RentRepositoryDecorator rrd = new RentRepositoryDecorator(rr, rrr);
    @BeforeEach
    public void setup(){
        clients.clear();
        vms.clear();
        rrr.clearAllCache();
        cm.dropAndCreate();
        vmm.dropAndCreate();
        rm.dropAndCreate();
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
    }
    @Test
    public void createElementTest() {
        //ClientManager
        Assertions.assertEquals(cm.getClientsAmount(), 0);
        cm.registerExistingClient(clients.get(0));
        Assertions.assertEquals(cm.getClientsAmount(), 1);
        Assertions.assertEquals(clients.get(0).toString(), cm.getClient(clients.get(0).getEntityId()).toString());
        //VMachineManager
        Assertions.assertEquals(0, vmm.getVMachinesAmount());
        vmm.registerExistingVMachine(vms.get(0));
        Assertions.assertEquals(1, vmm.getVMachinesAmount());
        vmm.registerExistingVMachine(vms.get(2));
        Assertions.assertEquals(2, vmm.getVMachinesAmount());
        Assertions.assertEquals(vms.get(2).toString(), vmm.getVMachine(vms.get(2).getEntityId()).toString());
        //RentManager
        Assertions.assertEquals(0, rm.size());
        Rent rent = new Rent(clients.get(0), vms.get(0), LocalDateTime.of(2024,11,15,21,37));
        rm.registerExistingRent(rent);
        Assertions.assertEquals(1, rm.size());
        Assertions.assertEquals(rent.toString(), rm.getRent(rent.getEntityId()).toString());
        Assertions.assertEquals(1, rm.size());
    }
    @Test
    public void updateElementTest() {
        Map<String, Object> updates = new HashMap<>();
        updates.put("CPUNumber", 111);
        updates.put("ramSize", "111GB");
        vmm.registerExistingVMachine(vms.get(2));
        Assertions.assertEquals(8, vmm.getVMachine(vms.get(2).getEntityId()).getCPUNumber());
        Assertions.assertEquals("8GB", vmm.getVMachine(vms.get(2).getEntityId()).getRamSize());
        vmm.update(vms.get(2).getEntityId(), updates);
        Assertions.assertEquals(111, vmm.getVMachine(vms.get(2).getEntityId()).getCPUNumber());
        Assertions.assertEquals("111GB", vmm.getVMachine(vms.get(2).getEntityId()).getRamSize());
    }
    @Test
    public void readElementTest() {
        cm.registerExistingClient(clients.get(2));
        Assertions.assertEquals(cm.getClient(clients.get(2).getEntityId()).toString(), clients.get(2).toString());
    }
    @Test
    public void deleteElementTest() {
        cm.registerExistingClient(clients.get(3));
        Assertions.assertEquals(cm.getClientsAmount(), 1);
        cm.unregisterClient(clients.get(3));
        Assertions.assertEquals(cm.getClientsAmount(), 0);

        vmm.registerExistingVMachine(vms.get(2));
        Assertions.assertEquals(vmm.getVMachinesAmount(), 1);
        vmm.unregisterVMachine(vms.get(2));
        Assertions.assertEquals(vmm.getVMachinesAmount(), 0);
    }

    @Test
    public void bomb() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "mongosh --host mongodb1:27017 --username admin --password adminpassword --authenticationDatabase admin --eval \"rs.status().members.filter(member => member.stateStr === 'PRIMARY')[0].name\"");

            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            StringBuilder output = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            if(output.isEmpty()){ //this is for debugging only and SHOULD never happen
                while ((line = errReader.readLine()) != null) {
                    output.append(line).append("\n");
                }
                throw new RuntimeException(output.toString());
            }


            process.waitFor();
            String toKill = output.toString();
            System.out.println(toKill);
            String hostname = toKill.substring(0, toKill.indexOf(":"));
            ProcessBuilder processBuilder2 = new ProcessBuilder("cmd.exe", "/c", "docker stop " + hostname);
            Process process2 = processBuilder2.start();
            process2.waitFor();

            createElementTest();
            updateElementTest();
            readElementTest();
            deleteElementTest();

            ProcessBuilder processBuilder3 = new ProcessBuilder("cmd.exe", "/c", "docker start " + hostname);
            Process process3 = processBuilder3.start();
            process3.waitFor();
            Thread.sleep(3000);
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
    @Test
    public void valid() {
        try {
            createElementTest();
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "mongosh --host mongodb1:27017 --username admin --password adminpassword --authenticationDatabase admin --eval \"use('vmrental'); db.rents.validate()\"");
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            StringBuilder output = new StringBuilder();
            String line;
            boolean cierpienie = false;

            while ((line = reader.readLine()) != null) {
                if (line.trim().startsWith("valid:")) {
                    String value = line.split(":")[1].trim().replace(",", "");
                    cierpienie = Boolean.parseBoolean(value);
                    break;
                }
            }
            if(output.isEmpty()){ //this is for debugging only and SHOULD never happen
                while ((line = errReader.readLine()) != null) {
                    output.append(line).append("\n");
                }
                throw new RuntimeException(output.toString());
            }
            process.waitFor();
            String toKill = output.toString();
            if(cierpienie){
                System.out.println("status: " + cierpienie);
            } else {
                throw new Exception("rents nie jest spójne!");
            }

            processBuilder = new ProcessBuilder("cmd.exe", "/c", "mongosh --host mongodb1:27017 --username admin --password adminpassword --authenticationDatabase admin --eval \"use('vmrental'); db.clients.validate()\"");
            process = processBuilder.start();
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            output = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                if (line.trim().startsWith("valid:")) {
                    String value = line.split(":")[1].trim().replace(",", "");
                    cierpienie = Boolean.parseBoolean(value);
                    break;
                }
            }
            process.waitFor();
            if(cierpienie){
                System.out.println("status: " + cierpienie);
            } else {
                throw new Exception("clients nie jest spójne!");
            }

            processBuilder = new ProcessBuilder("cmd.exe", "/c", "mongosh --host mongodb1:27017 --username admin --password adminpassword --authenticationDatabase admin --eval \"use('vmrental'); db.vMachines.validate()\"");
            process = processBuilder.start();
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            output = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                if (line.trim().startsWith("valid:")) {
                    String value = line.split(":")[1].trim().replace(",", "");
                    cierpienie = Boolean.parseBoolean(value);
                    break;
                }
            }
            process.waitFor();
            if(cierpienie){
                System.out.println("status: " + cierpienie);
            } else {
                throw new Exception("vMachines nie jest spójne!");
            }
        } catch (Exception e) {
            Assertions.fail(e);
        }
    }
}

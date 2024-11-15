import org.example.*;
import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ManagerTests {
    //Managers
    ClientManager cm = new ClientManager();
    VMachineManager vmm = new VMachineManager();
    RentManager rm = new RentManager();
    ClientType standardAccount = new Standard();
    ClientType admin = new Admin();
    Client c = new Client("Adam", "Nowak", "cti@p.lodz.pl", admin);
    VMachine normalVMachine = new x86(8, "16GiB", "AMD");
    VMachine appleVMachine = new AppleArch(8, "16GiB");

    @Test
    @Order(1)
    public void createElementTest() {
        //ClientManager
        Assertions.assertEquals(cm.getClientsAmount(), 0);
        cm.registerExistingClient(c);
        Assertions.assertEquals(cm.getClientsAmount(), 1);
        Client cc = cm.getClient(c.getEntityId());
        Assertions.assertEquals(c.toString(), cc.toString());
        //VMachineManager
        Assertions.assertEquals(vmm.getVMachinesAmount(), 0);
        vmm.registerExistingVMachine(normalVMachine);
        Assertions.assertEquals(vmm.getVMachinesAmount(), 1);
        vmm.registerExistingVMachine(appleVMachine);
        Assertions.assertEquals(vmm.getVMachinesAmount(), 2);
        VMachine vMachine = vmm.getVMachine(normalVMachine.getEntityId());
        Assertions.assertEquals(vMachine.toString(), normalVMachine.toString());
        //RentManager
        Assertions.assertEquals(rm.size(), 0);
        Rent rent = new Rent(c, normalVMachine, LocalDateTime.of(2024,11,15,21,37));
        rm.registerExistingRent(rent);
        Assertions.assertEquals(rm.size(), 1);
        Rent rent2 = rm.getRent(rent.getEntityId());
        Assertions.assertEquals(rent.toString(), rent2.toString());
        Assertions.assertEquals(rm.size(), 1);
    }
    @Test
    @Order(2)
    public void updateElementTest() {
        VMachine vm1 = new x86(8, "16GiB", "AMD");
        Map<String, Object> updates = new HashMap<>();
        updates.put("CPUNumber", 16);
        updates.put("ramSize", "8GB");
        vmm.registerExistingVMachine(vm1);
        vmm.update(vm1.getEntityId(), updates);
        Assertions.assertEquals(vmm.getVMachine(vm1.getEntityId()).getCPUNumber(), 16);
        Assertions.assertEquals(vmm.getVMachine(vm1.getEntityId()).getRamSize(), "8GB");
    }
    @Test
    @Order(3)
    public void readElementTest() {
        cm.registerExistingClient(c);
        Assertions.assertEquals(cm.getClient(c.getEntityId()).toString(), c.toString());
    }
    @Test
    @Order(4)
    public void deleteElementTest() {
        cm.registerExistingClient(c);
        Assertions.assertEquals(cm.getClientsAmount(), 1);
        cm.unregisterClient(c);
        Assertions.assertEquals(cm.getClientsAmount(), 0);

        vmm.registerExistingVMachine(normalVMachine);
        Assertions.assertEquals(vmm.getVMachinesAmount(), 1);
        vmm.unregisterVMachine(normalVMachine);
        Assertions.assertEquals(vmm.getVMachinesAmount(), 0);
    }

    @Test
    @Order(5)
    public void bomb() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c", "mongosh --host mongodb1:27017 --username admin --password adminpassword --authenticationDatabase admin --eval \"rs.status().members.filter(member => member.stateStr === 'PRIMARY')[0].name\"");

            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            process.waitFor();
            String toKill = output.toString();
            System.out.println(toKill);
            String hostname = toKill.substring(0, toKill.indexOf(":"));
            ProcessBuilder processBuilder2 = new ProcessBuilder("cmd.exe", "/c", "docker stop " + hostname);
            Process process2 = processBuilder2.start();
            process2.waitFor();


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

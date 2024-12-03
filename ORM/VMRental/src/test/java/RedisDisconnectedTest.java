import org.example.exception.RedisConnectionError;
import org.example.manager.ClientManager;
import org.example.manager.RentManager;
import org.example.manager.VMachineManager;
import org.example.model.*;
import org.example.repository.RentRedisRepository;
import org.example.repository.RentRepository;
import org.example.repository.RentRepositoryDecorator;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RedisDisconnectedTest {
    @Test
    public void redisContainerTest(){
        try (GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:7.4.1")).withExposedPorts(6379)) {
            redis.start();
            String redisHost = redis.getHost();
            int redisPort = redis.getFirstMappedPort();
            RentRedisRepository rentRedisRepository = new RentRedisRepository(redisHost, redisPort);
            rentRedisRepository.clearAllCache();
            redis.stop();
        } catch (RedisConnectionError e) {
            Assertions.fail(e);
        }
    }

    @Test
    public void getRents(){
        try (GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:7.4.1")).withExposedPorts(6379)) {
            redis.start();
            String redisHost = redis.getHost();
            int redisPort = redis.getFirstMappedPort();
            RentRedisRepository rentRedisRepository = new RentRedisRepository(redisHost, redisPort);
            RentRepository rentRepository = new RentRepository();
            RentRepositoryDecorator rentRepositoryDecorator = new RentRepositoryDecorator(rentRepository, rentRedisRepository);
            ClientManager cm = ClientManager.getInstance();
            VMachineManager vmm = VMachineManager.getInstance();
            List<Client> clients = new ArrayList<>();
            List<VMachine> vms = new ArrayList<>();
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
            Rent rent1 = new Rent(clients.get(0), vms.get(0), LocalDateTime.of(2024, 11, 11, 11, 11, 11));
            Rent rent2 = new Rent(clients.get(1), vms.get(1), LocalDateTime.of(2024, 11, 11, 11, 11, 11));
            Rent rent3 = new Rent(clients.get(2), vms.get(2), LocalDateTime.of(2024, 11, 11, 11, 11, 11));
            Rent rent4 = new Rent(clients.get(3), vms.get(3), LocalDateTime.of(2024, 11, 11, 11, 11, 11));
            rentRepositoryDecorator.clearAllCache();
            rentRepositoryDecorator.add(rent1);
            rentRepositoryDecorator.add(rent2);
            rentRepositoryDecorator.add(rent3);
            rentRepositoryDecorator.add(rent4);
            rentRepositoryDecorator.endRent(rent1.getEntityId(), LocalDateTime.of(2024, 11, 15, 11, 11, 12));
            rentRepositoryDecorator.endRent(rent2.getEntityId(), LocalDateTime.of(2024, 11, 15, 11, 11, 12));

            try {
                Assertions.assertEquals(2, rentRepositoryDecorator.getRentRedisRepository().getRents().size());
            } catch (RedisConnectionError e) {
                System.out.println("REDIS DISCONNECTED");
            }

            Assertions.assertTrue(redis.isRunning());
            Assertions.assertEquals(2, rentRepositoryDecorator.getRents().size());
            redis.stop();
            Assertions.assertFalse(redis.isRunning());
            Assertions.assertEquals(4, rentRepositoryDecorator.getRents().size());

        } catch (RedisConnectionError e) {
            Assertions.fail(e);
        }
    }
}
import org.example.manager.RentManager;
import org.example.model.*;
import org.example.repository.Consumer;
import org.example.repository.Producer;
import org.example.repository.RentRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Execution(ExecutionMode.CONCURRENT)
public class KafkaTest {
    public static String topic = "testTopic";
    public static UUID rentId;
    @Test
    @Order(1)
    public void testProducerSendMessage() {
        Client client = new Client("clientname", "clientsurname", "clientemail", new Standard());
        VMachine vm = new x86(16, "16GB", "AMD");
        Rent rent = new Rent(client, vm, LocalDateTime.of(2015,12,12,12,12,12));
        //rent.getEntityId().setUuid(UUID.fromString("00000000-0000-0000-0000-000000000000"));
        rentId = rent.getEntityId().getUuid();
        try {
//            Producer.deleteTopic(topic);
            Producer.sendMessage(topic, rent);
        } catch (InterruptedException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    @Order(2)
    public void testConsumerEatMessage() {
        try {
            Consumer.consumeTopicsByGroup(topic);
        } catch (InterruptedException e) {
            Assertions.fail(e.getMessage());
        }
        RentManager rm = RentManager.getInstance();
        Assertions.assertNotNull(rm.getRent(new MongoUUID(rentId)));
    }

    @Test
    @Order(3)
    public void testDuplicateRead() {
        testProducerSendMessage();
        testConsumerEatMessage();
        testConsumerEatMessage();
        RentManager rm = RentManager.getInstance();
        Assertions.assertEquals(1, rm.size());
    }
}

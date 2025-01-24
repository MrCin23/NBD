import org.example.manager.RentManager;
import org.example.model.*;
import org.example.repository.Consumer;
import org.example.repository.Producer;
import org.junit.jupiter.api.*;
import java.time.LocalDateTime;
import java.util.UUID;

//@Execution(ExecutionMode.CONCURRENT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
            Producer.deleteTopic(topic);
            Producer.createTopic(topic);
            Producer.sendMessage(topic, rent);
        } catch (InterruptedException e) {
            Assertions.fail(e.getMessage());
        }
//        Producer.deleteTopic(topic);
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
//        try {
//            Producer.deleteTopic(topic);
//            Producer.createTopic(topic);
//            Client client = new Client("2clientname", "2clientsurname", "2clientemail", new Standard());
//            VMachine vm = new x86(16, "16GB", "AMD");
//            Rent rent = new Rent(client, vm, LocalDateTime.of(2015,12,12,12,12,12));
//            Producer.sendMessage(topic, rent);
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            Assertions.fail(e.getMessage());
//        }
//        try {
//            System.out.println("It works now");
//            Consumer.consumeTopicsByGroup(topic);
//        } catch (InterruptedException e) {
//            Assertions.fail(e.getMessage());
//        }
//        try {
//            System.out.println("It works now");
//            Consumer.consumeTopicsByGroup(topic);
//        } catch (InterruptedException e) {
//            Assertions.fail(e.getMessage());
//        }
//        RentManager rm = RentManager.getInstance();
//        Assertions.assertEquals(1, rm.size());
    }

    @AfterAll
    public static void tearDown() {
        Producer.deleteTopic(topic);
    }
}

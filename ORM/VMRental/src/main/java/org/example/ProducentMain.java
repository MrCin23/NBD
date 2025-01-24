package org.example;

import org.example.model.*;
import org.example.repository.Producer;

import java.time.LocalDateTime;

public class ProducentMain {

    public static void main(String[] args) {

        try {
            Client client = new Client("Mathew", "Tar", "MTar@ias.allegro.com", new Standard());
            VMachine vMachine = new AppleArch(4, "8GB");
            Rent rent = new Rent(client, vMachine, LocalDateTime.now());
//            Producer.deleteTopic("rents");
            Thread.sleep(1000);
            if(!Producer.doesTopicExist("rents")) {
                Producer.createTopic("rents");
            }
            Producer.sendMessage("rents", rent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

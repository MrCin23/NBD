package org.example;


import org.example.repository.Consumer;

import java.io.IOException;

public class ConsumerMain {


    public static void main(String[] args) throws IOException, InterruptedException {
//        do {
//            System.out.println("Oczekiwanie na powstanie tematu");
//            Thread.sleep(1000);
//        } while(!Consumer.doesTopicExist("rents"));
//        System.out.println("It works now");
//        Consumer.createTopic("rents");
        Consumer.consumeTopicsByGroup("rents");


    }
}

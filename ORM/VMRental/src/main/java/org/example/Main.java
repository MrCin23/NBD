package org.example;

import org.example.repository.ClientRepository;

public class Main {
    public static void main(String[] args) {
        DBConnection db = DBConnection.getInstance();
        //db.initSession();
        //db.createSchema();
        ClientRepository cr = new ClientRepository();
        db.closeSession();
    }
}

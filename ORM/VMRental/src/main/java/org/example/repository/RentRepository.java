package org.example.repository;

import com.mongodb.MongoCommandException;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.*;
import org.bson.conversions.Bson;
import org.example.model.Client;
import org.example.model.MongoUUID;
import org.example.model.Rent;
import org.example.model.VMachine;

import java.time.LocalDateTime;
import java.util.*;

public class RentRepository extends AbstractMongoRepository implements RentDataSource{
    private final MongoCollection<Rent> rents;
    private final MongoCollection<VMachine> vMachines;
    private final MongoCollection<Client> clients;

    public RentRepository() {
        super.initDbConnection();
        MongoIterable<String> list = this.getDatabase().listCollectionNames();
        for (String name : list) {
            if (name.equals("rents")) {
                this.getDatabase().getCollection(name).drop();
                break;
            }
        }
        this.getDatabase().createCollection("rents");

        this.rents = this.getDatabase().getCollection("rents", Rent.class);
        this.vMachines = this.getDatabase().getCollection("vMachines", VMachine.class);
        this.clients = this.getDatabase().getCollection("clients", Client.class);
    }

    @Override
    public void endRent(MongoUUID uuid, LocalDateTime endTime){
        ClientSession session = getMongoClient().startSession();
        try {
            session.startTransaction();

            Bson filter1 = Filters.eq("_id", uuid.getUuid());
            Rent rent = rents.find(filter1).first();
            if(rent == null){
                throw new RuntimeException("Rent not found");
            } else if(rent.getEndTime() == null) {
                rent.endRent(endTime);
                Bson update1 = Updates.set("endTime", rent.getEndTime());
                rents.updateOne(session, filter1, update1);
                update1 = Updates.set("rentCost", rent.getRentCost());
                rents.updateOne(session, filter1, update1);
            }

            Bson filter = Filters.eq("_id", rent.getVMachine().getEntityId().getUuid().toString());
            Bson update = Updates.inc("isRented", -1);
            vMachines.updateOne(session, filter, update);

            Bson filter2 = Filters.eq("_id", rent.getClient().getEntityId().getUuid().toString());
            Bson update2 = Updates.inc("currentRents", -1);
            vMachines.updateOne(session, filter2, update2);

            session.commitTransaction();
        } catch (MongoCommandException ex) {
            session.abortTransaction();
        } finally {
            session.close();
        }
    }

    @Override
    public void add(Rent rent) {
        ClientSession session = getMongoClient().startSession();
        Client client;
        try {
            session.startTransaction();
            Bson clientFilter = Filters.eq("_id", rent.getClient().getEntityId().getUuid());
            Bson updateClientFilter = Updates.inc("currentRents", 1);
            clients.updateOne(session, clientFilter, updateClientFilter);
            Bson currentRentsFilter = Filters.lt("currentRents", rent.getClient().getClientType().getMaxRentedMachines());
            client = clients.find(Filters.and(clientFilter, currentRentsFilter)).first();
            if(client == null){
                throw new Exception("");
            }
            Bson filter = Filters.eq("_id", rent.getVMachine().getEntityId().getUuid().toString());
            Bson update = Updates.inc("isRented", 1);
            vMachines.updateOne(session, filter, update);
            rents.insertOne(rent);
            session.commitTransaction();
        } catch (Exception ex) {
            session.abortTransaction();
        } finally {
            session.close();
        }

    }

    @Override
    public void remove(MongoUUID uuid) {
        throw new RuntimeException("This should never be called");
    }

    @Override
    public long size() {
        return rents.countDocuments();
    }

//    public List<Rent> getRents(boolean active) {
//        return rents.find().into(new ArrayList<>());
//        //TODO
//    }

    @Override
    public List<Rent> getRents() {
        return rents.find().into(new ArrayList<>());
    }

    @Override
    public Rent getRentByID(MongoUUID uuid) {
        Bson filter = Filters.eq("_id", uuid.getUuid());
        return rents.find(filter).first();
    }

    public void dropAndCreate(){
        this.getDatabase().getCollection("rents").drop();
        this.getDatabase().createCollection("rents");
    }
}

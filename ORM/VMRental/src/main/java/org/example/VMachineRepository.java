package org.example;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VMachineRepository extends AbstractMongoRepository {
    private final String collectionName = "vMachines";
    private final MongoCollection<VMachine> vMachines;

    public VMachineRepository() {
        super.initDbConnection();
        MongoIterable<String> list = this.getDatabase().listCollectionNames();
        for (String name : list) {
            if (name.equals(collectionName)) {
                this.getDatabase().getCollection(name).drop();
                break;
            }
        }

        this.getDatabase().createCollection(collectionName);

        this.vMachines = this.getDatabase().getCollection(collectionName, VMachine.class);
    }

    //-------------METHODS---------------------------------------
    //TODO dorobić metody z diagramu

    public void update(long id, Map<String, Object> fieldsToUpdate) {
//        try (Session session = sessionFactory.openSession()) {
//            Transaction transaction = session.beginTransaction();
//
//            VMachine vMachine = session.createQuery("FROM VMachine vm WHERE vm.vMachineID = :vMachineID", VMachine.class)
//                    .setParameter("vMachineID", id)
//                    .uniqueResult();
//
//            for (Map.Entry<String, Object> entry : fieldsToUpdate.entrySet()) {
//                String fieldName = entry.getKey();
//                Object fieldValuee = entry.getValue();
//                try {
//                    Field field = VMachine.class.getDeclaredField(fieldName);
//                    field.setAccessible(true);
//                    Class<?> type = field.getType();
//                    String fieldValue = (String)fieldValuee;
//                    if (type == String.class) {
//                        field.set(vMachine, fieldValue);
//                    }
//                    else if (type == int.class || type == Integer.class) {
//                        field.set(vMachine, Integer.parseInt(fieldValue));
//                    }
//                    else if (type == boolean.class || type == Boolean.class) {
//                        field.set(vMachine, Boolean.parseBoolean(fieldValue));
//                    }
//                    else if (type == long.class || type == Long.class) {
//                        field.set(vMachine, Long.parseLong(fieldValue));
//                    }
//                    else if (type == double.class || type == Double.class) {
//                        field.set(vMachine, Double.parseDouble(fieldValue));
//                    }
//                    else if (type == float.class || type == Float.class) {
//                        field.set(vMachine, Float.parseFloat(fieldValue));
//                    }
//                    else {
//                        field.set(vMachine, type.cast(fieldValue));
//                    }
//                } catch (NoSuchFieldException | IllegalAccessException e) {
//                    throw new RuntimeException("Error updating field: " + fieldName, e);
//                }
//            }
//            transaction.commit();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }

    public void add(VMachine vMachine) {
        vMachines.insertOne(vMachine);
    }

    public void remove(VMachine vMachine) {
        Bson filter = Filters.eq("_id", vMachine.getEntityId());
        VMachine deletedVMachine = vMachines.findOneAndDelete(filter);
    }

    public long size() {
//        try (Session session = sessionFactory.openSession()) {
//            Transaction transaction = session.beginTransaction();
//            Long count = (Long) session.createQuery("SELECT COUNT(vm) FROM VMachine vm").uniqueResult();
//            transaction.commit();
//            return count;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return 0;
    }

    public List<VMachine> getVMachines() {
        return vMachines.find().into(new ArrayList<>());
    }

    public VMachine getVMachineByID(long ID) {
//        try (Session session = sessionFactory.openSession()) {
//            Transaction transaction = session.beginTransaction();
//
//            VMachine vMachine = session.createQuery("FROM VMachine vm WHERE vm.vMachineID = :vMachineID", VMachine.class)
//                    .setParameter("vMachineID", ID)
//                    .uniqueResult();
//
//            transaction.commit();
//            return vMachine;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        return null;
    }
}

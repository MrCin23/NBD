package org.example.manager;

import org.example.DBConnection;
import org.example.dao.VMachineDao;
import org.example.mapper.*;
import org.example.model.AppleArch;
import org.example.model.VMachine;
import org.example.model.x86;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.UUID;

public class VMachineManager {
    private static VMachineManager instance;
    DBConnection db;
    VMachineDao vMachineDao;

    public static synchronized VMachineManager getInstance() {
        if (instance == null) {
            instance = new VMachineManager();
        }
        return instance;
    }

    private VMachineManager() {
        db = DBConnection.getInstance();
        db.initSession();
        db.createVMachineTable();
        VMachineMapper vMachineMapper = new VMachineMapperBuilder(db.getSession()).build();
        vMachineDao = vMachineMapper.vMachineDao();
    }

    public void registerAppleArch(int CPUNumber, String RAMSize) {
        VMachine vMachine = new AppleArch(CPUNumber, RAMSize);
        registerExistingVMachine(vMachine);
    }

    public void registerX86(int CPUNumber, String RAMSize, String CPUManufacturer) {
        VMachine vMachine = new x86(CPUManufacturer, CPUNumber, RAMSize);
        registerExistingVMachine(vMachine);
    }

    public void registerExistingVMachine(VMachine vMachine) {
        vMachineDao.create(vMachine);
    }

    public void unregisterVMachine(VMachine vMachine) {
        vMachineDao.delete(vMachine);
    }

    public VMachine getVMachine(UUID uuid) {
        return vMachineDao.findById(uuid);
    }

    public void update(UUID uuid, Map<String, Object> fieldsToUpdate) {
        VMachine vMachine = vMachineDao.findById(uuid);

        // Update the fields dynamically (use reflection)
        for (Map.Entry<String, Object> entry : fieldsToUpdate.entrySet()) {
            String fieldName = entry.getKey();
            Object fieldValue = entry.getValue();

            // Use reflection to set the field value on the entity
            try {
                Field field = VMachine.class.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(vMachine, fieldValue);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException("Error updating field: " + fieldName, e);
            }
        }

        vMachineDao.update(vMachine);
    }

    public void update(UUID uuid, String field, Object value) {
        VMachine vMachine = vMachineDao.findById(uuid);

        // Use reflection to set the field value on the entity
        try {
            Field fieldName = VMachine.class.getDeclaredField(field);
            fieldName.setAccessible(true);
            fieldName.set(vMachine, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Error updating field: " + field, e);
        }
        vMachineDao.update(vMachine);
    }
}

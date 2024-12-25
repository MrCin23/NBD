package org.example.repository;

import org.example.model.VMachine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VMachineRepository {
    List<VMachine> vMachines;


    public VMachineRepository() {
        vMachines = new ArrayList<VMachine>();
    }

    public void update(long id, Map<String, Object> fieldsToUpdate) {
        throw new RuntimeException("Not implemented yet");
    }

    public void add(VMachine vMachine) {
        throw new RuntimeException("Not implemented yet");
    }

    public void remove(VMachine vMachine) {
        throw new RuntimeException("Not implemented yet");
    }

    public long size() {
        throw new RuntimeException("Not implemented yet");
    }

    public List<VMachine> getVMachines() {
        throw new RuntimeException("Not implemented yet");
    }

    public VMachine getVMachineByID(long ID) {
        throw new RuntimeException("Not implemented yet");
    }
}

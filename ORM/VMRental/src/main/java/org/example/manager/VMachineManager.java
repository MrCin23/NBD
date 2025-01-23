package org.example.manager;


import org.example.model.AppleArch;
import org.example.model.MongoUUID;
import org.example.model.VMachine;
import org.example.repository.VMachineRepository;
import org.example.model.x86;

import java.util.Map;

//Manager jako Singleton
public final class VMachineManager {

    private static VMachineManager instance;
    private final VMachineRepository vMachinesRepository;

    public VMachineManager() {
        vMachinesRepository = new VMachineRepository();
    }

    public static VMachineManager getInstance() {
        if (instance == null) {
            instance = new VMachineManager();
        }
        return instance;
    }

    public void registerExistingVMachine(VMachine vm) {
        vMachinesRepository.add(vm);
    }

    public void registerX86(int CPUNumber, String ramSize, String CPUManufacturer) {
        VMachine vMachine = new x86(CPUNumber, ramSize, CPUManufacturer);
        registerExistingVMachine(vMachine);
    }

    public void registerAppleArch(int CPUNumber, String ramSize) {
        VMachine vMachine = new AppleArch(CPUNumber, ramSize);
        registerExistingVMachine(vMachine);
    }

    public void unregisterVMachine(VMachine vm) {
        vMachinesRepository.remove(vm);
    }

    public void update(MongoUUID uuid, Map<String, Object> fieldsToUpdate) {
        vMachinesRepository.update(uuid, fieldsToUpdate);
    }

    public void update(MongoUUID uuid, String field, Object value) {
        vMachinesRepository.update(uuid, field, value);
    }

    public String getAllVMachinesReport() {
        return this.vMachinesRepository.getVMachines().toString();
    }
    public VMachine getVMachine(MongoUUID uuid) {
        return (VMachine) vMachinesRepository.getVMachineByID(uuid);
    }

    public int getVMachinesAmount() {
        return vMachinesRepository.getVMachines().size();
    }
}

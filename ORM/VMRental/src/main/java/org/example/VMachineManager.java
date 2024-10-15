package org.example;



//Manager jako Singleton
public final class VMachineManager {

    private static VMachineManager instance;
    private Repository vMachinesRepository;

    public VMachineManager() {
        vMachinesRepository = new Repository();
    }

    public static VMachineManager getInstance() {
        if (instance == null) {
            instance = new VMachineManager();
        }
        return instance;
    }

    public void registerExsistingVMachine(VMachine vm) {
        vMachinesRepository.add(vm);
    }

    public void registerX86(long vMachineID, int CPUNumber, String ramSize, String CPUManufacturer) {
        VMachine vMachine = new x86(vMachineID, CPUNumber, ramSize, CPUManufacturer);
        registerExsistingVMachine(vMachine);
    }

    public void registerAppleArch(long vMachineID, int CPUNumber, String ramSize) {
        VMachine vMachine = new AppleArch(vMachineID, CPUNumber, ramSize);
        registerExsistingVMachine(vMachine);
    }

    public void unregisterVMachine(VMachine vm) {
        vMachinesRepository.remove(vm);
    }

//METHODS-----------------------------------
//TODO
    public String getAllVMachinesReport() {
        return this.vMachinesRepository.getElements().toString();
    }
    public VMachine getVMachine(long vMachineID) {
        return (VMachine) vMachinesRepository.getElementByID(vMachineID);
    }

    public int getElementsAmount() {
        return vMachinesRepository.getElements().size();
    }
}

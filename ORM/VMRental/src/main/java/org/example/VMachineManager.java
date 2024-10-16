package org.example;



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

//METHODS-----------------------------------
//TODO
    public String getAllVMachinesReport() {
        return this.vMachinesRepository.getVMachines().toString();
    }
    public VMachine getVMachine(long vMachineID) {
        return (VMachine) vMachinesRepository.getVMachineByID(vMachineID);
    }

    public int getVMachinesAmount() {
        return vMachinesRepository.getVMachines().size();
    }
}

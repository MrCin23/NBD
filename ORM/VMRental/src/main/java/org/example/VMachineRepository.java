package org.example;

import java.util.ArrayList;
import java.util.List;

public class VMachineRepository {
    List<VMachine> vMachines;
    private final String url = "jdbc:postgresql://localhost:5432/vmrental";
    private final String user = "postgres";
    private final String password = "password";

    public VMachineRepository() {
        vMachines = new ArrayList<VMachine>();
    }

    //-------------METHODS---------------------------------------
    //TODO dorobić metody z diagramu

    public void add(VMachine vMachine) {
//TODO
//        String classPath = element.getClass().getName();
//        String className = classPath.substring(classPath.lastIndexOf(".") + 1);
//        try (Connection connection = DriverManager.getConnection(url, user, password)) {
//            connection.setAutoCommit(true);
//            Statement statement = connection.createStatement();
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        vMachines.add(element);
    }

    public void remove(VMachine vMachine) {
        //zwracanie elementu usuniętego
        //TODO UPDATE jednak funkcja remove nie zwraca obiektu a jedynie boolean czy się powiodło xDDD
        vMachines.remove(vMachine);
    }

    public int size() {
        return vMachines.size();
    }

    public List<VMachine> getVMachines() {
        return vMachines;
    }

    public RepoElement getVMachineByID(long ID) {
        for (RepoElement element : vMachines) {
            if (element.getID() == ID) { //TODO to pewnie nie działa, wypada to jakoś naprawić (może interface)
                return element;
            }
        }
        return null;
    }
}

package org.example;

import org.hibernate.Session;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    List<RepoElement> elements;
    private final String url = "jdbc:postgresql://localhost:5432/vmrental";
    private final String user = "postgres";
    private final String password = "password";

    public Repository() {
        elements = new ArrayList<RepoElement>();
    }

    //-------------METHODS---------------------------------------
    //TODO dorobić metody z diagramu

    public void add(RepoElement element) {

        String classPath = element.getClass().getName();
        String className = classPath.substring(classPath.lastIndexOf(".") + 1);
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            connection.setAutoCommit(true);
            Statement statement = connection.createStatement();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        elements.add(element);
    }

//    public void add(RepoElement element) {
//        //Session session = .
//        String classPath = element.getClass().getName();
//        String className = classPath.substring(classPath.lastIndexOf(".") + 1); // Nazwa klasy jako nazwa tabeli
//
//        // Pobierz wszystkie pola klasy
//        Field[] fields = element.getClass().getDeclaredFields();
//
//        // Tworzenie dynamicznego zapytania INSERT
//        StringBuilder sql = new StringBuilder("INSERT INTO " + className + " (");
//
//        StringBuilder placeholders = new StringBuilder();
//        List<Object> values = new ArrayList<>();
//
//        for (Field field : fields) {
//            field.setAccessible(true);  // Umożliwia dostęp do prywatnych pól
//
//            // Dodaj nazwę pola do zapytania
//            sql.append(field.getName()).append(",");
//
//            // Dodaj odpowiedni placeholder dla wartości
//            placeholders.append("?,");
//
//            try {
//                // Pobierz wartość pola z obiektu
//                values.add(field.get(element));
//            } catch (IllegalAccessException e) {
//                throw new RuntimeException("Nie udało się odczytać wartości pola", e);
//            }
//        }
//
//        // Usuń ostatni przecinek z SQL i placeholders
//        sql.setLength(sql.length() - 1);
//        placeholders.setLength(placeholders.length() - 1);
//
//        // Zakończ zapytanie SQL
//        sql.append(") VALUES (").append(placeholders).append(")");
//
//        try (Connection connection = DriverManager.getConnection(url, user, password)) {
//            connection.setAutoCommit(true);
//
//            // Przygotowanie zapytania
//            PreparedStatement statement = connection.prepareStatement(sql.toString());
//
//            // Ustawianie wartości parametrów w zapytaniu
//            for (int i = 0; i < values.size(); i++) {
//                statement.setObject(i + 1, values.get(i));  // Ustaw wartości dynamicznie
//            }
//
//            // Wykonanie zapytania
//            statement.executeUpdate();
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        elements.add(element);  // Dodanie elementu do listy (jeśli chcesz go przechowywać lokalnie)
//    }

    public void remove(RepoElement element) {
        //zwracanie elementu usuniętego
        //TODO UPDATE jednak funkcja remove nie zwraca obiektu a jedynie boolean czy się powiodło xDDD
        elements.remove(element);
    }

    public int size() {
        return elements.size();
    }

    public List<RepoElement> getElements() {
        return elements;
    }

    public RepoElement getElementByID(long ID) {
        for (RepoElement element : elements) {
            if (element.getID() == ID) { //TODO to pewnie nie działa, wypada to jakoś naprawić (może interface)
                return element;
            }
        }
        return null;
    }


//-----------------------------------------------------------

}

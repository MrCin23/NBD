package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Repository {
    List<RepoElement> elements;

    public Repository() {
        elements = new ArrayList<RepoElement>();
    }

    //-------------METHODS---------------------------------------
    //TODO dorobić metody z diagramu

    public void add(RepoElement element) {
        elements.add(element);
    }
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

    public <T>T getElementByID(UUID ID) {
        for(int i = 0; i < elements.size(); i++) {
            if(elements.get(i).getID() == ID) { //TODO to pewnie nie działa, wypada to jakoś naprawić (może interface)
                return (T) elements.get(i);
            }
        }
        return null;
    }


//-----------------------------------------------------------

}

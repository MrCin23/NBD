package org.example;

import java.util.List;

public class Repository<T> {
    List<T> elements;

//-------------METHODS---------------------------------------
    //TODO dorobić metody z diagramu

    public void add(T element) {
        elements.add(element);
    }
    public void remove(T element) {
        //TODO zwracanie elementu usuniętego
        elements.remove(element);
    }

    public int size() {
        return elements.size();
    }

    public List<T> getElements() {
        return elements;
    }


//-----------------------------------------------------------

}

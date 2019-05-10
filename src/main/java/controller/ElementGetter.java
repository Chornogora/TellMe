package controller;

public interface ElementGetter {

    //returns an element by it's id
    String get(long id);

    //returns all elements
    String getAll();
}

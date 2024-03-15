package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

public class Curs<T> {
    private String denumire;
    private int capacitate;

    // numar actual de studenti
    private int nrStud;

    // studenti participanti
    private ArrayList<T> stud;

    public Curs(String denumire, int capacitate) {
        this.denumire = denumire;
        this.capacitate = capacitate;
        this.nrStud = 0;
        this.stud = new ArrayList<T>();
    }

    public String getDenumire() {
        return denumire;
    }

    public int getCapacitate() {
        return capacitate;
    }
    public String toString() {
        return denumire + " " + capacitate;
    }

    public int getNrStud() {
        return nrStud;
    }

    public ArrayList<T> getStud() {
        return stud;
    }

    public void setNrStud(int nrStud) {
        this.nrStud = nrStud;
    }
}

package org.example;

import java.util.ArrayList;

public class Student {
    private String nume;
    private double medie;
    public Student(String nume) {
        this.nume = nume;
        this.medie = 0;
    }

    public String getNume() {
        return nume;
    }

    public double getMedie() {
        return medie;
    }
    public void setMedie(double medie) {
        this.medie = medie;
    }
    public String toString() {
        return nume + " - " + medie;
    }

}

package org.example;

import java.util.ArrayList;

public class StudLicenta extends Student{
    // lista preferinte
    private ArrayList<Curs<StudLicenta>> pref;
    // cursul la care a fost admis
    private Curs<StudLicenta> curs;
    public StudLicenta(String nume) {
        super(nume);
        this.pref = new ArrayList<Curs<StudLicenta>>();
    }

    public ArrayList<Curs<StudLicenta>> getPref() {
        return pref;
    }

    public Curs<StudLicenta> getCursL() {
        return curs;
    }

    public void setCurs(Curs<StudLicenta> curs) {
        this.curs = curs;
    }
}

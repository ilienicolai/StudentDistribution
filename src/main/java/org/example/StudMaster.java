package org.example;

import java.util.ArrayList;

public class StudMaster extends Student{
    // lista preferinte
    private ArrayList<Curs<StudMaster>> pref;
    // cursul la care a fost admis
    private Curs<StudMaster> curs;
    public StudMaster(String nume) {
        super(nume);
        this.pref = new ArrayList<Curs<StudMaster>>();
    }

    public ArrayList<Curs<StudMaster>> getPref() {
        return pref;
    }

    public Curs<StudMaster> getCursM() {
        return curs;
    }

    public void setCurs(Curs<StudMaster> curs) {
        this.curs = curs;
    }
}

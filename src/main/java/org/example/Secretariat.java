package org.example;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.TreeSet;

public class Secretariat {
    // set cu numele studentilor
    private TreeSet<Student> numeStud;
    // set cu studentii ordonati dupa medie
    private TreeSet<Student> studenti;
    // cursurile de licenta
    private ArrayList<Curs<StudLicenta>> cursLic;
    // cursurile de master
    private ArrayList<Curs<StudMaster>> cursMas;

    public Secretariat() {
        numeStud = new TreeSet<>(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                // ordonare alfabetica
                return o1.getNume().compareTo(o2.getNume());
            }
        });
        studenti = new TreeSet<>(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                double diff = o1.getMedie() - o2.getMedie();
                // medii egale -> ordonare alfabetica
                if (diff == 0) {
                    return o1.getNume().compareTo(o2.getNume());
                } else { // ordonare dupa medii
                    if (diff < 0)
                        return 1;
                }
                return -1;
            }
        });
        cursLic = new ArrayList<>();
        cursMas = new ArrayList<>();
    }

    public TreeSet<Student> getNumeStud() {
        return numeStud;
    }

    public TreeSet<Student> getStudenti() {
        return studenti;
    }

    public ArrayList<Curs<StudLicenta>> getCursLic() {
        return cursLic;
    }

    public ArrayList<Curs<StudMaster>> getCursMas() {
        return cursMas;
    }

    public void adaugaStudent(String studii, String nume) throws ExceptieStudExistent {
        if (studii.equals("licenta")) {
            StudLicenta studLic = new StudLicenta(nume);
            if (this.numeStud.contains(studLic)) {
                throw new ExceptieStudExistent("Student duplicat: " + nume);
            }
            this.numeStud.add(studLic);
        } else {
            StudMaster studMas = new StudMaster(nume);
            if (this.numeStud.contains(studMas)) {
                throw new ExceptieStudExistent("Student duplicat: " + nume);
            }
            this.numeStud.add(studMas);
        }
    }
    // cautare student dupa nume
    public Student findStud(Student stud) {
        Student ceil = this.numeStud.ceiling(stud);
        Student floor = this.numeStud.floor(stud);
        return ceil == floor? ceil:null;
    }
    public void citesteNote(String notePath) {
        double medie;
        try {
            BufferedReader br = new BufferedReader(new FileReader(notePath));
            String line;
            while ((line = br.readLine()) != null) {
                String[] vals = line.split(" - ");
                medie = Double.parseDouble(vals[1]);
                Student stud = new Student(vals[0]);
                // cautare student
                Student student = findStud(stud);
                // setare medie
                student.setMedie(medie);
                // adaugare student in setul sortat dupa medie
                this.studenti.add(student);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void contestatie(String nume, String med) {
        double medie = Double.parseDouble(med);
        // cautare student
        Student student = findStud((new Student(nume)));
        // eliminare student din set
        this.studenti.remove(student);
        // setare medie student
        student.setMedie(medie);
        // readaugare student in set cu noua medie
        this.studenti.add(student);
    }

    public void adaugaCurs(String[] vals) {
        if (vals[1].equals("licenta")) {
            Curs<StudLicenta> cursl = new Curs<>(vals[2], Integer.parseInt(vals[3]));
            this.cursLic.add(cursl);
        } else {
            Curs<StudMaster> cursm = new Curs<>(vals[2], Integer.parseInt(vals[3]));
            this.cursMas.add(cursm);
        }
    }
    public Curs<StudLicenta> findCursLic(String denum) {
        for (Curs<StudLicenta> c : this.cursLic) {
            if (c.getDenumire().equals(denum)) {
                return c;
            }
        }
        return null;
    }

    public Curs<StudMaster> findCursMas(String denum) {
        for (Curs<StudMaster> c : this.cursMas) {
            if (c.getDenumire().equals(denum)) {
                return c;
            }
        }
        return null;
    }

    public void adaugaPreferinte(String[] vals) {
        Student stud = findStud((new Student(vals[1])));
        if (stud instanceof StudLicenta) {
            Curs<StudLicenta> l = null;
            for (int i = 2; i < vals.length; i++) {
                l = findCursLic(vals[i]);
                ((StudLicenta) stud).getPref().add(l);
            }
        } else {
            Curs<StudMaster> m = null;
            for (int i = 2; i < vals.length; i++) {
                m = findCursMas(vals[i]);
                ((StudMaster) stud).getPref().add(m);
            }
        }

    }
    public void repartStudLic(Student stud) {
        // parcurgere lista de preferinte
        for (Curs<StudLicenta> l : ((StudLicenta) stud).getPref()) {
            // adaugare student daca mai este loc
            if (l.getNrStud() < l.getCapacitate())  {
                l.getStud().add((StudLicenta) stud);
                ((StudLicenta) stud).setCurs(l);
                l.setNrStud(l.getNrStud() + 1);
                break;
            } else { // situatie student cu ultima medie egala
                if (stud.getMedie() == l.getStud().get(l.getStud().toArray().length -1).getMedie()) {
                    l.getStud().add((StudLicenta) stud);
                    ((StudLicenta) stud).setCurs(l);
                    break;
                }
            }
        }
    }
    public void repartStudMas(Student stud) {
        // parcurgere lista de preferinte
        for (Curs<StudMaster> m : ((StudMaster) stud).getPref()) {
            // adaugare student daca mai este loc
            if (m.getNrStud() < m.getCapacitate())  {
                m.getStud().add((StudMaster) stud);
                ((StudMaster) stud).setCurs(m);
                m.setNrStud(m.getNrStud() + 1);
                break;
            } else { // situatie student cu ultima medie egala
                if (stud.getMedie() == m.getStud().get(m.getStud().toArray().length -1).getMedie()) {
                    m.getStud().add((StudMaster) stud);
                    ((StudMaster) stud).setCurs(m);
                    break;
                }
            }
        }
    }
    // repartizare studenti
    public void repartizeaza() {
        for (Student stud : this.studenti) {
            if (stud instanceof StudLicenta) {
                repartStudLic(stud);
            } else {
                repartStudMas(stud);
            }
        }
    }
    //sortare studenti de la fiecare curs alfabetic
    public void sortStudCurs() {
        Comparator<StudLicenta> compLic = new Comparator<StudLicenta>() {
            @Override
            public int compare(StudLicenta o1, StudLicenta o2) {
                return o1.getNume().compareTo(o2.getNume());
            }
        };
        Comparator<StudMaster> compMas = new Comparator<StudMaster>() {
            @Override
            public int compare(StudMaster o1, StudMaster o2) {
                return o1.getNume().compareTo(o2.getNume());
            }
        };
        for (Curs<StudLicenta> c : this.cursLic) {
            c.getStud().sort(compLic);
        }
        for (Curs<StudMaster> c : this.cursMas) {
            c.getStud().sort(compMas);
        }
    }
}

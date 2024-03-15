package org.example;

import java.io.*;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        // cale catre directorul testului
        String path = "src/main/resources/" + args[0];
        // cale catre fisierul cu comenzi
        String pathin = path + "/" + args[0] + ".in";
        // cale catre fisierul de afisare
        String pathout = path + "/" + args[0] + ".out";
        Secretariat s = new Secretariat();
        try {
            // deschidere buffer de citire din fisier
            BufferedReader br = new BufferedReader(new FileReader(pathin));
            // deschidere buffer de scriere in fisier
            FileWriter fw = new FileWriter(pathout, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw);
            String line;
            // citire comenzi si executarea lor
            while ((line = br.readLine()) != null) {
                String[] vals = line.split(" - ");
                // adaugare student
                if (vals[0].equals("adauga_student")) {
                    try {
                        s.adaugaStudent(vals[1], vals[2]);
                    } catch (ExceptieStudExistent e) {
                        out.println("***");
                        out.println(e.getMessage());
                    }
                    continue;
                }
                // citirea mediilor
                if (vals[0].equals("citeste_mediile")) {
                    int i = 1; // index fisier note
                    // cale catre fisierul cu note
                    String note_path = path + "/" + "note_" + i + ".txt";
                    // parcurgere fisiere cu note cat timp acestea exista
                    while ((new File(note_path)).exists()) {
                        s.citesteNote(note_path);
                        i++;
                        note_path = path + "/" + "note_" + i + ".txt";
                    }
                    continue;
                }
                // afisare medii
                if (vals[0].equals("posteaza_mediile")) {
                    Iterator<Student> studs = s.getStudenti().iterator();
                    out.println("***");
                    while (studs.hasNext()) {
                        out.println(studs.next());
                    }
                    continue;
                }
                // rezolvare contestatie
                if (vals[0].equals("contestatie")) {
                    s.contestatie(vals[1], vals[2]);
                    continue;
                }
                // adaugare curs
                if (vals[0].equals("adauga_curs")) {
                    s.adaugaCurs(vals);
                    continue;
                }
                // afisare detalii curs
                if (vals[0].equals("posteaza_curs")) {
                    out.println("***");
                    // cauatare curs in lista cursuri de licenta
                    Curs<StudLicenta> c = s.findCursLic(vals[1]);
                    if (c != null) {
                        out.print(c.getDenumire() + " (" + c.getCapacitate() + ")\n");
                        // afisare studenti participanti
                        for (StudLicenta stud : c.getStud()) {
                            out.println(stud.toString());
                        }
                    } else { // cautare curs in lista cursuri master
                        Curs<StudMaster> m = s.findCursMas(vals[1]);
                        out.print(m.getDenumire() + " (" + m.getCapacitate() + ")\n");
                        // afisare studenti participanti
                        for (StudMaster stud : m.getStud()) {
                            out.println(stud.toString());
                        }
                    }
                    continue;
                }
                // afisare detalii despre student
                if (vals[0].equals("posteaza_student")) {
                    out.println("***");
                    // cautare student dupa nume
                    Student student = s.findStud(new Student(vals[1]));
                    // afisare detalii
                    if (student instanceof StudLicenta)
                        out.println("Student Licenta: " + student.getNume() + " - " + student.getMedie() + " - " + ((StudLicenta)student).getCursL().getDenumire());
                    else out.println("Student Master: " + student.getNume() + " - " + student.getMedie() + " - " + ((StudMaster)student).getCursM().getDenumire());
                    continue;
                }
                // adaugare prefarinte
                if (vals[0].equals("adauga_preferinte")) {
                    s.adaugaPreferinte(vals);
                    continue;
                }
                // repartizare
                if (vals[0].equals("repartizeaza")) {
                    s.repartizeaza();
                    // sortare lista studenti de la fiecare curs
                    s.sortStudCurs();
                }
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package ro.ulbs.proiectaresoftware.students;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Application {

    public static void salveazaInFisier(String numeFisier, Collection<? extends Student> colectie) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(numeFisier))) {
            for (Student s : colectie) {
                writer.println(s.toString());
            }
            System.out.println("Salvare reusita! Colectia a fost scrisa in fisierul: " + numeFisier);
        } catch (IOException e) {
            System.out.println("A aparut o eroare la scrierea in fisierul " + numeFisier + ": " + e.getMessage());
        }
    }

    public static float gasesteNota(String prenume, String nume, Map<Integer, Student> tineri) {
        HashMap<String, Student> mapCautare = new HashMap<>();
        for (Student s : tineri.values()) {
            String cheie = s.getPrenume() + "-" + s.getNume();
            mapCautare.put(cheie, s);
        }

        String cheieCautata = prenume + "-" + nume;
        Student studentGasit = mapCautare.get(cheieCautata);

        if (studentGasit != null) {
            return (float) studentGasit.getNota();
        }
        return 0.0f;
    }

    // --- METODA NOUA PENTRU 7.6.3 b) ---
    public static List<Student> imparteInDouaFormatii(List<Student> studenti) {
        List<Student> listaModificata = new ArrayList<>();
        int dimensiune = studenti.size();
        int mijloc = (dimensiune + 1) / 2; // Pentru numar impar, prima jumatate primeste 1 in plus

        for (int i = 0; i < dimensiune; i++) {
            Student s = studenti.get(i);
            String formatieNoua = (i < mijloc) ? "Formatia_A" : "Formatia_B";

            // Apelam metoda noastra imutabila care returneaza un nou student
            listaModificata.add(s.mutaInFormatie(formatieNoua));
        }
        return listaModificata;
    }

    public static void main(String[] args) {
        HashMap<Integer, Student> studentiMap = new HashMap<>();

        try {
            File fisierStudenti = new File("studenti.txt");
            Scanner scanner = new Scanner(fisierStudenti);

            while (scanner.hasNextLine()) {
                String linie = scanner.nextLine();
                String[] date = linie.split(",");

                int matricol = Integer.parseInt(date[0].trim());
                String prenume = date[1].trim();
                String nume = date[2].trim();
                String formatie = date[3].trim();

                Student s = new Student(matricol, prenume, nume, formatie);
                studentiMap.put(matricol, s);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Fisierul cu studenti.txt nu a fost gasit!");
        }

        // Citim notele din note_anon.txt si actualizam map-ul
        try {
            File fisierNote = new File("note_anon.txt");
            Scanner scannerNote = new Scanner(fisierNote);

            while (scannerNote.hasNextLine()) {
                String linie = scannerNote.nextLine();
                String[] date = linie.split(",");

                int matricol = Integer.parseInt(date[0].trim());
                double nota = Double.parseDouble(date[1].trim());

                Student s = studentiMap.get(matricol);
                if (s != null) {
                    // ATENTIE AICI: Deoarece Student e imutabil, nu mai putem face s.setNota().
                    // Trebuie sa cream un student nou cu nota actualizata si sa il punem inapoi in Map.
                    Student studentActualizat = s.actualizeazaNota(nota);
                    studentiMap.put(matricol, studentActualizat);
                }
            }
            scannerNote.close();
        } catch (FileNotFoundException e) {
            System.out.println("Fisierul note_anon.txt nu a fost gasit!");
        }

        System.out.println("Catalog Studenti:");
        for (Map.Entry<Integer, Student> entry : studentiMap.entrySet()) {
            System.out.println(entry.getValue());
        }

        System.out.println("\n--- Sectiune Bursieri ---");

        List<StudentBursieri> bursieri = new ArrayList<>();
        bursieri.add(new StudentBursieri(1025, "Andrei", "Popa", "ISM141/2", 8.70, 725.50));
        bursieri.add(new StudentBursieri(1024, "Ioan", "Mihalcea", "ISM141/1", 9.80, 801.10));
        bursieri.add(new StudentBursieri(1026, "Anamaria", "Prodan", "TI131/1", 8.90, 745.50));
        bursieri.add(new StudentBursieri(1029, "Bianca", "Popescu", "TI131/1", 9.10, 780.80));

        salveazaInFisier("bursieri_out.txt", bursieri);

        System.out.println("\n--- Sectiune 3.5.2 (Citire din studenti_in.txt) ---");

        List<Student> listaStudentiNou = new ArrayList<>();

        try {
            File fisierIn = new File("studenti_in.txt");
            Scanner scannerIn = new Scanner(fisierIn);

            System.out.println("Studentii cititi sunt:");
            while (scannerIn.hasNextLine()) {
                String linie = scannerIn.nextLine();
                if (linie.trim().isEmpty()) {
                    continue;
                }

                String[] date = linie.split(",");
                int matricol = Integer.parseInt(date[0].trim());
                String prenume = date[1].trim();
                String nume = date[2].trim();
                String formatie = date[3].trim();

                Student s = new Student(matricol, prenume, nume, formatie);
                listaStudentiNou.add(s);

                System.out.println(s);
            }
            scannerIn.close();
        } catch (FileNotFoundException e) {
            System.out.println("Fisierul studenti_in.txt nu a fost gasit!");
        }

        listaStudentiNou.sort(Comparator.comparing(Student::getNume));
        salveazaInFisier("studenti_out.txt", listaStudentiNou);

        System.out.println("\n--- Sectiune 3.5.3 (Sortare Multipla) ---");
        listaStudentiNou.sort(
                Comparator.comparing(Student::getFormatiedestudiu)
                        .thenComparing(Student::getNume)
        );
        salveazaInFisier("studenti_out_sorted.txt", listaStudentiNou);

        System.out.println("\n--- Sectiune 4.5.3 (Cautare O(1)) ---");
        float notaM = gasesteNota("Bianca", "Popescu", studentiMap);
        float notaN = gasesteNota("Ioan", "Popa", studentiMap);
        System.out.println("notaM (Bianca Popescu) = " + notaM);
        System.out.println("notaN (Ioan Popa) = " + notaN);

        // --- TEMA CURENTA: 7.6.3 ---
        System.out.println("\n--- Sectiune 7.6.3 (Impartire in 2 formatii de studiu) ---");

        List<Student> studentiImpartiti = imparteInDouaFormatii(listaStudentiNou);

        System.out.println("Lista NOUA dupa mutarea studentilor in noile formatii (Formatiile vechi au ramas nemodificate):");
        for (Student s : studentiImpartiti) {
            System.out.println(s);
        }
    }
}
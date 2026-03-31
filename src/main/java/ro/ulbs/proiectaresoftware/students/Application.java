package ro.ulbs.proiectaresoftware.students;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Application {

    // --- METODA NOUA ADAUGATA: Metoda statica pentru salvarea colectiei in fisier ---
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

    public static void main(String[] args) {
        // Definim HashMap-ul: Cheia e numarul matricol, Valoarea e Studentul
        HashMap<Integer, Student> studentiMap = new HashMap<>();

        try {
            File fisierStudenti = new File("studenti.txt");
            Scanner scanner = new Scanner(fisierStudenti);

            while (scanner.hasNextLine()) {
                String linie = scanner.nextLine();
                // Separam linia dupa virgula
                String[] date = linie.split(",");

                int matricol = Integer.parseInt(date[0].trim());
                String prenume = date[1].trim();
                String nume = date[2].trim();
                String formatie = date[3].trim();

                Student s = new Student(matricol, prenume, nume, formatie);
                // Adaugam in Map
                studentiMap.put(matricol, s);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Fisierul cu studenti.txt nu a fost gasit!");
        }

        // 2. Citim notele din note_anon.txt si actualizam in O(1)
        try {
            File fisierNote = new File("note_anon.txt");
            Scanner scannerNote = new Scanner(fisierNote);

            while (scannerNote.hasNextLine()) {
                String linie = scannerNote.nextLine();
                String[] date = linie.split(",");

                int matricol = Integer.parseInt(date[0].trim());
                double nota = Double.parseDouble(date[1].trim());

                // Punctul cheie: Cautam studentul in O(1) folosind cheia si ii setam nota
                Student s = studentiMap.get(matricol);
                if (s != null) {
                    s.setNota(nota);
                }
            }
            scannerNote.close();
        } catch (FileNotFoundException e) {
            System.out.println("Fisierul note_anon.txt nu a fost gasit!");
        }

        // 3. Parcurgem Map-ul si afisam elementele
        System.out.println("Catalog Studenti:");
        for (Map.Entry<Integer, Student> entry : studentiMap.entrySet()) {
            System.out.println(entry.getValue());
        }

        System.out.println("\n--- Sectiune Bursieri ---");

        // Initializarea colectiei (List) de bursieri
        List<StudentBursieri> bursieri = new ArrayList<>();
        bursieri.add(new StudentBursieri(1025, "Andrei", "Popa", "ISM141/2", 8.70, 725.50));
        bursieri.add(new StudentBursieri(1024, "Ioan", "Mihalcea", "ISM141/1", 9.80, 801.10));
        bursieri.add(new StudentBursieri(1026, "Anamaria", "Prodan", "TI131/1", 8.90, 745.50));
        bursieri.add(new StudentBursieri(1029, "Bianca", "Popescu", "TI131/1", 9.10, 780.80));

        salveazaInFisier("bursieri_out.txt", bursieri);
    }
}
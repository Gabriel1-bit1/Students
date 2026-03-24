package ro.ulbs.proiectaresoftware.students;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        // Definim HashMap-ul: Cheia e numarul matricol, Valoarea e Studentul
        HashMap<Integer, Student> studentiMap = new HashMap<>();

        // 1. Citim studentii (presupunem ca fisierul se numeste studenti.txt.txt)
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

        // 2. Citim notele din note_anon.txt.txt si actualizam in O(1)
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
            System.out.println("Fisierul note_anon.txt.txt nu a fost gasit!");
        }

        // 3. Parcurgem Map-ul si afisam elementele
        System.out.println("Catalog Studenti:");
        for (Map.Entry<Integer, Student> entry : studentiMap.entrySet()) {
            System.out.println(entry.getValue());
        }
    }
}
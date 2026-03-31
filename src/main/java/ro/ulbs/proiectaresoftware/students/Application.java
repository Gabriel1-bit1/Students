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

    // --- METODA NOUA ADAUGATA PENTRU LAB4
    public static float gasesteNota(String prenume, String nume, Map<Integer, Student> tineri) {
        // Construim un HashMap temporar cu cheia "prenume-nume" pentru cautare O(1) conform indicatiilor
        HashMap<String, Student> mapCautare = new HashMap<>();
        for (Student s : tineri.values()) {
            String cheie = s.getPrenume() + "-" + s.getNume();
            mapCautare.put(cheie, s);
        }

        // Cautam studentul folosind noua cheie
        String cheieCautata = prenume + "-" + nume;
        Student studentGasit = mapCautare.get(cheieCautata);

        // Returnam nota daca e gasit, altfel 0.0
        if (studentGasit != null) {
            return (float) studentGasit.getNota(); // convertim la float conform cerintei
        }
        return 0.0f;
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

        System.out.println("\n--- Sectiune 3.5.2 (Citire din studenti_in.txt) ---");

        List<Student> listaStudentiNou = new ArrayList<>();

        try {
            File fisierIn = new File("studenti_in.txt");
            Scanner scannerIn = new Scanner(fisierIn);

            System.out.println("Studentii cititi sunt:");
            while (scannerIn.hasNextLine()) {
                String linie = scannerIn.nextLine();
                // Ignoram eventualele randuri goale de la finalul fisierului
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

                // Afisam studentii cititi in consola
                System.out.println(s);
            }
            scannerIn.close();
        } catch (FileNotFoundException e) {
            System.out.println("Fisierul studenti_in.txt nu a fost gasit!");
        }

        // Sortam dupa numele de familie (nume)
        listaStudentiNou.sort(Comparator.comparing(Student::getNume));

        // Salvam lista sortata in fisier
        salveazaInFisier("studenti_out.txt", listaStudentiNou);

        // Tema lab 3
        System.out.println("\n--- Sectiune 3.5.3 (Sortare Multipla) ---");

        // Sortam lista mai intai dupa "formatie de studiu", apoi dupa "nume"
        listaStudentiNou.sort(
                Comparator.comparing(Student::getFormatiedestudiu)
                        .thenComparing(Student::getNume)
        );

        // Salvam lista dublu-sortata in noul fisier
        salveazaInFisier("studenti_out_sorted.txt", listaStudentiNou);

        // Tema Lab 4
        System.out.println("\n--- Sectiune 4.5.3 (Cautare O(1)) ---");

        // Map-ul tau principal se numeste "studentiMap" in cod (in loc de "tineri" cum zice in poza)
        float notaM = gasesteNota("Bianca", "Popescu", studentiMap);
        float notaN = gasesteNota("Ioan", "Popa", studentiMap);

        System.out.println("notaM (Bianca Popescu) = " + notaM);
        System.out.println("notaN (Ioan Popa) = " + notaN);
    }
}
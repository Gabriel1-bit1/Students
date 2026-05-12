package ro.ulbs.proiectaresoftware.students;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AplicatieCuBursa {

    public static void main(String[] args) {
        AplicatieCuBursa instanta = new AplicatieCuBursa();
        List<StudentBursieri> lista = instanta.genereaza();

        System.out.println("Lista initiala:");
        for (StudentBursieri student : lista) {
            System.out.println(student);
        }

        System.out.println("Lista sortata (Formatie -> Nume -> Prenume -> Nota -> Bursa):");
        List<StudentBursieri> sortata = instanta.sorteaza(lista);
        for (StudentBursieri student : sortata) {
            System.out.println(student);
        }
    }

    public List<StudentBursieri> genereaza() {
        List<StudentBursieri> lista = new ArrayList<>();
        // Constructorul tau: numarMatricol, prenume, nume, formatie, nota, cuantumBursa
        lista.add(new StudentBursieri(1025, "Andrei", "Popa", "ISM141/2", 8.70, 725.50));
        lista.add(new StudentBursieri(1024, "Ioan", "Mihalcea", "ISM141/1", 9.80, 801.10));
        lista.add(new StudentBursieri(1029, "Bianca", "Popescu", "TI131/1", 9.10, 780.80));
        lista.add(new StudentBursieri(1026, "Anamaria", "Prodan", "TI131/1", 8.90, 745.50));
        lista.add(new StudentBursieri(1029, "Bianca", "Popescu", "TI131/1", 9.10, 100.00));
        return lista;
    }

    public List<StudentBursieri> sorteaza(List<StudentBursieri> lst) {
        List<StudentBursieri> listaSortata = new ArrayList<>(lst);

        // Sortarea pe multiple criterii conform cerintei din laborator:
        // 1. Formatia de studiu
        // 2. Numele
        // 3. Prenumele
        // 4. Nota
        // 5. Cuantumul bursei
        listaSortata.sort(Comparator
                .comparing(StudentBursieri::getFormatiedestudiu)
                .thenComparing(StudentBursieri::getNume)
                .thenComparing(StudentBursieri::getPrenume)
                .thenComparing(StudentBursieri::getNota)
                .thenComparing(StudentBursieri::getCuantumBursa));

        return listaSortata;
    }
}
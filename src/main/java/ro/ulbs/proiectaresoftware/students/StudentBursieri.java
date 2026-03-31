package ro.ulbs.proiectaresoftware.students;

import java.util.Objects;

public class StudentBursieri extends Student {
    private double cuantumBursa;

    // Constructor cu toti parametrii, incluzandu-i pe cei mosteniti
    public StudentBursieri(int numarMatricol, String prenume, String nume, String formatiedestudiu, double nota, double cuantumBursa) {
        super(numarMatricol, prenume, nume, formatiedestudiu);
        this.setNota(nota); // Folosim setter-ul din clasa parinte pentru nota
        this.cuantumBursa = cuantumBursa;
    }

    // Implementare equals folosind si implementarea din clasa parinte
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false; // Verifica numarul matricol din parinte
        StudentBursieri that = (StudentBursieri) o;
        return Double.compare(that.cuantumBursa, cuantumBursa) == 0;
    }

    // Implementare hashCode
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cuantumBursa);
    }

    // Implementare toString folosind metoda din parinte
    @Override
    public String toString() {
        return super.toString() + String.format(" | Bursa: %7.2f", cuantumBursa);
    }
}
package ro.ulbs.proiectaresoftware.students;

import java.util.Objects;

public class Student {
    public int numarMatricol;
    public String prenume;
    public String nume;
    public String formatiedestudiu;
    private double nota; // Am adaugat campul nota

    public Student(int numarMatricol, String prenume, String nume, String formatiedestudiu) {
        this.numarMatricol = numarMatricol;
        this.prenume = prenume;
        this.nume = nume;
        this.formatiedestudiu = formatiedestudiu;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public int getNumarMatricol() {
        return numarMatricol;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getNume() {
        return nume;
    }

    public String getFormatiedestudiu() {
        return formatiedestudiu;
    }

    // equals si hashCode folosesc doar numarMatricol pentru comparatie
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return numarMatricol == student.numarMatricol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numarMatricol);
    }

    // toString actualizat pentru a include si nota
    @Override
    public String toString() {
        return String.format("%6d | %20s | %10s | Nota: %5.2f", numarMatricol, prenume + " " + nume, formatiedestudiu, nota);
    }
}
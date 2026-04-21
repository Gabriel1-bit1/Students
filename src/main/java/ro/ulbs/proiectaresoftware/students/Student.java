package ro.ulbs.proiectaresoftware.students;

import java.util.Objects;

public class Student {
    // Toate atributele sunt private final pentru imutabilitate
    private final int numarMatricol;
    private final String prenume;
    private final String nume;
    private final String formatiedestudiu;
    private final double nota;

    // Constructorul inițial (fără notă, setează nota implicită la 0.0)
    public Student(int numarMatricol, String prenume, String nume, String formatiedestudiu) {
        this(numarMatricol, prenume, nume, formatiedestudiu, 0.0);
    }

    // Constructor complet utilizat pentru a crea noi instanțe (necesar pentru imutabilitate)
    public Student(int numarMatricol, String prenume, String nume, String formatiedestudiu, double nota) {
        this.numarMatricol = numarMatricol;
        this.prenume = prenume;
        this.nume = nume;
        this.formatiedestudiu = formatiedestudiu;
        this.nota = nota;
    }

    // --- METODE PENTRU SIMULAREA MODIFICĂRILOR (Returnează instanțe noi) ---

    // Metoda pentru actualizarea notei (înlocuiește vechiul setNota)
    public Student actualizeazaNota(double notaNoua) {
        return new Student(this.numarMatricol, this.prenume, this.nume, this.formatiedestudiu, notaNoua);
    }

    // 7.6.3 b) Funcția care mută un student într-o nouă formație
    public Student mutaInFormatie(String formatieNoua) {
        return new Student(this.numarMatricol, this.prenume, this.nume, formatieNoua, this.nota);
    }

    // --- GETTERI ---
    public double getNota() {
        return nota;
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

    @Override
    public String toString() {
        return String.format("%6d | %20s | %10s | Nota: %5.2f", numarMatricol, prenume + " " + nume, formatiedestudiu, nota);
    }
}
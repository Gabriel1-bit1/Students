package ro.ulbs.proiectaresoftware.students;

import java.util.Objects;

public class StudentBursieri extends Student {
    private final double cuantumBursa; // Si aici facem variabila finala pentru consecventa

    // Constructor cu toti parametrii
    public StudentBursieri(int numarMatricol, String prenume, String nume, String formatiedestudiu, double nota, double cuantumBursa) {
        // Apelam constructorul complet din parinte care primeste si nota
        super(numarMatricol, prenume, nume, formatiedestudiu, nota);
        this.cuantumBursa = cuantumBursa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StudentBursieri that = (StudentBursieri) o;
        return Double.compare(that.cuantumBursa, cuantumBursa) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cuantumBursa);
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Bursa: %7.2f", cuantumBursa);
    }
}
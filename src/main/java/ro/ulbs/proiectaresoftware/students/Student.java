package ro.ulbs.proiectaresoftware.students;

public class Student {
    public int numarMatricol;
    public String prenume;
    public String nume;
    public String formatiedestudiu;

    public Student(int numarMatricol, String prenume, String nume, String formatiedestudiu) {
        this.numarMatricol = numarMatricol;
        this.prenume = prenume;
        this.nume = nume;
        this.formatiedestudiu = formatiedestudiu;
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
    public String toString() {
        return String.format("%14d %20s %15s", numarMatricol, prenume + " " + nume, formatiedestudiu);
    }
}

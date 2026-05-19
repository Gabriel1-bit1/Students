package ro.ulbs.proiectaresoftware.students;

import java.util.List;

public interface StudentExporter {
    void export(String numeFisier, List<Student> studenti);
}
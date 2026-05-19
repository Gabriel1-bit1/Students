package ro.ulbs.proiectaresoftware.students;

import java.util.List;

public class ExcelStudentExporter implements StudentExporter {
    @Override
    public void export(String numeFisier, List<Student> studenti) {
        // Apelăm metoda originală
        Application.exportaInExcel(numeFisier, studenti);
    }
}
package ro.ulbs.proiectaresoftware.students;

import java.util.List;

public class TimeMeasuringExporter implements StudentExporter {
    private StudentExporter wrappedExporter;

    public TimeMeasuringExporter(StudentExporter wrappedExporter) {
        this.wrappedExporter = wrappedExporter;
    }

    @Override
    public void export(String numeFisier, List<Student> studenti) {
        // 1. Înregistrăm timpul de început
        long startTime = System.currentTimeMillis();

        // 2. Executăm exportul delegând sarcina către obiectul împachetat (wrapped)
        wrappedExporter.export(numeFisier, studenti);

        // 3. Înregistrăm timpul de final și calculăm durata
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        // 4. Afișăm în consolă timpul de execuție, conform cerinței
        System.out.println("Timpul de executie pentru export a fost de: " + duration + " ms.");
    }
}
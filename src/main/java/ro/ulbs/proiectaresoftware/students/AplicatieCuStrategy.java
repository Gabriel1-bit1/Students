package ro.ulbs.proiectaresoftware.students;

import java.io.*;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;


// Interfață pentru Export (a, b, c)
interface IStudentiExport {
    void doExport(List<Student> studenti);
}

// Interfață pentru Citire (d, e)
interface IStudentiImport {
    List<Student> doImport();
}

// --- CONTEXTUL --- [cite: 49, 114]
class Exporter {
    // Metodă care rulează strategia de export
    void startExport(IStudentiExport strategyInstance, List<Student> students) {
        strategyInstance.doExport(students);
    }

    // Metodă care rulează strategia de import
    List<Student> startImport(IStudentiImport strategyInstance) {
        return strategyInstance.doImport();
    }
}

// --- IMPLEMENTĂRI STRATEGII ---

// a) Strategy pentru afișare în consolă
class StudentiInConsola implements IStudentiExport {
    @Override
    public void doExport(List<Student> studenti) {
        System.out.println("\n--- [Strategy] Afișare în Consolă ---");
        studenti.forEach(System.out::println);
    }
}

// b) Strategy pentru export în fișier TXT
class StudentiInFisierText implements IStudentiExport {
    private String fileName;
    public StudentiInFisierText(String fileName) { this.fileName = fileName; }

    @Override
    public void doExport(List<Student> studenti) {
        Application.salveazaInFisier(fileName, studenti);
    }
}

// c) Strategy pentru export în fișier XLSX (folosind .xls din codul tău)
class StudentiInFisierXlsx implements IStudentiExport {
    private String fileName;
    public StudentiInFisierXlsx(String fileName) { this.fileName = fileName; }

    @Override
    public void doExport(List<Student> studenti) {
        Application.exportaInExcel(fileName, studenti);
    }
}

// d) Strategy pentru CITIRE din fișier TXT [cite: 98]
class StudentiDinFisierText implements IStudentiImport {
    private String fileName;
    public StudentiDinFisierText(String fileName) { this.fileName = fileName; }

    @Override
    public List<Student> doImport() {
        List<Student> cititi = new ArrayList<>();
        try (Scanner sc = new Scanner(new File(fileName))) {
            while (sc.hasNextLine()) {
                String[] d = sc.nextLine().split(",");
                if(d.length >= 4) {
                    cititi.add(new Student(Integer.parseInt(d[0].trim()), d[1].trim(), d[2].trim(), d[3].trim()));
                }
            }
            System.out.println("Import TXT reușit din: " + fileName);
        } catch (Exception e) { System.out.println("Eroare import TXT: " + e.getMessage()); }
        return cititi;
    }
}

// e) Strategy pentru CITIRE din fișier XLSX
class StudentiDinFisierXlsx implements IStudentiImport {
    private String fileName;
    public StudentiDinFisierXlsx(String fileName) { this.fileName = fileName; }

    @Override
    public List<Student> doImport() {
        return Application.citesteDinExcel(fileName);
    }
}

// --- CLASA PRINCIPALĂ CERUTĂ ---
public class AplicatieCuStrategy {
    public static void main(String[] args) {
        // Lista de studenți din laboratorul anterior
        List<Student> studenti = Arrays.asList(
                new Student(1025, "Andrei", "Popa", "ISM141/2", 8.70),
        new Student(1024, "Ioan", "Mihalcea", "ISM141/1", 10.0),
        new Student(1026, "Anamaria", "Prodan", "TI131/1", 8.90),
        new Student(1029, "Bianca", "Popescu", "TI131/1", 10.0),
        new Student(1029, "Maria", "Pana", "TI131/1", 4.10),
        new Student(1029, "Gabriela", "Mohanu", "TI131/2", 7.33),
        new Student(1029, "Marius", "Nasta", "TI131/2", 3.20),
        new Student(1029, "Marius", "Nasta", "TI131/1", 5.12),
        new Student(1029, "Andrei", "Dobrescu", "TI131/2", 2.22)
        );

        Exporter context = new Exporter();

        // a) Export Consolă
        context.startExport(new StudentiInConsola(), studenti);

        // b) Export TXT
        context.startExport(new StudentiInFisierText("studenti_lab10.txt"), studenti);

        // c) Export XLSX
        context.startExport(new StudentiInFisierXlsx("studenti_lab10.xls"), studenti);

        // d) Import TXT
        List<Student> dinTxt = context.startImport(new StudentiDinFisierText("studenti_lab10.txt"));
        System.out.println("Studenți citiți din TXT: " + dinTxt.size());

        // e) Import XLSX
        List<Student> dinXls = context.startImport(new StudentiDinFisierXlsx("studenti_lab10.xls"));
        System.out.println("Studenți citiți din XLS: " + dinXls.size());
    }
}
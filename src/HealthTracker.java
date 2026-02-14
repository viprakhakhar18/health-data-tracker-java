import java.io.*;
import java.util.ArrayList;

public class HealthTracker {
    private final ArrayList<HealthEntry> entries;

    public HealthTracker() {
        entries = new ArrayList<>();
    }

    public void addEntry(HealthEntry entry) {
        entries.add(entry);
    }

    public ArrayList<HealthEntry> getAllEntries() {
        return entries;
    }

    public ArrayList<HealthEntry> searchByDate(String date) {
        ArrayList<HealthEntry> result = new ArrayList<>();
        for (HealthEntry e : entries) {
            if (e.getDate().equals(date)) {
                result.add(e);
            }
        }
        return result;
    }

    public void loadFromFile(String filepath) {
        File file = new File(filepath);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                HealthEntry entry = HealthEntry.fromCSV(line);
                if (entry != null) {
                    entries.add(entry);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }

    public void saveToFile(String filepath) {
        File file = new File(filepath);

        // Ensure parent folder exists
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) parent.mkdirs();

        try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
            for (HealthEntry e : entries) {
                pw.println(e.toCSV());
            }
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }
}
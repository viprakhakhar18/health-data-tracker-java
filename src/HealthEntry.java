public class HealthEntry {
    private final String date;      // format: YYYY-MM-DD
    private final String symptoms;  // free text
    private final int mood;         // 1-5
    private final String notes;     // free text

    public HealthEntry(String date, String symptoms, int mood, String notes) {
        this.date = date;
        this.symptoms = symptoms;
        this.mood = mood;
        this.notes = notes;
    }

    public String getDate() {
        return date;
    }

    public String toCSV() {
        // Replace commas so we can safely store in CSV
        String safeSymptoms = symptoms.replace(",", " ");
        String safeNotes = notes.replace(",", " ");
        return date + "," + safeSymptoms + "," + mood + "," + safeNotes;
    }

    public static HealthEntry fromCSV(String line) {
        String[] parts = line.split(",", 4); // only split into 4 parts max
        if (parts.length < 4) return null;

        String date = parts[0].trim();
        String symptoms = parts[1].trim();
        int mood;
        try {
            mood = Integer.parseInt(parts[2].trim());
        } catch (NumberFormatException e) {
            return null;
        }
        String notes = parts[3].trim();
        return new HealthEntry(date, symptoms, mood, notes);
    }

    @Override
    public String toString() {
        return "Date: " + date +
               "\nSymptoms: " + symptoms +
               "\nMood (1-5): " + mood +
               "\nNotes: " + notes;
    }
}
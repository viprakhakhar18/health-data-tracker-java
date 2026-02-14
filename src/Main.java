import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final String DATA_FILE = "data/entries.csv";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HealthTracker tracker = new HealthTracker();

        // Load existing entries (if file exists)
        tracker.loadFromFile(DATA_FILE);

        System.out.println("=== Health Data Tracking System ===");

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt(sc, "Choose an option: ");

            switch (choice) {
                case 1:
                    addEntryFlow(sc, tracker);
                    break;
                case 2:
                    viewAllFlow(tracker);
                    break;
                case 3:
                    searchFlow(sc, tracker);
                    break;
                case 4:
                    tracker.saveToFile(DATA_FILE);
                    System.out.println("Saved to " + DATA_FILE);
                    break;
                case 5:
                    tracker.saveToFile(DATA_FILE);
                    System.out.println("Saved. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Please choose a valid option (1-5).");
            }
            System.out.println();
        }
        sc.close();
    }

    private static void printMenu() {
        System.out.println("1) Add Entry");
        System.out.println("2) View All Entries");
        System.out.println("3) Search By Date");
        System.out.println("4) Save");
        System.out.println("5) Exit");
    }

    private static void addEntryFlow(Scanner sc, HealthTracker tracker) {
        String date = readDate(sc, "Enter date (YYYY-MM-DD): ");
        System.out.print("Enter symptoms (short text): ");
        String symptoms = sc.nextLine().trim();
        int mood = readIntRange(sc, "Enter mood (1-5): ", 1, 5);
        System.out.print("Enter notes (optional): ");
        String notes = sc.nextLine().trim();

        HealthEntry entry = new HealthEntry(date, symptoms, mood, notes);
        tracker.addEntry(entry);
        System.out.println("Entry added.");
    }

    private static void viewAllFlow(HealthTracker tracker) {
        ArrayList<HealthEntry> entries = tracker.getAllEntries();
        if (entries.isEmpty()) {
            System.out.println("No entries yet.");
            return;
        }
        System.out.println("=== All Entries ===");
        for (int i = 0; i < entries.size(); i++) {
            System.out.println("\nEntry #" + (i + 1));
            System.out.println(entries.get(i));
        }
    }

    private static void searchFlow(Scanner sc, HealthTracker tracker) {
        String date = readDate(sc, "Search date (YYYY-MM-DD): ");
        ArrayList<HealthEntry> results = tracker.searchByDate(date);

        if (results.isEmpty()) {
            System.out.println("No entries found for " + date);
            return;
        }

        System.out.println("=== Results for " + date + " ===");
        for (HealthEntry e : results) {
            System.out.println("\n" + e);
        }
    }

    private static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    private static int readIntRange(Scanner sc, String prompt, int min, int max) {
        while (true) {
            int val = readInt(sc, prompt);
            if (val >= min && val <= max) return val;
            System.out.println("Please enter a number between " + min + " and " + max + ".");
        }
    }

    private static String readDate(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String date = sc.nextLine().trim();

            // Simple validation: YYYY-MM-DD length and dash positions
            if (date.length() == 10 && date.charAt(4) == '-' && date.charAt(7) == '-') {
                return date;
            }
            System.out.println("Please use format YYYY-MM-DD (example: 2026-02-14).");
        }
    }
}
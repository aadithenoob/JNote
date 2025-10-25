package org.example;

import picocli.*;
import picocli.CommandLine.*;

import java.io.File;
import java.util.Scanner;

@Command(name = "create",
        description = "Create note directly through arguments.",
        mixinStandardHelpOptions = true)

public class CreateCommand implements Runnable {
    @Option(names = {"-t", "--title"}, description = "Note title with extension (e.g., note.md).")
    private String noteTitle;

    @Option(names = {"-f", "--filepath"}, description = "Optional: Directory path to save the note.")
    private String noteFilepath;

    String defaultPath = Main.getDefaultStoragePath();

    @Override
    public void run() {
        try (Scanner sc = new Scanner(System.in)) {
            if (noteTitle == null || noteTitle.trim().isEmpty()) {
                System.out.println("Enter Note Title with Extension (e.g., note.md). Default: '.md' if none: ");
                noteTitle = sc.nextLine().trim();

                if (!noteTitle.contains(".")) {
                    noteTitle += ".md";
                }
            }

            if (noteFilepath == null || noteFilepath.trim().isEmpty()) {
                System.out.println("Enter Note Filepath excluding Note Name (use \\\\ for \\). (e.g., C:\\\\Users\\\\JohnSmith). Enter for Default (" + defaultPath + "): ");
                noteFilepath = sc.nextLine().trim();

                if (noteFilepath.isEmpty()) {
                    noteFilepath = defaultPath;
                }
            }

            System.out.println("Enter Note Content (type 'EOF' on a new line to finish): ");
            StringBuilder noteContentBuilder = new StringBuilder();
            String line;

            while (true) {
                line = sc.nextLine();
                if (line.equals("EOF")) {
                    break;
                }
                noteContentBuilder.append(line).append(System.lineSeparator());
            }

            String noteContent = noteContentBuilder.toString();

            String fullPath = noteFilepath + File.separator + noteTitle;

            Main.createNote(noteTitle, noteContent, fullPath);

        } catch (Exception e) {
            System.out.println("An unexpected error occurred during note creation: " + e.getMessage());
        }
    }
}
package org.example;

import java.util.Scanner;
import java.io.*;

public class Main {

    private static String getDefaultStoragePath() {
        String userHome = System.getProperty("user.home");
        String storagePath = userHome + File.separator + "JNote Storage";

        File storageDir = new File(storagePath);
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }

        return storagePath;
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            if (args[0].equals("-v") || args[0].equals("--version")) {
                String version = "JNote v0.1.0-alpha\nBuild: Pre-release";

                System.out.println(version);
                return;
            }
        }

        Scanner sc = new Scanner(System.in);

        System.out.println("To Create a Note, enter 'Note'.");
        String ans = sc.nextLine().trim();

        if (checkNote(ans)) {
            System.out.println("Enter Note Title with Extension (e.g., note.md). Default: '.md' if none: ");
            String noteTitle = sc.nextLine();

            if (!noteTitle.contains(".")) {
                noteTitle += ".md";
            }

            String defaultPath = getDefaultStoragePath();
            System.out.println("Enter Note Filepath excluding Note Name (use \\\\ for \\). (e.g., C:\\\\Users\\\\JohnSmith). Enter for Default (" + defaultPath + "): ");
            String noteFilepath = sc.nextLine().trim();

            if (noteFilepath.isEmpty()) {
                noteFilepath = defaultPath;
            }

            System.out.println("Enter Note Content (type 'END' on a new line to finish): ");
            StringBuilder noteContentBuilder = new StringBuilder();
            String line;

            while (true) {
                line = sc.nextLine();
                if (line.equals("END")) {
                    break;
                }
                noteContentBuilder.append(line).append(System.lineSeparator());
            }

            String noteContent = noteContentBuilder.toString();

            String fullPath = noteFilepath + File.separator + noteTitle;

            createNote(noteTitle, noteContent, fullPath);

        } else {
            System.out.println("Invalid Input. Exiting.");
        }

        sc.close();
    }

    public static boolean checkNote(String ans) {
        return ans.equalsIgnoreCase("Note");
    }

    public static void createNote(String title, String content, String filepath) {
        try {
            File folder = new File(filepath).getParentFile();
            if (!folder.exists()) {
                folder.mkdirs();
            }

            if (!checkIfNoteAlreadyExists(title, filepath)) {
                return;
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(filepath));
            bw.write(content);
            bw.close();

            Note newNote = new Note(title, content, filepath);
            MetadataManager.saveNoteMetadata(newNote);

            System.out.println("\n--- Note Created ---");
            System.out.println("Title: " + newNote.getTitle());
            System.out.println("Filepath: " + newNote.getFilepath());
            System.out.println("Timestamp: " + newNote.getTIMESTAMP());
            System.out.println("ID: " + newNote.getID());
            System.out.println("Content: \n" + newNote.getContent());

        } catch (IllegalArgumentException iae) {
            System.out.println("\nError: " + iae.getMessage());
        } catch (IOException ioe) {
            System.out.println("\nError writing file: " + ioe.getMessage());
        }
    }

    public static boolean checkIfNoteAlreadyExists(String title, String filepath) {
        File newNote = new File(filepath);

        if (newNote.exists()) {
            Scanner sc = new Scanner(System.in);

            System.out.println("\n" + title + " already exists, Overwrite? (Y/N): ");
            String ans = sc.nextLine().trim();

            if (!ans.equalsIgnoreCase("y")) {
                System.out.println("Cancelled. Note not saved.");
                return false;
            }
        }
        return true;
    }
}
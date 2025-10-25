package org.example;

import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.io.*;
import java.util.Scanner;

@Command(name = "jnote",
        mixinStandardHelpOptions = true,
        version = "JNote v0.2.0-alpha\nBuild: Pre-release",
        subcommands = {CreateCommand.class})
public class Main implements Runnable {

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        System.out.println("JNote: Simple command-line text-file creation utility.");
        System.out.println("Use 'jnote create' or 'jnote --help' for usage.");
    }

    public static String getDefaultStoragePath() {
        String userHome = System.getProperty("user.home");
        String storagePath = userHome + File.separator + "JNote Storage";

        File storageDir = new File(storagePath);

        if (!storageDir.exists()) {
            if (storageDir.mkdirs()) {
                System.out.println("Created storage directory: " + storagePath);
            } else {
                throw new RuntimeException("Failed to create storage directory: " + storagePath
                        + ". Check permissions or if a file with the same name exists.");
            }
        }

        if (!storageDir.isDirectory()) {
            throw new RuntimeException("Path exists but is not a directory: " + storagePath);
        }

        return storagePath;
    }

    public static void createNote(String title, String content, String filepath) {
        try {
            if (!checkOverwrite(title, filepath)) {
                return;
            }

            Note newNote = new Note(title, content, filepath);

            BufferedWriter bw = new BufferedWriter(new FileWriter(filepath));
            bw.write(content);
            bw.close();

            MetadataManager.saveNoteMetadata(newNote);

            System.out.println("\n--- Note Created ---");
            System.out.println("Title: " + title);
            System.out.println("Filepath: " + filepath);
            System.out.println("Timestamp: " + newNote.getTIMESTAMP());
            System.out.println("UUID: " + newNote.getID());

        } catch (IllegalArgumentException iae) {
            System.out.println("\nError: " + iae.getMessage());
        } catch (IOException ioe) {
            System.out.println("\nError writing file: " + ioe.getMessage());
        }
    }

    public static boolean checkOverwrite(String title, String filepath) {
        File newNote = new File(filepath);

        if (newNote.exists()) {
            Scanner sc = new Scanner(System.in);

            System.out.println("\n" + title + " already exists, Overwrite? (Y/N): ");
            String ans = sc.nextLine().trim();

            if (!ans.equalsIgnoreCase("y")) {
                System.out.println("Cancelled. Note not saved.");
                return false;
            }

            sc.close();
        }
        return true;
    }
}
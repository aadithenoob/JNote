package io.github.aadithenoob.jnote.commands;

import io.github.aadithenoob.jnote.utils.MetadataManager;
import io.github.aadithenoob.jnote.models.NoteMetadata;
import picocli.CommandLine.*;

import java.util.List;

@Command(name = "list",
        description = "List all saved notes",
        mixinStandardHelpOptions = true)
public class ListCommand implements Runnable {

    @Override
    public void run() {
        try {
            List<NoteMetadata> notes = MetadataManager.loadAllMetadata();

            if (notes.isEmpty()) {
                System.out.println("No notes found.");
                return;
            }

            System.out.println("\n=== All Notes (" + notes.size() + ") ===\n");

            int index = 1;
            for (NoteMetadata note : notes) {
                System.out.println("[" + index + "] " + note.getTitle());
                System.out.println("    Created: " + note.getTimestamp());
                System.out.println("    Path: " + note.getFilepath());
                System.out.println("    ID: " + note.getID());
                System.out.println();
                index++;
            }
        } catch (Exception e) {
            System.out.println("An unexpected error occurred during note creation: " + e.getMessage());
        }
    }
}

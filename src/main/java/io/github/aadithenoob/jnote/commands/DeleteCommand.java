package io.github.aadithenoob.jnote.commands;
import io.github.aadithenoob.jnote.models.NoteMetadata;
import io.github.aadithenoob.jnote.utils.MetadataManager;
import picocli.CommandLine.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Command(name = "delete",
        description = "Delete a note by title",
        mixinStandardHelpOptions = true)
public class DeleteCommand implements Runnable {
    @SuppressWarnings("unused")
    @Parameters(index = "0", description = "The title of the note to be deleted")
    private String title;

    @Override
    public void run() {
        try {
            List<NoteMetadata> notes = MetadataManager.loadAllMetadata();

            NoteMetadata toDelete = null;
            for (NoteMetadata note : notes) {
                if (note.getTitle().equals(title)) {
                    toDelete = note;
                    break;
                }
            }

            if (toDelete == null) {
                System.out.println("Note not found: " + title);
                return;
            }

            File file = new File(toDelete.getFilepath());
            if (file.exists()) {
                if (file.delete()) {
                    System.out.println("Note file deleted: " + title);
                } else {
                    System.out.println("Could not delete note file: " + title);
                }
            } else {
                System.out.println("Note file not found on disk, deleting metadata only: " + title);
            }

            notes.remove(toDelete);
            MetadataManager.saveAllMetadata(notes);

            System.out.println("\n--- Note Deleted ---");
            System.out.println("Title: " + toDelete.getTitle());
            System.out.println("ID: " + toDelete.getID());

        } catch (IOException e) {
            System.out.println("Error deleting note: " + e.getMessage());
        }
    }
}
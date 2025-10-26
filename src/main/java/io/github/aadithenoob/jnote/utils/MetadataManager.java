package io.github.aadithenoob.jnote.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import io.github.aadithenoob.jnote.Main;
import io.github.aadithenoob.jnote.models.Note;
import io.github.aadithenoob.jnote.models.NoteMetadata;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.*;
import java.util.*;

public class MetadataManager {
    private static final String METADATA_FILE = Main.getDefaultStoragePath() + File.separator + "notes-metadata.json";

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void saveNoteMetadata(Note note) throws IOException {
        List<NoteMetadata> notes = loadAllMetadata();

        NoteMetadata metadata = new NoteMetadata(
                note.getID().toString(),
                note.getTitle(),
                note.getFilepath(),
                note.getTIMESTAMP().toString()
        );

        notes.add(metadata);

        String json = gson.toJson(notes);

        Files.writeString(Paths.get(METADATA_FILE), json);
    }

    public static List<NoteMetadata> loadAllMetadata() throws IOException {
        File file = new File(METADATA_FILE);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        String json = Files.readString(Paths.get(METADATA_FILE));
        Type listType = new TypeToken<List<NoteMetadata>>(){}.getType();

        return gson.fromJson(json, listType);
    }

    public static void saveAllMetadata(List<NoteMetadata> notes) throws IOException {
        String json = gson.toJson(notes);
        Files.writeString(Paths.get(METADATA_FILE), json);
    }
}

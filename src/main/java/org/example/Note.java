package org.example;

import java.time.LocalDateTime;
import java.util.UUID;

public class Note {
    private final UUID ID;
    private String title;
    private String content;
    private String filepath;
    private final LocalDateTime TIMESTAMP;

    public Note(String title, String content, String filepath) {
        if (title == null || title.trim().isEmpty() || content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Title and content cannot be empty.");
        }

        this.title = title;
        this.content = content;
        this.filepath = filepath;

        this.ID = UUID.randomUUID();
        this.TIMESTAMP = LocalDateTime.now();
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public String getFilepath(){
        return this.filepath;
    }

    public UUID getID() {
        return this.ID;
    }

    public LocalDateTime getTIMESTAMP() {
        return this.TIMESTAMP;
    }
}
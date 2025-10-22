package org.example;

public class NoteMetadata {
    private final String id;
    private String title;
    private String filepath;
    private final String timestamp;

    public NoteMetadata(String ID, String title, String filepath, String timestamp) {
        this.title = title;
        this.filepath = filepath;
        this.id = ID;
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return this.title;
    }

    public String getFilepath(){
        return this.filepath;
    }

    public String getID() {
        return this.id;
    }

    public String getTimestamp() {
        return this.timestamp;
    }
}

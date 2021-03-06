package com.theschnucki.popularmoviesstage2.model;

public class Trailer {

    private static final String TAG = Trailer.class.getSimpleName();

    private String key;
    private String name;
    private String type;
    private String path;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() { return path; }

    public void setPath(String path) { this.path = path; }

}


package com.theschnucki.popularmoviesstage2.model;

public class Trailer {

    public static final String TAG = Trailer.class.getSimpleName();

    public String key;
    public String name;
    public String type;
    public String path;

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


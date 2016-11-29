package com.example.caitlin.cookhelper.database;

/**
 * Created by timothysmith on 2016-11-29.
 */

public class SearchResult {
    private String name;
    private long id;

    public SearchResult(String name, long id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public String toString() {
        String result = "";
        result += "Name: " + name + ", id: " + id;
        return result;
    }
}
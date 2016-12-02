package com.example.caitlin.cookhelper.database;

/**
 * Lightweight class for displaying search results from database without having
 * to load entire objects.
 */

public class SearchResult{
    private String name;
    private int rank;
    private long id;

    public SearchResult(String name, long id) {
        this.name = name;
        this.id = id;
    }

    public SearchResult(String name, int rank, long id) {
        this.name = name;
        this.rank = rank;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String toString() {
        String result = "";
        result += "Name: " + name + ", id: " + id;
        return result;
    }

}

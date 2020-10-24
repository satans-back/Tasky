package com.pomiecho.tasky.objects;

public class Task {

    public static final String TABLE_NAME = "tasks";
    public static final String ID = "ID";
    public static final String TITLE = "TITLE";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String STATE = "STATE";

    private enum State {TODO, INPROGRESS, DONE}

    private int id;
    private String title;
    private String description;
    private State state;

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
            + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + TITLE + " TEXT,"
            + DESCRIPTION + " TEXT,"
            + STATE + " INTEGER"
            + ")";

    public Task() {
        this.title = "Lorem ipsum";
        this.description = "Dolor sit amet";
        this.state = State.TODO;
    }

    public Task(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.state = State.TODO;
    }

    public int getId() { return this.id; }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public State getState() {return this.state; }

    public void setId(int id) { this.id = id; }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setState(State state) { this.state = state; }

}

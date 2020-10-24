package com.pomiecho.tasky.objects;

public class Task {
    private String title;
    private String description;
    private State state;

    private enum State {TODO, INPROGRESS, DONE}

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.state = State.TODO;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public State getState() {return this.state; }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setState(State state) { this.state = state; }

}

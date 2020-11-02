package com.pomiecho.tasky.interfaces;

import com.pomiecho.tasky.objects.Task;

public interface Communicator {
    public void createTask(Task task);
    public void setTaskToDo(Task task);
    public void setTaskInProgress(Task task);
    public void setTaskDone(Task task);
    public void deleteTask(Task task);
}

package com.example.wajahat.to_do.Data;

import java.util.Date;

public class message {

    private String from;
    private String to;
    private String task;
    private String deadline;


    public message(String from, String to, String task, String deadline){
        this.from=from;
        this.to=to;
        this.task=task;
        this.deadline=deadline;
    }
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}

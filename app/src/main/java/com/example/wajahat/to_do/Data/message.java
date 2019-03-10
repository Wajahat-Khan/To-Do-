package com.example.wajahat.to_do.Data;

import java.util.Date;

public class message {
    private int id;
    private String from;
    private String to;
    private String task;
    private Date deadline;

    public message(){

    }
    public message(int id, String from, String to, String task, Date deadline){
        this.id=id;
        this.from=from;
        this.to=to;
        this.task=task;
        this.deadline=deadline;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }
}

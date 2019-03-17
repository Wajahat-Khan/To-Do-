package com.example.wajahat.to_do.Data;

public class Depts {
    private String user;
    private String dept;

    public Depts(){}
    public Depts (String user, String d){
        this.user=user;
        this.dept=d;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
}

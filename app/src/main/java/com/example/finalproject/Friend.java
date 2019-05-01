package com.example.finalproject;

public class Friend {
    public String name;
    public int id;
    public int status; // 0 = pending, 1 = accepted, 2 = denied

    Friend(String name, int id){
        this.name = name;
        this.id = id;
        this.status = 1;
    }

    // Methods for friend
    public void setName(String name){
        this.name = name;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setStatus(int status){
        this.status = status;
    }

    public String getName(){
        return this.name;
    }

    public int getId(){
        return id;
    }

    public int getStatus(){
        return this.status;
    }

}


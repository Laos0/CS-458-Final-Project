package com.example.finalproject;


/*
    The purpose for this class is to store incoming data from the server side
    solely for friends that are under a user's account
 */
public class Friend {
    public String name;
    public int id;
    public int status; // 0 = pending, 1 = accepted, 2 = denied/rejected

    Friend(String name, int id){
        this.name = name;
        this.id = id;
        this.status = 1;
    }

    // Set the name for friend obj
    public void setName(String name){
        this.name = name;
    }

    // Set id for friend obj
    public void setId(int id){
        this.id = id;
    }

    // Set status for friend obj
    public void setStatus(int status){
        this.status = status;
    }

    // Get the friend obj's name
    public String getName(){
        return this.name;
    }

    // Get the friend obj's id
    public int getId(){
        return id;
    }

    // Get the friend obj's status
    public int getStatus(){
        return this.status;
    }

}


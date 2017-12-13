package com.example.ganoncannon.balance;

/**
 * Created by ganoncannon on 11/30/17.
 */

public class Controller {
    private Profile user;
    private int counter;

    public Controller() {
        user = new Profile(this);
        counter = 300; //5*60
    }

    public Profile getUser() {
        return user;
    }

    public void setUser(Profile user) {
        this.user = user;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }


}

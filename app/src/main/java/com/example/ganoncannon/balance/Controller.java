package com.example.ganoncannon.balance;

/**
 * Created by ganoncannon on 11/30/17.
 */

public class Controller {
    private Profile user;
    private int counter;
    private String state;
    private int chosenDif;
    private int chosenSpeed;
    private int chosenTime;

    public Controller() {
        user = new Profile(this);
        chosenTime = user.getGoals().get("time");
        chosenDif = user.getGoals().get("difficulty");
        chosenSpeed = user.getGoals().get("speed");
        counter = chosenTime;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getChosenDif() {
        return chosenDif;
    }

    public void setChosenDif(int chosenDif) {
        this.chosenDif = chosenDif;
    }

    public int getChosenSpeed() {
        return chosenSpeed;
    }

    public void setChosenSpeed(int chosenSpeed) {
        this.chosenSpeed = chosenSpeed;
    }

    public int getChosenTime() {
        return chosenTime;
    }

    public void setChosenTime(int chosenTime) {
        this.chosenTime = chosenTime;
    }
}

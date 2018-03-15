package com.ua.ganoncannon.balance;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by ganoncannon on 11/30/17.
 */

public class Data implements Serializable{
    private HashMap<String, ArrayList<HashMap<String, Integer>>> history;
    private HashMap<String, Integer> goals;
    private String context;

    public Data(HashMap<String, Integer> goals, String context) {
        this.context = context;
        // First try to load history, if fails, then create new history object
        if (!readData()) {
            history = new HashMap<String, ArrayList<HashMap<String, Integer>>>();
            ArrayList init_runs = new ArrayList<HashMap<String, Integer>>();
            history.put(Integer.toString(Calendar.WEEK_OF_YEAR), init_runs);
        }
        this.goals = goals;
    }

    public void writeData() {
        try {
            FileOutputStream fstream = new FileOutputStream(context + "history.ser");
            ObjectOutputStream ostream = new ObjectOutputStream(fstream);
            ostream.writeObject(history);
            ostream.close();
            fstream.close();
            System.out.println("write success");
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    public boolean readData() {
        try {
            FileInputStream istream = new FileInputStream(context + "history.ser");
            ObjectInputStream ostream = new ObjectInputStream(istream);
            history = (HashMap) ostream.readObject();
            ostream.close();
            istream.close();
            System.out.println("write success");
            return true;
        } catch (IOException err){
            return false;
        } catch (ClassNotFoundException cErr) {
            return false;
        }
    }

    public HashMap<String, ArrayList<HashMap<String, Integer>>> getHistory() {
        return history;
    }

    public void setHistory(HashMap history) {
        this.history = history;
    }

    /** Gets the run data for given week and compares to goals.  Overview of Data:
     *  {"Week 0" : {"speed" : int // speed from progress bar
                     "difficulty" : int // 1 for linear, 2 for random
                     "time" : int // seconds
                    }
        }
     * @param week
     * @return (int) progress
     */
    public int getOverallProgress(String week) {
        // Sum the weighted average of all the categories
        int speedScore = getProgressScore(week, "speed");
        int difScore = getProgressScore(week, "difficulty");
        int timeScore = getProgressScore(week, "time");
        int attemptScore = getProgressScore(week, "attempts");

        return (int) (speedScore + difScore + timeScore + attemptScore);
    }

    public int getNumAttempts(String week) {
        return history.get(week).size();
    }

    public double getAverageOf(String week, String of) {
        double sum = 0;

        if (history.get(week).size() > 0) {
            for (int i = 0; i < history.get(week).size(); i++) {
                sum += history.get(week).get(i).get(of);
            }
            return sum / (double)history.get(week).size();
        }
        else {
            return 0;
        }
    }

    public int getProgressScore(String week, String of) {
        ArrayList<HashMap<String, Integer>> attempts = history.get(week);
        double score = 0;
        if (of.equals("attempts")) {
            score = (double)attempts.size() / goals.get("attempts");
            return (int) ((score * Profile.RUN_WEIGHT) * 100);
        } else if (of.equals("speed")){
            score = getAverageOf(week, of) / goals.get("speed");
            return (int) ((score * Profile.SPEED_WEIGHT) * 100);
        } else if (of.equals("difficulty")) {
            score = getAverageOf(week, of) / goals.get("difficulty");
            return (int) ((score * Profile.DIF_WEIGHT) * 100);
        } else if (of.equals("time")) {
            score = getAverageOf(week, of) / goals.get("time");
            return (int) ((score * Profile.TIME_WEIGHT) * 100);
        } else {
            return 0;
        }
    }

    public void setGoals(HashMap h) {
        goals = h;
    }
}

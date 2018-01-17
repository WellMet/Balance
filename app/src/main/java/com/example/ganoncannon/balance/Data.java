package com.example.ganoncannon.balance;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ganoncannon on 11/30/17.
 */

public class Data implements Serializable{
    private HashMap<String, ArrayList<HashMap<String, Integer>>> history;
    private HashMap<String, Integer> goals;

    public Data(HashMap<String, Integer> goals) {
        // First try to load history, if fails, then create new history object
        if (!readData()) {
            history = new HashMap<String, ArrayList<HashMap<String, Integer>>>();
            ArrayList init_runs = new ArrayList<HashMap<String, Integer>>();
            history.put("Week 1", init_runs);
        }
        this.goals = goals;
    }

    public void writeData() {
        try {
            FileOutputStream fstream = new FileOutputStream("history.ser");
            ObjectOutputStream ostream = new ObjectOutputStream(fstream);
            ostream.writeObject(history);
            ostream.close();
            fstream.close();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    public boolean readData() {
        try {
            FileInputStream istream = new FileInputStream("history.ser");
            ObjectInputStream ostream = new ObjectInputStream(istream);
            history = (HashMap) ostream.readObject();
            ostream.close();
            istream.close();
            return true;
        } catch (IOException err){
            err.printStackTrace();
            return false;
        } catch (ClassNotFoundException cErr) {
            cErr.printStackTrace();
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
                     "goal_speed"
                     "goal_time"
                     "goal_runs"
                     "goal_dif"
                    }
        }
     * @param week
     * @return (int) progress
     */
    public int getOverallProgress(String week) {
        // Sum the weighted average of all the categories
        return (int) ((getProgressScore(week, "speed") * Profile.SPEED_WEIGHT)
                + (getProgressScore(week, "difficulty") * Profile.DIF_WEIGHT)
                + (getProgressScore(week, "time") * Profile.TIME_WEIGHT)
                + (getProgressScore(week, "attempts") * Profile.RUN_WEIGHT));
    }

    public int getNumAttempts(String week) {
        return history.get(week).size();
    }

    public double getAverageOf(String week, String of) {
        int sum = 0;
        for(int i = 0; i < history.get(week).size(); i++) {
            sum += history.get(week).get(i).get(of);
        }
        return sum / history.get(week).size();
    }

    public int getProgressScore(String week, String of) {
        ArrayList<HashMap<String, Integer>> attempts = history.get(week);
        double score = 0;
        if (of.equals("attempts")) {
            score = attempts.size() / goals.get("attempts");
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
}

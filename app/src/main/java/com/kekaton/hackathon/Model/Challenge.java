package com.kekaton.hackathon.Model;

/**
 * Created by User on 17.11.2016.
 */

public class Challenge {
    private int goal;
    private int progress;
    private String description;
    private int UserID;

    public Challenge(int goal, int progress, String description, int userID) {
        this.goal = goal;
        this.progress = progress;
        this.description = description;
        UserID = userID;
    }

    public int getGoal() {
        return goal;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUserID() {
        return UserID;
    }
}

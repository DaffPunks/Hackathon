package com.kekaton.hackathon.Model;

/**
 * Created by User on 17.11.2016.
 */

public class Challenge {
    private int goal;
    private int progress;
    private String description;
    private String f_name;
    private String l_name;
    private String profile_photo;
    private boolean isCompleted;

    public Challenge(int goal, int progress, String description, String f_name, String l_name, String profile_photo, boolean isCompleted) {
        this.goal = goal;
        this.progress = progress;
        this.description = description;
        this.f_name = f_name;
        this.l_name = l_name;
        this.profile_photo = profile_photo;
        this.isCompleted = isCompleted;

    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public String getFName() {
        return f_name;
    }

    public String getLName() {
        return l_name;
    }

    public String getProfilePhoto() {
        return profile_photo;
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
}

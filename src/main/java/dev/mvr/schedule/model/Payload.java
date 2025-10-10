package dev.mvr.schedule.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class Payload {
    @JsonProperty("action")
    private String action;

    @JsonProperty("university")
    private String university;

    @JsonProperty("group")
    private String group;

    @JsonProperty("date")
    private String date; // или LocalDate, но проще как String
    @JsonProperty("need_update")
    private boolean needUpdate;

    // Конструкторы
    public Payload() {}

    public Payload(String action, String group, LocalDate date,boolean needUpdate) {
        this.action = action;
        this.group = group;
        this.date = date.toString();
        this.needUpdate = needUpdate;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isNeedUpdate() {
        return needUpdate;
    }

    public void setNeedUpdate(boolean needUpdate) {
        this.needUpdate = needUpdate;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }
}
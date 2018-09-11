package org.launchcode.exercisemeetup.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.launchcode.exercisemeetup.Models.data.ActivityType;
import org.launchcode.exercisemeetup.Models.data.SkillLevel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.ManyToOne;

import javax.validation.constraints.NotNull;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

import java.time.LocalTime;


@Entity
public class Activity{

    private static final String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT
            = new SimpleDateFormat(DATE_FORMAT_PATTERN);

    private static final String TIME_FORMAT_PATTERN = "HH:mm";
    private static final SimpleDateFormat TIME_SIMPLE_DATE_FORMAT
            = new SimpleDateFormat(TIME_FORMAT_PATTERN);
    @Id
    @GeneratedValue
    private int id;


    @NotNull
    private ActivityType type;


    @NotNull(message = "Please select a level.")
    private SkillLevel level;


    @DateTimeFormat(pattern = DATE_FORMAT_PATTERN)
    @NotNull(message = "Please select a date.")
    private LocalDate date; //using dateformat data type

    @DateTimeFormat(pattern = TIME_FORMAT_PATTERN)
    @NotNull(message = "Please select a time.")
    private LocalTime time;

    @ManyToOne
    private User user;

    private boolean completed;

    @NotNull(message = "Please specify a location")
    private String  location;


    public Activity(){

    }

    public Activity(ActivityType type, SkillLevel level,LocalDate date, LocalTime time, String location){

        this.type = type;
        this.level = level;
        this.date = date;
        this.completed = false;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ActivityType getType() {
        return type;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public SkillLevel getLevel() {
        return level;
    }

    public void setLevel(SkillLevel level) {
        this.level = level;
    }


    public LocalDate getDate() {
        return date;
 }

    @JsonIgnore
    public String getFormattedDate() {
        return Activity.SIMPLE_DATE_FORMAT.format(date);
    }

    public void setDate(LocalDate date) {
        this.date = date;
   }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;

    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @JsonIgnore
    public String getFormattedTime() {
        return Activity.TIME_SIMPLE_DATE_FORMAT.format(time);
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
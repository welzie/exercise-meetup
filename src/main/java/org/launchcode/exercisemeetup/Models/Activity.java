package org.launchcode.exercisemeetup.Models;

import org.launchcode.exercisemeetup.Models.data.ActivityType;
import org.launchcode.exercisemeetup.Models.data.SkillLevel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.swing.text.DateFormatter;
import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.time.LocalDateTime;


@Entity
public class Activity{

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private ActivityType type;

    @NotNull
    private SkillLevel level;

    @NotNull
    private LocalDateTime date; //using dateformat data type

    public Activity(){

    }

    public Activity(ActivityType type, SkillLevel level, LocalDateTime date){

        this.type = type;
        this.level = level;
        this.date =date;
    }

    public int getId() {
        return id;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
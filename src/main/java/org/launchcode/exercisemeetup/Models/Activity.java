package org.launchcode.exercisemeetup.Models;

import org.launchcode.exercisemeetup.Models.data.ActivityType;
import org.launchcode.exercisemeetup.Models.data.SkillLevel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;


@Entity
public class Activity{

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private ActivityType type;

    @NotNull
    private SkillLevel level;

    public Activity(){

    }

    public Activity(ActivityType type, SkillLevel level){
        this.type = type;
        this.level = level;
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
}
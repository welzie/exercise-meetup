package org.launchcode.exercisemeetup.Models;

import org.launchcode.exercisemeetup.Models.data.ActivityType;
import org.launchcode.exercisemeetup.Models.data.SkillLevel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.swing.text.DateFormatter;
import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;



@Entity
public class Activity{

    private static final String START_DATE_FORMAT_PATTERN = "MM/dd/yyyy";
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT
            = new SimpleDateFormat(START_DATE_FORMAT_PATTERN);

    @Id
    @GeneratedValue
    private int id;


    @NotNull
    private ActivityType type;


    @NotNull
    private SkillLevel level;

    @DateTimeFormat(pattern = START_DATE_FORMAT_PATTERN)
    private LocalDate date; //using dateformat data type

    public Activity(){

    }

    public Activity(ActivityType type, SkillLevel level,LocalDate date){

        this.type = type;
        this.level = level;
        this.date = date;

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

    public LocalDate getDate() {
        return date;
 }

    public String getFormattedDate() {
        return Activity.SIMPLE_DATE_FORMAT.format(date);
    }

    public void setDate(LocalDate date) {
        this.date = date;
   }
}
package org.launchcode.exercisemeetup.Models;

import org.launchcode.exercisemeetup.Models.data.ActivityType;
import org.launchcode.exercisemeetup.Models.data.SkillLevel;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Activity{

    @Id
    @GeneratedValue
    private int id;

    private ActivityType type;

    private SkillLevel level;




}
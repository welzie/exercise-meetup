package org.launchcode.exercisemeetup.Models.data;

public enum SkillLevel {

    EASY ("Easy"),
    MEDIUM ("Medium"),
    HARD ("Hard"),
    EXPERT ("Expert");

    private final String name;

    SkillLevel(String name){
        this.name = name;

    }

    public String getName() {
        return name;
    }
}

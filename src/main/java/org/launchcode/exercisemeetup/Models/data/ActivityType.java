package org.launchcode.exercisemeetup.Models.data;

public enum ActivityType {





        MOUNTAINBIKING ("Mountain Biking"),
        ROADBIKING ("Road Biking"),
        JOGGING ("Jogging"),
        SWIMMING ("Swimming"),
        HIKING ("Hiking"),
        STRENGTHTRAINING ("Strength Training"),
        YOGA ("Yoga"),
        PILATES ("Pilates");

        private final String name;

        public String getName() {
            return name;
        }

        ActivityType(String name){
            this.name = name;
        }








}

package org.launchcode.exercisemeetup.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//structure of this register/login experience taken from Chris Bay's demo: https://github.com/LaunchCodeEducation/spring-filter-based-auth

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends AbstractEntity {

    @NotNull
    private String username;

    @NotNull
    private String pwHash;
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @OneToMany
    @JsonIgnore
    @JoinColumn(name = "user_uid")
    private List<Activity> activities = new ArrayList<>();

    private String lastBreach;

    private int breachNotify;

    public User() {
    }

    public User(String username, String password, String lastBreach) {
        this.username = username;
        this.pwHash = hashPassword(password);
        this.lastBreach = lastBreach;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public int getBreachNotify() {
        return breachNotify;
    }

    public void setBreachNotify(int breachNotify) {
        this.breachNotify = breachNotify;
    }

    public String getLastBreach() {
        return lastBreach;
    }

    public void setLastBreach(String lastBreach) {
        this.lastBreach = lastBreach;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPwHash() {
        return pwHash;
    }

    private static String hashPassword(String password) {
        return encoder.encode(password);
    }

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }

    public ArrayList<String> getUserActivityLocations(User user){
        ArrayList<String> locations = new ArrayList<String>();
        List<Activity> activities = user.getActivities();
        for( int i = 0; i < activities.size(); i++){
            Activity idivActivity = activities.get(i);
            String indivLocation = idivActivity.getLocation();
            locations.add(indivLocation);
        }
        return locations;


    }


}
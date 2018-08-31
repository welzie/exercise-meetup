package org.launchcode.exercisemeetup.Models;

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
    @JoinColumn(name = "user_uid")
    private List<Activity> activities = new ArrayList<>();

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.pwHash = hashPassword(password);
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwHash() {
        return pwHash;
    }

    private static String hashPassword(String password) {
        return encoder.encode(password);
    }

    public boolean isMatchingPassword(String password) {
        return encoder.matches(password, pwHash);
    }

}
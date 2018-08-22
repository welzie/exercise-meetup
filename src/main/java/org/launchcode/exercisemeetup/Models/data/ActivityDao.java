package org.launchcode.exercisemeetup.Models.data;

import org.launchcode.exercisemeetup.Models.Activity;
import org.launchcode.exercisemeetup.Models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


//structure of this register/login experience taken from Chris Bay's demo: https://github.com/LaunchCodeEducation/spring-filter-based-auth
@Repository
@Transactional
public interface ActivityDao extends CrudRepository<Activity, Integer> {

    public List<Activity> findByUser(User user);

    public ArrayList<Activity> findByDate(LocalDate date);

    public ArrayList<Activity> findByDateAndTime(LocalDate date, LocalTime time);

    public ArrayList<Activity> findByDateAndType(LocalDate date, ActivityType type);

    public ArrayList<Activity> findByDateAndLevel(LocalDate date, SkillLevel level);

    public ArrayList<Activity> findByDateAndTypeAndLevel(LocalDate date, ActivityType type, SkillLevel level);

    public ArrayList<Activity> findByDateAndTimeAndLevel(LocalDate date, LocalTime time, SkillLevel level);

    public ArrayList<Activity> findByDateAndTimeAndType(LocalDate date, LocalTime time, ActivityType type);

    public ArrayList<Activity> findByDateAndTimeAndTypeAndLevel(LocalDate date, LocalTime time, ActivityType type, SkillLevel level);

    public ArrayList<Activity>findByTime(LocalTime time);

    public ArrayList<Activity>findByTimeAndType(LocalTime time, ActivityType type);

    public ArrayList<Activity>findByTimeAndLevel(LocalTime time, SkillLevel level);

    public ArrayList<Activity>findByTimeAndTypeAndLevel(LocalTime time, ActivityType type, SkillLevel level);

    public Activity findById(int id);

    public ArrayList<Activity> findByType(ActivityType type);

    public ArrayList<Activity> findByTypeAndLevel(ActivityType type, SkillLevel level);

    public ArrayList<Activity>findByLevel(SkillLevel String);



}
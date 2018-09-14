package org.launchcode.exercisemeetup.Models.data;

import org.launchcode.exercisemeetup.Models.Activity;
import org.launchcode.exercisemeetup.Models.User;
import org.springframework.data.jpa.repository.Query;
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

    public ArrayList<Activity> findAllByOrderByDateAsc();

    public ArrayList<Activity> findByCompletedOrderByDateAsc(boolean completed);

    public List<Activity> findByUserAndCompletedOrderByDateAsc(User user, boolean completed);

    public ArrayList<Activity> findByDateAndCompletedOrderByDateAsc(LocalDate date, boolean completed);

    public ArrayList<Activity> findByDateAndTimeAndCompletedOrderByDateAsc(LocalDate date, LocalTime time, boolean completed);

    public ArrayList<Activity> findByDateAndTypeAndCompletedOrderByDateAsc(LocalDate date, ActivityType type, boolean completed);

    public ArrayList<Activity> findByDateAndLevelAndCompletedOrderByDateAsc(LocalDate date, SkillLevel level, boolean completed);

    public ArrayList<Activity> findByDateAndTypeAndLevelAndCompletedOrderByDateAsc(LocalDate date, ActivityType type, SkillLevel level, boolean completed);

    public ArrayList<Activity> findByDateAndTimeAndLevelAndCompletedOrderByDateAsc(LocalDate date, LocalTime time, SkillLevel level, boolean completed);

    public ArrayList<Activity> findByDateAndTimeAndTypeAndCompletedOrderByDateAsc(LocalDate date, LocalTime time, ActivityType type, boolean completed);

    public ArrayList<Activity> findByDateAndTimeAndTypeAndLevelAndCompletedOrderByDateAsc(LocalDate date, LocalTime time, ActivityType type, SkillLevel level, boolean completed);

    public ArrayList<Activity>findByTimeAndCompletedOrderByDateAsc(LocalTime time, boolean completed);

    public ArrayList<Activity>findByTimeAndTypeAndCompletedOrderByDateAsc(LocalTime time, ActivityType type, boolean completed);

    public ArrayList<Activity>findByTimeAndLevelAndCompletedOrderByDateAsc(LocalTime time, SkillLevel level, boolean completed);

    public ArrayList<Activity>findByTimeAndTypeAndLevelAndCompletedOrderByDateAsc(LocalTime time, ActivityType type, SkillLevel level, boolean completed);

    public Activity findById(int id);

    public ArrayList<Activity> findByTypeAndCompletedOrderByDateAsc(ActivityType type, boolean completed);

    public ArrayList<Activity> findByTypeAndLevelAndCompletedOrderByDateAsc(ActivityType type, SkillLevel level, boolean completed);

    public ArrayList<Activity>findByLevelAndCompletedOrderByDateAsc(SkillLevel String, boolean completed);

    public List<Activity> findByUserOrderByDateAsc(User user);

    public ArrayList<Activity> findByDateOrderByDateAsc(LocalDate date);

    public ArrayList<Activity> findByDateAndTimeOrderByDateAsc(LocalDate date, LocalTime time);

    public ArrayList<Activity> findByDateAndTypeOrderByDateAsc(LocalDate date, ActivityType type);

    public ArrayList<Activity> findByDateAndLevelOrderByDateAsc(LocalDate date, SkillLevel level);

    public ArrayList<Activity> findByDateAndTypeAndLevelOrderByDateAsc(LocalDate date, ActivityType type, SkillLevel level);

    public ArrayList<Activity> findByDateAndTimeAndLevelOrderByDateAsc(LocalDate date, LocalTime time, SkillLevel level);

    public ArrayList<Activity> findByDateAndTimeAndTypeOrderByDateAsc(LocalDate date, LocalTime time, ActivityType type);

    public ArrayList<Activity> findByDateAndTimeAndTypeAndLevelOrderByDateAsc(LocalDate date, LocalTime time, ActivityType type, SkillLevel level);

    public ArrayList<Activity>findByTimeOrderByDateAsc(LocalTime time);

    public ArrayList<Activity>findByTimeAndTypeOrderByDateAsc(LocalTime time, ActivityType type);

    public ArrayList<Activity>findByTimeAndLevelOrderByDateAsc(LocalTime time, SkillLevel level);

    public ArrayList<Activity>findByTimeAndTypeAndLevelOrderByDateAsc(LocalTime time, ActivityType type, SkillLevel level);

    public ArrayList<Activity> findByTypeOrderByDateAsc(ActivityType type);

    public ArrayList<Activity> findByTypeAndLevelOrderByDateAsc(ActivityType type, SkillLevel level);

    public ArrayList<Activity>findByLevelOrderByDateAsc(SkillLevel String);

}
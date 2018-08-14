package org.launchcode.exercisemeetup.Models.data;

import org.launchcode.exercisemeetup.Models.Activity;
import org.launchcode.exercisemeetup.Models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


//structure of this register/login experience taken from Chris Bay's demo: https://github.com/LaunchCodeEducation/spring-filter-based-auth
@Repository
@Transactional
public interface ActivityDao extends CrudRepository<Activity, Integer> {

    public List<Activity> findByUser(User user);

    public ArrayList<Activity> findByDate(LocalDate date);

    public Activity findById(int id);

    public ArrayList<Activity> findByType(ActivityType String);

    public ArrayList<Activity>findByLevel(SkillLevel String);

    public ArrayList<Activity>findByTime(LocalTime time);

}
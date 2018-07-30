package org.launchcode.exercisemeetup.Models.data;

import org.launchcode.exercisemeetup.Models.Activity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


//structure of this register/login experience taken from Chris Bay's demo: https://github.com/LaunchCodeEducation/spring-filter-based-auth
@Repository
@Transactional
public interface ActivityDao extends CrudRepository<Activity, Integer> {


}
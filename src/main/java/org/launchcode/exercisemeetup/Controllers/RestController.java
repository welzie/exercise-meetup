package org.launchcode.exercisemeetup.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.launchcode.exercisemeetup.Models.Activity;
import org.launchcode.exercisemeetup.Models.User;
import org.launchcode.exercisemeetup.Models.data.ActivityType;
import org.launchcode.exercisemeetup.Models.data.SkillLevel;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

@org.springframework.web.bind.annotation.RestController
@RequestMapping(value = "rest")
public class RestController extends AbstractController {

    @RequestMapping(value = "completed")
    public String completed(@RequestParam String username, @RequestParam boolean completed, @RequestParam int activityId, HttpSession session) {
        if (getUserFromSession(session) != null) {
            String usernameCheck = getUserFromSession(session).getUsername();
            if (username.equals(usernameCheck)) {
                 Activity activity = activityDao.findById(activityId);
                 activity.setCompleted(!completed);
                 activityDao.save(activity);
                return "{ \"completed\" : \"" + !completed + "\" }";
            }
        }
        /*TODO: add real error handler*/
        return "{ \"error\" : \"unauthorized user\" }";
    }

    @RequestMapping(value="activities")
    public @ResponseBody String activities() throws JsonProcessingException {
        /*needs
        *   number of last item
        *   */
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = new String();
        ArrayList<Activity> test = activityDao.findByCompletedOrderByDateAsc(false);
        jsonInString = mapper.writeValueAsString(test);


        return jsonInString;
    }

    @RequestMapping(value="search")
    public String search(Model model, @RequestParam(value = "search_date", required = false)
    @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate search_date,
                         @RequestParam(value = "time", required = false)@DateTimeFormat(pattern="HH:mm")LocalTime time,
                         @RequestParam(value = "type", required = false) ActivityType type,
                         @RequestParam(value = "level", required = false) SkillLevel level,
                         @RequestParam(value = "completed", required = false) String completed) throws JsonProcessingException {

        ArrayList<Activity> searchResult = new ArrayList<>();

        boolean isCompleted = false;

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = new String();

        if(completed != null && completed.equals("both")) {

            if (search_date != null && time!=null && type !=null && level !=null) {
                searchResult = activityDao.findByDateAndTimeAndTypeAndLevelOrderByDateAsc(search_date, time, type, level);
            }
            else if (search_date !=null && type !=null && level!=null) {
                searchResult = activityDao.findByDateAndTypeAndLevelOrderByDateAsc(search_date,type,level);
            }
            else if (search_date !=null && time !=null && level!=null) {
                searchResult = activityDao.findByDateAndTimeAndLevelOrderByDateAsc(search_date, time, level);
            }
            else if (search_date !=null && time!=null && type != null) {
                searchResult = activityDao.findByDateAndTimeAndTypeOrderByDateAsc(search_date, time, type);
            }
            else if (time !=null && type !=null && level !=null) {
                searchResult = activityDao.findByTimeAndTypeAndLevelOrderByDateAsc(time,type,level);
            }
            else if (time !=null && type !=null) {
                searchResult = activityDao.findByTimeAndTypeOrderByDateAsc(time,type);
            }
            else if (time !=null && level !=null) {
                searchResult = activityDao.findByTimeAndLevelOrderByDateAsc(time,level);
            }
            else if (type !=null && level !=null){
                searchResult = activityDao.findByTypeAndLevelOrderByDateAsc(type,level);
            }
            else if (search_date != null && type !=null) {
                searchResult = activityDao.findByDateAndTypeOrderByDateAsc(search_date,type);
            }
            else if (search_date !=null && level!=null) {
                searchResult = activityDao.findByDateAndLevelOrderByDateAsc(search_date,level);
            }
            else if (search_date !=null && time != null) {
                searchResult = activityDao.findByDateAndTimeOrderByDateAsc(search_date,time);
            }
            else if (search_date != null) {
                searchResult = activityDao.findByDateOrderByDateAsc(search_date);
            }
            else if (time !=null) {
                searchResult = activityDao.findByTimeOrderByDateAsc(time);
            }
            else if (type !=null) {
                searchResult = activityDao.findByTypeOrderByDateAsc(type);
            }
            else if (level !=null) {
                searchResult =activityDao.findByLevelOrderByDateAsc(level);
            } else {
                searchResult = activityDao.findAllByOrderByDateAsc();
            }

            jsonInString = mapper.writeValueAsString(searchResult);


            return jsonInString;

        } else if (completed != null && completed.equals("true")) {
            isCompleted = true;
        }


        if (search_date != null && time!=null && type !=null && level !=null) {
            searchResult = activityDao.findByDateAndTimeAndTypeAndLevelAndCompletedOrderByDateAsc(search_date, time, type, level, isCompleted);
        }
        else if (search_date !=null && type !=null && level!=null) {
            searchResult = activityDao.findByDateAndTypeAndLevelAndCompletedOrderByDateAsc(search_date,type,level, isCompleted);
        }
        else if (search_date !=null && time !=null && level!=null) {
            searchResult = activityDao.findByDateAndTimeAndLevelAndCompletedOrderByDateAsc(search_date, time, level, isCompleted);
        }
        else if (search_date !=null && time!=null && type != null) {
            searchResult = activityDao.findByDateAndTimeAndTypeAndCompletedOrderByDateAsc(search_date, time, type, isCompleted);
        }
        else if (time !=null && type !=null && level !=null) {
            searchResult = activityDao.findByTimeAndTypeAndLevelAndCompletedOrderByDateAsc(time,type,level, isCompleted);
        }
        else if (time !=null && type !=null) {
            searchResult = activityDao.findByTimeAndTypeAndCompletedOrderByDateAsc(time,type, isCompleted);
        }
        else if (time !=null && level !=null) {
            searchResult = activityDao.findByTimeAndLevelAndCompletedOrderByDateAsc(time,level, isCompleted);
        }
        else if (type !=null && level !=null){
            searchResult = activityDao.findByTypeAndLevelAndCompletedOrderByDateAsc(type,level, isCompleted);
        }
        else if (search_date != null && type !=null) {
            searchResult = activityDao.findByDateAndTypeAndCompletedOrderByDateAsc(search_date,type, isCompleted);
        }
        else if (search_date !=null && level!=null) {
            searchResult = activityDao.findByDateAndLevelAndCompletedOrderByDateAsc(search_date,level, isCompleted);
        }
        else if (search_date !=null && time != null) {
            searchResult = activityDao.findByDateAndTimeAndCompletedOrderByDateAsc(search_date,time, isCompleted);
        }
        else if (search_date != null) {
            searchResult = activityDao.findByDateAndCompletedOrderByDateAsc(search_date, isCompleted);
        }
        else if (time !=null) {
            searchResult = activityDao.findByTimeAndCompletedOrderByDateAsc(time, isCompleted);
        }
        else if (type !=null) {
            searchResult = activityDao.findByTypeAndCompletedOrderByDateAsc(type, isCompleted);
        }
        else if (level !=null) {
            searchResult =activityDao.findByLevelAndCompletedOrderByDateAsc(level, isCompleted);
        } else {
            searchResult = activityDao.findByCompletedOrderByDateAsc(isCompleted);
        }

        jsonInString = mapper.writeValueAsString(searchResult);
        return jsonInString;
    }

    @RequestMapping(value = "breachNotify")
    public User breachNotify(@RequestParam String username, @RequestParam int breachNotify, HttpSession session) {
        User user = userDao.findByUsername(username);
        if (getUserFromSession(session) != null) {
            String usernameCheck = getUserFromSession(session).getUsername();
            if (username.equals(usernameCheck)) {
                user.setBreachNotify(breachNotify);
            }
        }
        return userDao.save(user);
    }
}

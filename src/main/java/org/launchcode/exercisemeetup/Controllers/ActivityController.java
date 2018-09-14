package org.launchcode.exercisemeetup.Controllers;


import org.launchcode.exercisemeetup.Models.Activity;
import org.launchcode.exercisemeetup.Models.User;
import org.launchcode.exercisemeetup.Models.data.ActivityDao;
import org.launchcode.exercisemeetup.Models.data.ActivityType;
import org.launchcode.exercisemeetup.Models.data.SkillLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;


@Controller
@RequestMapping("activity")
public class ActivityController extends AbstractController {

    @Value("${google.maps.api.key}")
    private String apiKey;

    @RequestMapping(value = "")
    public String index(Model model) {
        Iterable<Activity> activities = activityDao.findAll();
        model.addAttribute("activities", activities);
        model.addAttribute("title", "User Activities");

        return "activity/index";
    }

    @RequestMapping(value="add", method = RequestMethod.GET)
    public String displayAddActivity(Model model, HttpSession httpSession) {

        String src = "https://maps.googleapis.com/maps/api/js?key=" + this.apiKey + "&libraries=places&callback=initMap";


        if (getUserFromSession(httpSession)==null) {
            return "redirect:/register";
        } else {

            model.addAttribute("title", "Add Activity");
            model.addAttribute("types", ActivityType.values());
            model.addAttribute("levels", SkillLevel.values());
            model.addAttribute(new Activity());
            model.addAttribute("apiKey", this.apiKey);
            model.addAttribute("src", src);



            return "activity/add-activity";
        }
    }

    @RequestMapping(value="add", method= RequestMethod.POST)
    public String processAddActivity(@ModelAttribute @Valid Activity newActivity, Errors errors,  Model model, HttpSession httpSession) {
        String src = "https://maps.googleapis.com/maps/api/js?key=" + this.apiKey + "&libraries=places&callback=initMap";

        Optional <LocalDate> date = Optional.ofNullable(newActivity.getDate());
        Boolean futureDate;
        String location = newActivity.getLocation();
        if (date.isPresent()) {
            futureDate = newActivity.getDate().isBefore(LocalDate.now());


        }else futureDate = false;

        if (errors.hasErrors() || futureDate ==true || location.isEmpty()) {
            model.addAttribute("title", "Add Activity");
            model.addAttribute("types", ActivityType.values());
            model.addAttribute("levels", SkillLevel.values());
            model.addAttribute("error",futureDate);
            model.addAttribute("location", location);
            model.addAttribute("apiKey", this.apiKey);
            model.addAttribute("src", src);



            return "activity/add-activity";
        }

        newActivity.setUser(getUserFromSession(httpSession));

        activityDao.save(newActivity);

        return "redirect:view/" + newActivity.getId();
    }

    @RequestMapping(value="view/{activityId}")
    public String viewActivity(@PathVariable int activityId, Model model, HttpSession httpSession,
                               @RequestParam(value = "search_date", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate search_date,
                               @RequestParam(value = "time", required = false)@DateTimeFormat(pattern="HH:mm")LocalTime time,
                               @RequestParam(value = "type", required = false) String type,
                               @RequestParam(value = "level", required = false) String level,
                               @RequestParam(value="completed", required = false) boolean completed){


       Activity activity = activityDao.findById(activityId);
       int activityOwnerId = activity.getUser().getUid();
       
        if (getUserFromSession(httpSession) != null && activityOwnerId == getUserFromSession(httpSession).getUid()) {
            model.addAttribute("owner", true);
        }

        if(completed) {
            activity.setCompleted(true);
        }

        model.addAttribute("activity", activity);
        model.addAttribute("title", "New Activity");
        model.addAttribute("types", ActivityType.values());
        model.addAttribute("levels", SkillLevel.values());
        model.addAttribute("activities", activityDao.findByUserOrderByDateAsc(getUserFromSession(httpSession)));

        return "activity/view-activity";
    }

    @RequestMapping(value="results")
    public String search(Model model,@RequestParam(value = "search_date", required = false)
                             @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate search_date,
                         @RequestParam(value = "time", required = false)@DateTimeFormat(pattern="HH:mm")LocalTime time,
                         @RequestParam(value = "type", required = false) ActivityType type,
                         @RequestParam(value = "level", required = false) SkillLevel level) {

        ArrayList<Activity> searchResult = new ArrayList<>();

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
        }
        model.addAttribute("searchResult", searchResult);

        return "activity/search-results";
    }

    @RequestMapping(value = "view-all")
    public String viewAll (Model model){
        model.addAttribute("title", "View All Activities");
        model.addAttribute("activities", activityDao.findAll());
        model.addAttribute("types", ActivityType.values());
        model.addAttribute("levels", SkillLevel.values());

            return "activity/view-all-activities";
        }

    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String displayEditActivity (Model model,@RequestParam int id){

        Activity activity = activityDao.findById(id);
        model.addAttribute("title", activity.getType());
        model.addAttribute("activity", activity);
        model.addAttribute("types", ActivityType.values());
        model.addAttribute("levels", SkillLevel.values());

        return "activity/edit";
    }

    @RequestMapping(value="edit", method = RequestMethod.POST)
    public String ProcessEditActivity(Model model,
                                      @RequestParam int id,
                                      @RequestParam(value = "type", required = false) ActivityType type,
                                      @RequestParam(value = "date", required = false)@DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date,
                                      @RequestParam(value = "level", required = false) SkillLevel level,
                                      @RequestParam(value = "location", required = false) String location,
                                      @RequestParam(value = "time", required = false)@DateTimeFormat(pattern="HH:mm")LocalTime time){





        Activity activity = activityDao.findById(id);

        activity.setType(type);
        activityDao.save(activity);

            if(date != null){
                activity.setDate(date);
                activityDao.save(activity);

            }
            if (level != null) {
                activity.setLevel(level);
                activityDao.save(activity);
            }
            if (time != null) {
                activity.setTime(time);
                activityDao.save(activity);
            }
        if (location != null) {
            activity.setLocation(location);
            activityDao.save(activity);
        }

            return "main/profile";
        }


    @RequestMapping(value="delete")
    public String deleteActivity(Model model, @RequestParam int id, HttpSession session) {
        Activity activity = activityDao.findById(id);
        int uid = getUserFromSession(session).getUid();

        if (activity.getUser().getUid() == uid) {
            activityDao.deleteById(id);
            return "redirect:/user/" + getUserFromSession(session).getUsername();
        } else if (getUserFromSession(session) == null) {
            /* TODO: if the user doesn't own the activity throw an error*/
            return "redirect:/login";
        }

        /*TODO: show error on redirect */
        return "redirect:/";
    }

}
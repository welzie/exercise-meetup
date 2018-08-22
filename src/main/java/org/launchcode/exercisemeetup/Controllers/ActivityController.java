package org.launchcode.exercisemeetup.Controllers;


import org.launchcode.exercisemeetup.Models.Activity;
import org.launchcode.exercisemeetup.Models.User;
import org.launchcode.exercisemeetup.Models.data.ActivityDao;
import org.launchcode.exercisemeetup.Models.data.ActivityType;
import org.launchcode.exercisemeetup.Models.data.SkillLevel;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("activity")
public class ActivityController extends AbstractController {

    @RequestMapping(value = "")
    public String index(Model model) {
        Iterable<Activity> activities = activityDao.findAll();
        model.addAttribute("activities", activities);
        model.addAttribute("title", "User Activities");

        return "activity/index";
    }

    @RequestMapping(value="add", method = RequestMethod.GET)
    public String displayAddActivity(Model model, HttpSession httpSession) {

        if (getUserFromSession(httpSession)==null) {
            return "redirect:/register";
        } else {

            model.addAttribute("title", "Add Activity");
            model.addAttribute("types", ActivityType.values());
            model.addAttribute("levels", SkillLevel.values());
            model.addAttribute(new Activity());

            return "activity/add-activity";
        }
    }

    @RequestMapping(value="add", method= RequestMethod.POST) //error is happening in the @valid activity new activity
    //failing to convert data type string to needed dateformat data type
    public String processAddActivity(@ModelAttribute @Valid Activity newActivity, Errors errors,  Model model, HttpSession httpSession) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Activity");
            model.addAttribute("types", ActivityType.values());
            model.addAttribute("levels", SkillLevel.values());

            return "activity/add-activity";
        }

        newActivity.setUser(getUserFromSession(httpSession));

        activityDao.save(newActivity);

        return "redirect:view/" + newActivity.getId();



    }

    @RequestMapping(value="view/{activityId}")
    public String viewActivity(@PathVariable int activityId, Model model, HttpSession httpSession,
                               @RequestParam(value = "search_date", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate search_date,
                               @RequestParam(value="completed", required = false) boolean completed,
                               HttpSession session){

       Activity activity = activityDao.findById(activityId);
       int activityOwnerId = activity.getUser().getUid();
       
        if (getUserFromSession(session) != null && activityOwnerId == getUserFromSession(session).getUid()) {
            model.addAttribute("owner", true);
        }

        if(completed) {
            activity.setCompleted(true);
        }

        model.addAttribute("activity", activity);
        model.addAttribute("title", "New Activity");
        model.addAttribute("activities", activityDao.findByUser(getUserFromSession(httpSession)));

        ArrayList<Activity> searchResult= new ArrayList<>();

        if(search_date != null) {
            searchResult=activityDao.findByDate(search_date);
        }
        model.addAttribute("searchResult",searchResult);

        return "activity/view-activity";
    }

    @RequestMapping(value="view-all")
    public String viewAll(Model model) {
        model.addAttribute("title", "View All Activities");
        model.addAttribute("activities", activityDao.findAll());

        return "activity/view-all-activities";
    }


    @RequestMapping(value="edit", method = RequestMethod.GET)
    public String displayEditActivity(Model model, @RequestParam int id ){


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
                                      @RequestParam(value = "date", required = false) @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate date,
                                      @RequestParam(value = "level", required = false) SkillLevel level,
                                      @RequestParam(value = "time", required = false)@DateTimeFormat(pattern="HH:mm")LocalTime time){
        Activity activity = activityDao.findById(id);

        activity.setType(type);



        if(date != null){
            activity.setDate(date);


        }
        if(level != null){
            activity.setLevel(level);

        }
        if(time != null){
            activity.setTime(time);

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
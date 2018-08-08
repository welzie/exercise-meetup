package org.launchcode.exercisemeetup.Controllers;


import org.launchcode.exercisemeetup.Models.Activity;
import org.launchcode.exercisemeetup.Models.data.ActivityDao;
import org.launchcode.exercisemeetup.Models.data.ActivityType;
import org.launchcode.exercisemeetup.Models.data.SkillLevel;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
            model.addAttribute(new Activity());
            return "activity/add-activity";
        }

        newActivity.setUser(getUserFromSession(httpSession));

        activityDao.save(newActivity);

        return "redirect:view/" + newActivity.getId();



    }

    @RequestMapping(value="view/{activityId}")
    public String viewActivity(@PathVariable int activityId, Model model, HttpSession httpSession, @RequestParam(value="completed", required = false) boolean completed){

        Optional<Activity> activity = activityDao.findById(activityId);
        Activity activityInfo = activity.get();
        if(completed == true) {
            activityInfo.setCompleted(true);
        }
        model.addAttribute("activity", activityInfo);
        model.addAttribute("title", "New Activity");
        model.addAttribute("activities", activityDao.findByUser(getUserFromSession(httpSession)));

        return "activity/view-activity";
    }

    @RequestMapping(value="view-all")
    public String viewAll(Model model) {
        model.addAttribute("title", "View All Activities");
        model.addAttribute("activities", activityDao.findAll());

        return "activity/view-all-activities";
    }

}
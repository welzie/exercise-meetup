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

import javax.validation.Valid;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("main/activity")
public class ActivityController{

    @Autowired
    private ActivityDao activityDao;

    @RequestMapping(value = "")
    public String index(Model model) {
        Iterable<Activity> activities = activityDao.findAll();
        model.addAttribute("activities", activities);
        model.addAttribute("title", "User Activities");

        return "activity/index";
    }

    @RequestMapping(value="add", method = RequestMethod.GET)
    public String displayAddActivity(Model model){
        model.addAttribute("title", "Add Activity");
        model.addAttribute("types", ActivityType.values());
        model.addAttribute("levels", SkillLevel.values());
        model.addAttribute(new Activity());

        return "activity/add-activity";
    }

    @RequestMapping(value="add", method= RequestMethod.POST) //error is happening in the @valid activity new activity
    //failing to convert data type string to needed dateformat data type
    public String processAddActivity(@ModelAttribute @Valid Activity newActivity,
                                     Errors errors,  Model model){
//               DateFormat actDate = newActivity.getDate();   i was trying to think of was to get into correct data type
//        Date newActDate = actDate.parse(String.valueOf(actDate));


        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Activity");
            model.addAttribute("types", ActivityType.values());
            model.addAttribute("levels", SkillLevel.values());
            model.addAttribute(new Activity());
            return "activity/add-activity";
        }



        activityDao.save(newActivity);
        return "redirect:view/" + newActivity.getId();



    }

    @RequestMapping(value="view/{activityId}",method = RequestMethod.GET)
    public String viewActivity(@PathVariable int activityId, Model model){

        Optional<Activity> activity = activityDao.findById(activityId);
        Activity activityInfo = activity.get();
        model.addAttribute("activity", activityInfo);
        model.addAttribute("title", "New Activity");

        return "activity/view-activity";


    }

}
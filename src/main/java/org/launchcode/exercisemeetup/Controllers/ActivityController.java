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
import java.util.Optional;

@Controller
@RequestMapping("activity")
public class ActivityController extends AbstractController {

    @RequestMapping(value = "")
    public String index(Model model) {
        model.addAttribute("title", "New Activity");

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

    @RequestMapping(value="add", method= RequestMethod.POST)
    public String processAddActivity(@ModelAttribute @Valid Activity newActivity,
                                     Errors errors,  Model model, HttpSession httpSession){
        int uId = getUserFromSession(httpSession).getUid();


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

    @RequestMapping(value="view/{activityId}",method = RequestMethod.GET)
    public String viewActivity(@PathVariable int activityId, Model model, HttpSession httpSession){

        Optional<Activity> activity = activityDao.findById(activityId);
        Activity activityInfo = activity.get();
        model.addAttribute("activity", activityInfo);
        model.addAttribute("title", "New Activity");
        model.addAttribute("activities", activityDao.findByUser(getUserFromSession(httpSession)));

        return "activity/view-activity";


    }

}
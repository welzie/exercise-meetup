package org.launchcode.exercisemeetup.Controllers;


import org.launchcode.exercisemeetup.Models.data.ActivityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("main/activity")
public class ActivityController{

    @Autowired
    private ActivityDao activityDao;

    @RequestMapping(value = "")
    public String index(Model model) {
        model.addAttribute("title", "New Activity");

        return "activity/index";
    }
}
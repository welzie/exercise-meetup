package org.launchcode.exercisemeetup.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("main")
public class UserController {
    @RequestMapping(value = "")
    public String index(Model model) {
        model.addAttribute("title", "Index");
    return "main/index";
    }

    @RequestMapping(value="register")
    public String register(Model model) {
        model.addAttribute("title", "New User Registration");
    return "main/register";
    }
}

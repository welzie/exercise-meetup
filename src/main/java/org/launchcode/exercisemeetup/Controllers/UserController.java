package org.launchcode.exercisemeetup.Controllers;

import org.launchcode.exercisemeetup.Models.User;
import org.launchcode.exercisemeetup.Models.data.UserDao;
import org.launchcode.exercisemeetup.Models.forms.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("main")
public class UserController extends org.launchcode.springfilterbasedauth.controllers.AbstractController {


    @Autowired
    private UserDao userDao;

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

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute(new LoginForm());
        model.addAttribute("title","Please Login");
    return "main/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(@ModelAttribute LoginForm form, Errors errors, HttpServletRequest request) {


        if (errors.hasErrors()) {
            return "login";
        }

        User theUser = userDao.findByusername(form.getUsername());
        String password = form.getPassword();

        if(theUser == null) {

            errors.rejectValue("username", "user.invalid", "The given username does not exist");
            return "login";
        }

        if (!theUser.isMatchingPassword(password)) {
            errors.rejectValue("password","password.invalid","Invalid password");
            return "login";
        }

        setUserInSession(request.getSession(), theUser);

    return "redirect:";

    }


}

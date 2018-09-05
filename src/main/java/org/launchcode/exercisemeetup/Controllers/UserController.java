package org.launchcode.exercisemeetup.Controllers;

import org.launchcode.exercisemeetup.Models.Activity;
import org.launchcode.exercisemeetup.Models.User;
import org.launchcode.exercisemeetup.Models.data.ActivityType;
import org.launchcode.exercisemeetup.Models.data.SkillLevel;
import org.launchcode.exercisemeetup.Models.data.UserDao;
import org.launchcode.exercisemeetup.Models.forms.LoginForm;
import org.launchcode.exercisemeetup.Models.forms.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping("")
public class UserController extends AbstractController {


    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "")
    public String index(Model model) {
        model.addAttribute("title", "Index");
    return "main/landingpage";
    }

    @RequestMapping(value="register", method=RequestMethod.GET)
    public String register(Model model) {

        model.addAttribute("title", "New User Registration");
        model.addAttribute(new RegisterForm());
    return "main/register";
    }

    @RequestMapping(value="register", method = RequestMethod.POST)
    public String processRegisterForm(@ModelAttribute @Valid RegisterForm form, Errors errors, HttpServletRequest request) {


        if (errors.hasErrors()) {
            return "main/register";
        }

        User existingUser = userDao.findByUsername(form.getUsername());


        if (existingUser != null) {
            errors.rejectValue("username", "username.alreadyexists", "A user with that username already exists");
            return "main/register";
        }

        User newUser = new User(form.getUsername(), form.getPassword());
        userDao.save(newUser);
        setUserInSession(request.getSession(), newUser);

        return "redirect:activity/add";
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
            return "main/login";
        }

        User theUser = userDao.findByUsername(form.getUsername());
        String password = form.getPassword();

        if(theUser == null) {

            errors.rejectValue("username", "user.invalid", "The given username does not exist");
            return "main/login";
        }

        if (!theUser.isMatchingPassword(password)) {
            errors.rejectValue("password","password.invalid","Invalid password");
            return "main/login";
        }

        setUserInSession(request.getSession(), theUser);

        return "redirect:activity/add";

    }

    @RequestMapping(value = "logout", method=RequestMethod.POST)
    public String logout(HttpSession session) {
        if(!getUserFromSession(session).equals(null)) {
            removeUserFromSession(session);
        }


        session.removeAttribute("user_id");
        return "redirect:";

    }

    @RequestMapping(value = "user/{username}", method = RequestMethod.GET)
    public String profile(@PathVariable String username, Model model) {

        /* Add edit profile links if user id and session user id match? */
        User user = userDao.findByUsername(username);

        ArrayList<String> locations = user.getUserActivityLocations(user);
        model.addAttribute("user", user);

        model.addAttribute("types", ActivityType.values());
        model.addAttribute("levels", SkillLevel.values());

        model.addAttribute("locations", locations);



        return "main/profile";
    }



}

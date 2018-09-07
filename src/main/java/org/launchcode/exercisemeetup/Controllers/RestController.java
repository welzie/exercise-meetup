package org.launchcode.exercisemeetup.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.launchcode.exercisemeetup.Models.Activity;
import org.launchcode.exercisemeetup.Models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@org.springframework.web.bind.annotation.RestController
@RequestMapping(value = "rest")
public class RestController extends AbstractController {

    @Value("${google.maps.api.key}")
    private String apiKey;

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
        System.out.println(this.apiKey);


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

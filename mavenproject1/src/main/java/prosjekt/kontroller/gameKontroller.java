/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package prosjekt.kontroller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;
import prosjekt.Domene.User;
import prosjekt.Ui.Assignment;
import prosjekt.Ui.Loginform;
import prosjekt.database.DatabaseConnection;

/**
 *
 * @author balder
 */
@Controller
@SessionAttributes({"assignment","loginform"})
public class gameKontroller {
    
    @Autowired
    private DatabaseConnection database;
    
    @ModelAttribute("assignment")
    public Assignment makeAssignment(){
        return new Assignment();
    }
    
    @RequestMapping (value = "game")
    public String game (@ModelAttribute(value="assignment") Assignment assignment, Model model) {
        assignment.setAllTasks(database.getTasks(1));
        switch (assignment.getCurrentTask().getType()) {
            case "hangman":
                return "hangmang";
            case "mpc":
                return "multiplechoice";
            default:
                return "game";
        }
    }
    
    @RequestMapping (value = "nesteOppgave")
    public String nesteOppg(@ModelAttribute(value="assignment") Assignment assignment, 
            @ModelAttribute(value="loginform") Loginform loginform, WebRequest request, 
                HttpServletRequest user, Model model) {
        if(assignment.checkNumbers()) {
            int tasknr = assignment.nextTask();
            if(tasknr != -1){
                switch (assignment.getCurrentTask().getType()) {
                    case "hangman":
                        return "hangmang";
                    case "mpc":
                        return "multiplechoice";
                    default:
                        return "game";
                }
            } else {   
                User u = database.getUser(((User)user.getSession().getAttribute("currentUser")).getEmail());
                database.registerScore( u  , assignment.sumUp() , 1);
                request.removeAttribute("assignment", WebRequest.SCOPE_SESSION);
                model.addAttribute("assignment", makeAssignment());
                return "Hovedside";
            }
        } else {
           if(assignment.getCurrentTaskNr() != -1){
                switch (assignment.getCurrentTask().getType()) {
                    case "hangman":
                        return "hangmang";
                    case "mpc":
                        return "multiplechoice";
                    default:
                        return "game";
                }
            } else {
               return "Hovedside";
           } 
        }
    }
}

package prosjekt.kontroller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public String game (@ModelAttribute(value="assignment") Assignment assignment, @ModelAttribute(value="loginform") Loginform loginform, WebRequest request, Model model) {
        if(loginform.isInGame() == false){
            assignment.setTimescore(10);
            assignment.setCurrentTask(0);
            assignment.setAllTasks(database.getTasks());
            loginform.setInGame(true);
        } else {
            assignment.setTimescore(0);
        }

        loginform.setMessages(database.gotMessage(loginform.getUser()));
        switch (assignment.getCurrentTask().getType()) {
            case "hangman":
                return "hangman";
            case "mpc":
                return "multiplechoice";
            default:
                return "game";
        }
    }
    
    @RequestMapping (value = "nesteOppgave")
    public String nesteOppg(@ModelAttribute(value="loginform") Loginform loginform, @ModelAttribute(value="assignment") Assignment assignment, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        boolean loggedInOk = false;
        try{
            User bruker = (User)session.getAttribute("currentUser");
            if(bruker.getId() >= 1){
                model.addAttribute("loggedIn", true);
                loggedInOk = true;
            }else{
                model.addAttribute("loggedIn", false);
                return "login";
            }
        }catch(Exception e){
            model.addAttribute("loggedIn", false);
        }
        if(!loggedInOk){
            return "login";
        }
        loginform.setMessages(database.gotMessage(loginform.getUser()));
        if(assignment.checkNumbers()) {
            int tasknr = assignment.nextTask();
            if(tasknr != -1){
                switch (assignment.getCurrentTask().getType()) {
                    case "hangman":
                        return "hangman";
                    case "mpc":
                        return "multiplechoice";
                    default:
                        return "game";
                }
            } else {   
                User u = database.getUser(((User)request.getSession().getAttribute("currentUser")).getEmail());
                database.registerScore( u  , assignment.sumUp() , 1);
                return "score";
            }
        } else {
           if(assignment.getCurrentTaskNr() != -1){
                switch (assignment.getCurrentTask().getType()) {
                    case "hangman":
                        return "hangman";
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

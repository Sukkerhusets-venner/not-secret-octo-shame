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
@SessionAttributes({"loginform"})
public class gameKontroller {
    
    @Autowired
    private DatabaseConnection database;
    
    @RequestMapping (value = "game")
    public String game (@ModelAttribute(value="loginform") Loginform loginform, HttpServletRequest req, WebRequest request, Model model) {
        HttpSession session = req.getSession();
        try{
            User bruker = (User)session.getAttribute("currentUser");
            if(bruker.getId() < 1){
                return "login";
            }
        }catch(Exception e){
            return "login";
        }
        if(loginform.isInGame() == false){
            loginform.setAssignment(new Assignment());
            loginform.getAssignment().setTimescore(10);
            loginform.getAssignment().setCurrentTask(0);
            loginform.getAssignment().setAllTasks(database.getTasks());
            loginform.setInGame(true);
        } else {
             loginform.getAssignment().setTimescore(0);
        }
        switch ( loginform.getAssignment().getCurrentTask().getType()) {
            case "hangman":
                return "hangman";
            case "mpc":
                if( loginform.getAssignment().getTimescore()==10){
                     loginform.getAssignment().setTimescore(5);
                }
                return "multiplechoice";
            default:
                return "game";
        }
    }
    
    @RequestMapping (value = "nesteOppgave")
    public String nesteOppg(@ModelAttribute(value="loginform") Loginform loginform, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();boolean loggedInOk = false;
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
        if(loginform.isFerdig()){
            return "score";
        }
        if( loginform.getAssignment().checkNumbers()) {
            int tasknr =  loginform.getAssignment().nextTask();
            if(tasknr != -1){
                switch ( loginform.getAssignment().getCurrentTask().getType()) {
                    case "hangman":
                        return "hangman";
                    case "mpc":
                        if( loginform.getAssignment().getTimescore()==10){
                             loginform.getAssignment().setTimescore(5);
                        }
                        return "multiplechoice";
                    default:
                        return "game";
                }
            } else {   
                User u = database.getUser(((User)request.getSession().getAttribute("currentUser")).getEmail());
                database.registerScore( u  ,  loginform.getAssignment().sumUp() , 1);
                loginform.setFerdig(true);
                return "score";
            }
        } else {
           if( loginform.getAssignment().getCurrentTaskNr() != -1){
                switch ( loginform.getAssignment().getCurrentTask().getType()) {
                    case "hangman":
                        return "hangman";
                    case "mpc":
                        if(loginform.getAssignment().getTimescore()==10){
                             loginform.getAssignment().setTimescore(5);
                        }
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

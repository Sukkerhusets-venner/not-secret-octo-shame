
package prosjekt.kontroller;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
<<<<<<< HEAD
=======
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import prosjekt.Domene.User;
>>>>>>> FETCH_HEAD
import prosjekt.Domene.UserScore;
import prosjekt.Ui.Loginform;
import prosjekt.database.DatabaseConnection;

@Controller
@SessionAttributes({"loginform"})
public class loginKontroller {
    
    @Autowired
    private DatabaseConnection database;
    
<<<<<<< HEAD
=======
    
     @ModelAttribute("loginform")
    public Loginform makeAssignment(){
        return new Loginform();
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String meh(){
        return "error";
    }
>>>>>>> FETCH_HEAD
    @RequestMapping(value = "/*")
    public String showForm(@ModelAttribute Loginform loginform){
        return "login";
    }
    @RequestMapping (value = "Log inn")
    public String login (@ModelAttribute(value="loginform") Loginform loginform, HttpServletRequest request, Model model) {
        if(loginform.getUser().getPassword().isEmpty() || loginform.getUser().getEmail().isEmpty()){
            model.addAttribute("loginError", "Fyll inn alle feltene");
            return "login";
        }else if (database.checkLogin(loginform.getUser().getEmail(), loginform.getUser().getPassword())) {
            HttpSession session = request.getSession();
<<<<<<< HEAD
            session.setAttribute("currentUser", database.getUser(loginform.getUser().getEmail()).getUsername());
            session.setAttribute("loginform", loginform);
=======
            session.setAttribute ("Username", database.getUser(loginform.getUser().getEmail()).getUsername());
            session.setAttribute("currentUser", new User( database.getUser(loginform.getUser().getEmail()).getUsername(), 
                    loginform.getUser().getEmail(), loginform.getUser().getPassword()));
>>>>>>> FETCH_HEAD
            ArrayList<UserScore> hiScores = database.getHighScoreList();
            loginform.setHiScore(hiScores);
            return "Hovedside";
        } else {
            model.addAttribute("loginError", "Feil email/passord");
            return "login";
        }
    }
}

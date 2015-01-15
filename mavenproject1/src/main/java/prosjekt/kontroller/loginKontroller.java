
package prosjekt.kontroller;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import prosjekt.Domene.User;
import prosjekt.Domene.UserScore;
import prosjekt.Ui.Loginform;
import prosjekt.database.DatabaseConnection;

@Controller
@SessionAttributes({"loginform"})
public class loginKontroller {
    
    @Autowired
    private DatabaseConnection database;
    
    @ModelAttribute("loginform")
    public Loginform makeAssignment(){
        return new Loginform();
    }
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

            session.setAttribute("loginform", loginform);
            session.setAttribute("currentUser", new User( database.getUser(loginform.getUser().getEmail()).getUsername(), 
                    loginform.getUser().getEmail(), loginform.getUser().getPassword()));
            ArrayList<UserScore> hiScores = database.getHighScoreList();
            loginform.setHiScore(hiScores);
<<<<<<< Updated upstream
            loginform.getUser().setUsername(database.getUser(loginform.getUser().getEmail()).getUsername());
=======
            
>>>>>>> Stashed changes
            return "Hovedside";
        } else {
            model.addAttribute("loginError", "Feil email/passord");
            return "login";
        }
    }
}

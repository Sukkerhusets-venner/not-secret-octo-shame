
package prosjekt.kontroller;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import prosjekt.Domene.UserScore;
import prosjekt.Ui.Loginform;
import prosjekt.database.DatabaseConnection;

@Controller
public class loginKontroller {
    
    @Autowired
    private DatabaseConnection database;
    
    @RequestMapping(value = "/*")
    public String showForm(@ModelAttribute Loginform loginform){
        return "login";
    }
    @RequestMapping (value = "Log inn")
    public String login (@ModelAttribute Loginform loginform, HttpServletRequest request, Model model) {
        if(loginform.getUser().getPassword().isEmpty() || loginform.getUser().getEmail().isEmpty()){
            model.addAttribute("loginError", "Fyll inn alle feltene");
            return "login";
        }else if (database.checkLogin(loginform.getUser().getEmail(), loginform.getUser().getPassword())) {
            HttpSession session = request.getSession();
            session.setAttribute("currentUser", database.getUser(loginform.getUser().getEmail()).getUsername());
            session.setAttribute("loginform", loginform);
            ArrayList<UserScore> hiScores = database.getHighScoreList();
            loginform.setHiScoreList(hiScores);
            return "Hovedside";
        } else {
            model.addAttribute("loginError", "Feil email/passord");
            return "login";
        }
    }
}

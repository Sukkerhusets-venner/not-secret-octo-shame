
package prosjekt.kontroller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import prosjekt.Domene.User;
import prosjekt.Ui.Editform;
import prosjekt.Ui.Loginform;
import prosjekt.database.DatabaseConnection;

@Controller
public class profilKontroller {
    
    @Autowired
    private DatabaseConnection database;
    
    @RequestMapping (value = "byttBrukernavn")
    public String byttBrukernavn (Editform editform, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        
        Loginform loginform = (Loginform) session.getAttribute("loginform");
        User currentUser = loginform.getUser();
        model.addAttribute("godkjentListe", database.getUserScoreOverview());
        model.addAttribute("profilbrukernavn", "haha");
        
        boolean inputfeil = false;
        
        if (editform.getUserNew().getUsername().isEmpty()) {
            inputfeil = true;
            model.addAttribute("InputfeilBrukernavn", "Feltet kan ikke være tomt");
        }
        
        if (editform.getUserOld().getPassword().isEmpty()) {
            inputfeil = true;
            model.addAttribute("InputfeilPassord", "Feltet kan ikke være tomt");
        }
        
        if (inputfeil){
            session.setAttribute("loginform", loginform);
            return "Hovedside";
        }

        if (database.checkLogin(currentUser.getEmail(), editform.getUserOld().getPassword())) {
            currentUser.setUsername(editform.getUserNew().getUsername());
            database.editUser(currentUser);
            loginform.getUser().setUsername(editform.getUserNew().getUsername());
            session.setAttribute("loginform", loginform);
            return "Hovedside";
        } else {
           model.addAttribute("brukernavnfeilpassord", "Passordet er ikke riktig");
           session.setAttribute("loginform", loginform);
           return "Hovedside";
        }
    }
    
    @RequestMapping (value = "byttPassord")
    public String byttPassord (Editform editform, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        
        Loginform loginform = (Loginform) session.getAttribute("loginform");
        User currentUser = loginform.getUser();
        model.addAttribute("godkjentListe", database.getUserScoreOverview());
        model.addAttribute("profilpassord", "hehe");
        
        boolean inputfeil = false;
        
        if (editform.getUserNew().getPassword().isEmpty()) {
            inputfeil = true;
            model.addAttribute("Inputfeilnyttpassord", "Feltet kan ikke være tomt");
        }
        
        if (editform.getUserOld().getPassword().isEmpty()) {
            inputfeil = true;
            model.addAttribute("Inputfeilgammeltpassord", "Feltet kan ikke være tomt");
        }
        
        if (inputfeil){
            session.setAttribute("loginform", loginform);
            return "Hovedside";
        }

        if (database.checkLogin(currentUser.getEmail(), editform.getUserOld().getPassword())) {
            currentUser.setPassword(editform.getUserNew().getPassword());
            database.editUser(currentUser);
            loginform.getUser().setPassword(editform.getUserNew().getPassword());
            session.setAttribute("loginform", loginform);
            return "Hovedside";
        } else {
           model.addAttribute("passordfeilpassord", "Passordet er ikke riktig");
           session.setAttribute("loginform", loginform);
           return "Hovedside";
        }
    }
    
    
}

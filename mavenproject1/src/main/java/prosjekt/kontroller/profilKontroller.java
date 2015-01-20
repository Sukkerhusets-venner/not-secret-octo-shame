
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
            model.addAttribute("suksessBrukernavn", "Du har byttet brukernavn. Gratulerer!");
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
        
        if (editform.getUserNew().getPassword().length() < 6) {
            inputfeil = true;
            model.addAttribute("Inputfeilnyttpassord", "Det nye passordet må være minst 6 karakterer");
        }
        
        if (editform.getUserOld().getPassword().isEmpty()) {
            inputfeil = true;
            model.addAttribute("Inputfeilgammeltpassord", "Feltet kan ikke være tomt");
        }
        
        if (editform.getUserNew().getEmail().length() < 6) {
            inputfeil = true;
            model.addAttribute("Inputfeilnyttpassordbekreft", "Det nye passordet må være minst 6 karakterer");
        }
        
        if (!(editform.getUserNew().getEmail().equals(editform.getUserNew().getPassword())))
            inputfeil = true;
            model.addAttribute("passordfeilpassord", "Felt 2 og 3 stemmer ikke overens");
        
        if (inputfeil){
            session.setAttribute("loginform", loginform);
            return "Hovedside";
        }

        if (database.checkLogin(currentUser.getEmail(), editform.getUserOld().getPassword())) {
            currentUser.setPassword(editform.getUserNew().getPassword());
            database.editUser(currentUser);
            loginform.getUser().setPassword(editform.getUserNew().getPassword());
            session.setAttribute("loginform", loginform);
            model.addAttribute("suksessPassord", "Du har byttet passord. Gratulerer!");
            return "Hovedside";
        } else {
           model.addAttribute("passordfeilpassord", "Det gamle passordet er ikke riktig");
           session.setAttribute("loginform", loginform);
           return "Hovedside";
        }
    }
    
    
}


package prosjekt.kontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import prosjekt.Ui.Loginform;
import prosjekt.Ui.Registreringform;
import prosjekt.database.DatabaseConnection;
import prosjekt.mailservice.emailer;



@Controller
public class registrerKontroller {
    
    @Autowired
    private DatabaseConnection database;
    
    @Autowired
    private emailer emailer;
    
    @RequestMapping(value = "/registrer")
    public String showForm(@ModelAttribute Registreringform registreringform){
        return "registrer";
    }
    @RequestMapping(value = "/send")
    public String showForm(@ModelAttribute Registreringform registreringform, Model model){
        if (database.registerUser(registreringform.getUser())) {
            emailer.email(registreringform.getUser().getEmail(), registreringform.getUser().getUsername(),
                            database.getUser(registreringform.getUser().getEmail()).getPassword());
            return "registerSuccess";
        } else {
            model.addAttribute("registerError", "En bruker med denne emailen er allerede registrert");
            return "registrer";
        }
    }
    
    @RequestMapping (value = "/login")
    public String showlogin (@ModelAttribute Loginform loginform) {
        return "login";
    }
    
    @RequestMapping (value = "/registrertest")
    public String registertest () {
        return "registerSuccess";
    }
    
    @RequestMapping(value = "/game")
    public String showForm(){
        return "game";
    }
    
}

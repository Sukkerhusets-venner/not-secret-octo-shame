
package prosjekt.kontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
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
        
        boolean returnregister = false;
        
        if (!emailer.validator (registreringform.getUser().getEmail())) {
            model.addAttribute("emailError", "Du må skrive inn en gyldig email adresse");
            returnregister = true;
        }
        
        if (registreringform.getUser().getUsername().isEmpty()) {
            model.addAttribute("usernameEmptyError", "Du må skrive inn et brukernavn");
            returnregister = true;
        }
        
        if (returnregister) {
            return "registrer";
        }
        
        String password = database.registerUser(registreringform.getUser());
        if (password != null) {
            emailer.email(registreringform.getUser().getEmail(), registreringform.getUser().getUsername(), password);
            return "registerSuccess";
        } else {
            model.addAttribute("registerError", "En bruker med denne emailen er allerede registrert");
            return "registrer";
        }
    }  
}

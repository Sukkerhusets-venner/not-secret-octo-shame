
package prosjekt.kontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import prosjekt.Ui.Loginform;
import prosjekt.Ui.Registreringform;



@Controller
public class registrerKontroller {
    
    @RequestMapping(value = "/registrer")
    public String showForm(@ModelAttribute Registreringform registreringform){
        return "registrer";
    }
    @RequestMapping(value = "/send")
    public String showForm(@ModelAttribute Loginform loginform, Model model){
        return "login";
    }
    @RequestMapping(value = "/game")
    public String showForm(){
        return "game";
    }
    
}

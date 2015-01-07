
package prosjekt.kontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import prosjekt.Ui.Loginform;
import prosjekt.Ui.Registreringform;



@Controller
@RequestMapping("/")
public class registrerKontroller {
    
    @RequestMapping(value = "/registrer")
    public String showForm(@ModelAttribute Registreringform registreringform){
        return "registrer";
    }
    
    @RequestMapping(value = "/send")
    public String showForm(@ModelAttribute Loginform loginform){
        return "login";
    }
    
}


package prosjekt.kontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import prosjekt.Ui.Loginform;



@Controller
public class loginKontroller {
    
    @RequestMapping(value = "/")
    public String showForm(@ModelAttribute Loginform loginform){
        return "login";
    }
    
    @RequestMapping (value = "/Log inn")
    public String login () {
        return "Hovedside";
    }
    
}

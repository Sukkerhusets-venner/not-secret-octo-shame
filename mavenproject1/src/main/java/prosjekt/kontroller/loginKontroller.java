
package prosjekt.kontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import prosjekt.Ui.Loginform;



@Controller
@RequestMapping("/")
public class loginKontroller {
    
    @RequestMapping(method = RequestMethod.GET)
    public String showForm(@ModelAttribute Loginform loginform){
        return "login";
    }
    
}

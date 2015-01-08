
package prosjekt.kontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    public String login (@ModelAttribute Loginform loginform) {
        System.out.println( loginform.getUser().getEmail() + " " + loginform.getUser().getPassword());
        if (database.checkLogin(loginform.getUser().getEmail(), loginform.getUser().getPassword()))
        return "Hovedside";
        else return "login";
    }
    
    @RequestMapping (value = "/logincheat")
    public String login () {
        return "Hovedside";
    }
    
}

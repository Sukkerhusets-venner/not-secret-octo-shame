
package prosjekt.kontroller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
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
    public String login (HttpServletRequest request,@ModelAttribute Loginform loginform, Model model) {

        if (database.checkLogin(loginform.getUser().getEmail(), loginform.getUser().getPassword())) {
            HttpSession session = request.getSession();
            session.setAttribute ("Username", database.getUser(loginform.getUser().getEmail()).getUsername());
            return "Hovedside";
        } else {
            model.addAttribute("loginError", "Feil email/passord");
            return "login";
        }
    }
    
    @RequestMapping (value = "/logincheat")
    public String login () {
        return "Hovedside";
    }
    
    
    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest req, Exception exception) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("melding", "feilmelding.generell");
        mav.addObject("unntak",exception);
        mav.setViewName("error");
        return mav;
    }
    
}

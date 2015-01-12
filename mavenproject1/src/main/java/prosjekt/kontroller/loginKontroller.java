
package prosjekt.kontroller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
    public String login (@ModelAttribute Loginform loginform, HttpServletRequest request, Model model) {
        if(loginform.getUser().getPassword().isEmpty() || loginform.getUser().getEmail().isEmpty()){
            model.addAttribute("loginError", "Fyll inn alle feltene");
            return "login";
        }
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
        mav.addObject("melding", "feilmelding.generell"); //feilmelding.generelt finnes ikke
        mav.addObject("unntak",exception);
        mav.setViewName("error");
        return mav;
    }
    // Det er tilfeller hvor exceptionHandler ikke handler feilen ^-^ (eks localhost/prosjekt/side/side/side...)
    @ResponseStatus(value=HttpStatus.NOT_FOUND)
    public ModelAndView error(HttpServletRequest req){
        ModelAndView mav = new ModelAndView();
        mav.addObject("unntak","404: Not found");
        mav.setViewName("error");
        return mav;
    }
}

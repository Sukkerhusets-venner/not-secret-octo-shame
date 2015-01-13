
package prosjekt.kontroller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import prosjekt.Ui.EditUser;
import prosjekt.Ui.Loginform;

/**
 *
 * Slettes senere
 */
@Controller
public class testKontroller {
    
    
    @RequestMapping("/snake")
    public String snake(){
        return "snake";
    }
    @RequestMapping("/taskTester*")
    public String test(){
        return "taskTester";
    }

}

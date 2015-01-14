
package prosjekt.kontroller;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @RequestMapping("/testError*") // kaster en feil for å skjekker om vi fanger den
    public String throwError(HttpServletResponse response) throws IOException{
        if(true){
            throw new IOException("feil");
        }
        return "index";
    }
    @RequestMapping (value = "/logincheat")
    public String login () {
        return "Hovedside";
    }
    @RequestMapping(value="/mk*")
    public String changeMeny(HttpServletRequest req, Model m)throws IOException{
        String mapping = req.getServletPath();
        try{
            mapping = mapping.substring(3,4);
            switch(mapping){
                case "1": m.addAttribute("meny", 1); break;
                case "2": m.addAttribute("meny", 2); break;
                case "3": m.addAttribute("meny", 3); break;
            }
        }catch(Exception e){}
        
        return "Hovedside";
    }
}

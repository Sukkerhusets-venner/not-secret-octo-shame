
package prosjekt.kontroller;

import org.springframework.stereotype.Controller;
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
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package prosjekt.kontroller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;
import prosjekt.Ui.Assignment;
import prosjekt.database.DatabaseConnection;

/**
 *
 * @author balder
 */
@Controller
@SessionAttributes({"assignment"})
public class gameKontroller {
    
    @Autowired
    private DatabaseConnection database;
    
    @ModelAttribute("assignment")
    public Assignment makeAssignment(){
        return new Assignment();
    }
    
    @RequestMapping (value = "game")
    public String game (@ModelAttribute(value="assignment") Assignment assignment, Model model) {
        assignment.setAllTasks(database.getTasks(1));
        return "game";
    }
    
    @RequestMapping (value = "nesteOppgave")
    public String nesteOppg (@ModelAttribute(value="assignment") Assignment assignment, WebRequest request, Model model) {
        int tasknr = assignment.nextTask();
        if(tasknr != -1){
            return "game";
        } else {
            request.removeAttribute("assignment", WebRequest.SCOPE_SESSION);
            model.addAttribute("assignment", makeAssignment());
            return "Hovedside";
        }
    }
}

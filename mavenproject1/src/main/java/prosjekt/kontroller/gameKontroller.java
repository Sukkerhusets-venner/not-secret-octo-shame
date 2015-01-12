/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package prosjekt.kontroller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import prosjekt.Ui.Assignment;

/**
 *
 * @author balder
 */
@Controller
public class gameKontroller {
    @RequestMapping (value = "game")
    public String game (@ModelAttribute Assignment assignment, HttpServletRequest request, Model model) {
        return "game";
    }
}

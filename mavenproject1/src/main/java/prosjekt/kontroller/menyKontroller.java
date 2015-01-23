/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prosjekt.kontroller;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;
import prosjekt.Domene.ScoreProfile;
import prosjekt.Domene.User;
import prosjekt.Domene.UserScore;
import prosjekt.Domene.UserScoreOverview;
import prosjekt.Ui.Editform;
import prosjekt.Ui.Loginform;
import prosjekt.annet.searchHelper;
import prosjekt.database.DatabaseConnection;

@Controller
@SessionAttributes({"loginform"})
public class menyKontroller {
    @Autowired
    private DatabaseConnection database;    
    
    
    @RequestMapping (value = "/godkjentliste*")
    public String getGodkjentListe(HttpServletRequest req, Model model)throws Exception{
        HttpSession session = req.getSession();
        try{
            User bruker = (User)session.getAttribute("currentUser");
            if(bruker.getId() < 1){
                return "login";
            }
        }catch(Exception e){
            return "login";
        }
        
        searchHelper sorter = new searchHelper();
        ArrayList<UserScoreOverview> godkjentListe = new ArrayList<UserScoreOverview>();
        String sokt =  req.getQueryString();
        try{
            godkjentListe = database.getUserScoreOverview();
        }catch(Exception e){
            throw e;
        }
        if(sokt == null || sokt.equals("")){
            model.addAttribute("sokt", "Intet søk");
        }else{
            model.addAttribute("sokt", sokt);
            godkjentListe = sorter.getSearch(sokt, godkjentListe, 9);
        }
        model.addAttribute("godkjentListe", godkjentListe);
        return "godkjentliste";
    }
        
    @RequestMapping(value = "/hovedside")
    public String showForm1(@ModelAttribute(value="loginform") Loginform loginform, WebRequest webReq, Editform editform, Model model, HttpServletRequest req, SessionStatus status){
        HttpSession session = req.getSession();
        try{
            User bruker = (User)session.getAttribute("currentUser");
            if(bruker.getId() >= 1){
                ArrayList<ScoreProfile> brukerScore = database.getScoreProfile(bruker, 10);
                model.addAttribute("brukerScore", brukerScore);
                if(brukerScore.size() > 9){
                    model.addAttribute("flereRes", true);
                }
                ArrayList<UserScore> hiScores = database.getHighScoreList();
                loginform.setHiScore(hiScores);
                return "Hovedside";
            }else{
                model.addAttribute("loggedIn", false);
                return "login";
            }
        }catch(Exception e){
            model.addAttribute("loggedIn", false);
            return "login";
        }
    }
    
    @RequestMapping("fullGodkjentListe")
    public String fullGodkjentListe(HttpServletRequest req, Model model){
        HttpSession session = req.getSession();
        try{
            User bruker = (User)session.getAttribute("currentUser");
            if(bruker.getId() >= 1){
                ArrayList<UserScoreOverview> godkjentListe = database.getUserScoreOverview();
                model.addAttribute("godkjentListe", godkjentListe);
                model.addAttribute("sokt", "");
                model.addAttribute("fulListe", true);
                return "godkjentliste";
            }
        }catch(Exception e){
            return "error";
        }
        return "login";
    }
    @RequestMapping("alleBrukerRes")
    public String alleBrukerRes(HttpServletRequest req, Model model){
        HttpSession session = req.getSession();
        try{
            User bruker = (User)session.getAttribute("currentUser");
            if(bruker.getId() >= 1){
                ArrayList<ScoreProfile> brukerScore = database.getScoreProfile(bruker, -1);
                model.addAttribute("brukerScore", brukerScore);
                model.addAttribute("fulListe", true);
                return "alleBrukerResultater";
            }
        }catch(Exception e){
            return "error";
        }
        return "login";
    }
    
      
}


package prosjekt.kontroller;

import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;
import prosjekt.Domene.User;
import prosjekt.Domene.UserScore;
import prosjekt.Domene.UserScoreOverview;
import prosjekt.Ui.Editform;
import prosjekt.Ui.Loginform;
import prosjekt.annet.searchHelper;
import prosjekt.database.DatabaseConnection;
import prosjekt.mailservice.emailer;

@Controller
@SessionAttributes({"loginform"})
public class loginKontroller {
    
    @Autowired
    private DatabaseConnection database;    
    
    @Autowired
    private emailer emailer;
   
    @ModelAttribute("loginform")
    public Loginform makeLoginform(){
        return new Loginform();
    }
    @RequestMapping(value = "/*")
    public String showForm(@ModelAttribute Loginform loginform, HttpServletRequest req, Model model)throws Exception{
        HttpSession session = req.getSession();
        
        try{
            
            if(!database.checkConnection()){
                database.closeConnection();
                database = new DatabaseConnection();
                if(!database.checkConnection()){
                    model.addAttribute("notConnected", true);
                }
            }else{
                model.addAttribute("notConnected", false);
            }
        }catch(Exception e){
            throw e;
        }
        
        try{
            User bruker = (User)session.getAttribute("currentUser");
            if(bruker.getId() >= 1){
                model.addAttribute("loggedIn", true);
                model.addAttribute("currentUser", bruker.getUsername());
            }else{
                model.addAttribute("loggedIn", false);
            }
        }catch(Exception e){
            model.addAttribute("loggedIn", false);
        }
        return "login";
    }
    
    @RequestMapping (value ="/glemtPassord")
    public String glemtPassord (@ModelAttribute Loginform loginform, Model model) {
        if (loginform.getUser().getEmail().isEmpty()) {
            model.addAttribute("GlemtPassordError", "Email feltet kan ikke være tomt");
            return "login";
        }
        User user = database.getUser(loginform.getUser().getEmail());
        if (user == null) {
            model.addAttribute("GlemtPassordError", "Ingen bruker er tilknyttet til denne emailen");
            return "login";
        }
        String nyttPassord = database.getNewPassword(user);
        emailer.email (user.getEmail(), user.getUsername(), nyttPassord);
        return "login";
        
    }
    @RequestMapping (value = "Log inn")
    public String login (@ModelAttribute(value="loginform") Loginform loginform, HttpServletRequest request, Model model, Editform editform) {
        if(loginform.getUser().getPassword().isEmpty() || loginform.getUser().getEmail().isEmpty()){
            model.addAttribute("loginError", "Fyll inn alle feltene");
            return "login";
        }else if (database.checkLogin(loginform.getUser().getEmail(), loginform.getUser().getPassword())) {
            HttpSession session = request.getSession();
            
            User bruker = database.getUser(loginform.getUser().getEmail());
            
            session.setAttribute("currentUser", bruker);
            
            ArrayList<UserScore> hiScores = database.getHighScoreList();
            loginform.setHiScore(hiScores);
            loginform.setUser(bruker);
            
            return "Hovedside";
        } else {
            model.addAttribute("loginError", "Feil email/passord");
            return "login";
        }
    }
    
    @RequestMapping (value = "/godkjentliste*")
    public String getGodkjentListe(HttpServletRequest req, Model model)throws Exception{
        HttpSession session = req.getSession();
        try{
            User bruker = (User)session.getAttribute("currentUser");
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
    public String showForm1(@ModelAttribute(value="loginform") Loginform loginform, Editform editform, Model model, HttpServletRequest req){
        HttpSession session = req.getSession();
        try{
            User bruker = (User)session.getAttribute("currentUser");
            if(bruker.getId() >= 1){
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
    
    @RequestMapping (value = "logUt")
    public String meny(Editform editform, @ModelAttribute(value="loginform") Loginform loginform,HttpServletRequest req, WebRequest request, Model model) {
        loginform.setInGame(false);
        loginform = null;
        HttpSession session = req.getSession();
        session.invalidate();
        request.removeAttribute("loginform", WebRequest.SCOPE_SESSION);
        model.addAttribute("loginform", makeLoginform());
        if(!database.checkConnection()){
            model.addAttribute("notConnected", true); 
        }
        return "login";
    }
}

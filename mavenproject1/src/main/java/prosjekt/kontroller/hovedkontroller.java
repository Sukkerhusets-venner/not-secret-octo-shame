/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prosjekt.kontroller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class hovedkontroller {
   
    // brukes for å gjøre om de valgte personene fra tekst til Person-objekt
   /* @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        //binder.registerCustomEditor(Person.class, new PersonEditor(personService));
    } */
    
    @RequestMapping("/")
    public String visStartView(){
        return "index";
    }
    
    //Sørger for å gi en feilside når feil oppstår, merk at vi godt kunne hatt
    //flere slike feilhåndterere og håndtert ulike feil mer spesifikt
    //Denne metoden håndterer og "SQL-exceptions" om vi bruker 
    //PersonDatabaseJdbcTemplateRepositoryImpl som repository i stedet for 
    //PersonDatabaseRepositoryImpl. Dette avgjøres i springmvc.konfig.Konfigurasjon.respoistory().
    @ExceptionHandler(Exception.class)
    public ModelAndView handleError(HttpServletRequest req, Exception exception) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("melding", "feilmelding.generell");
        mav.addObject("unntak",exception);
        mav.setViewName("error");
        return mav;
    }
}
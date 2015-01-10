package prosjekt.lytter;

import javax.servlet.ServletContextEvent;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author joooog
 */
public class contextListener extends ContextLoaderListener {
    
    public contextListener(WebApplicationContext context){
        super(context);
    }
    
    @Override // Trigger = åpner program
    public void contextInitialized(ServletContextEvent event){
        System.gc();
    }
    @Override // Trigger = lukker program
    public void contextDestroyed(ServletContextEvent event){
        System.gc(); // rydder opp - vil ikke bruke unødig data
    }
    /*  
    * Her kan du legge til en lytter for events mot servlets.  
    *   - Veldig kjekt for å jobbe rundt spring
    private appListener implements SerlvetEventListener{

    }*/
}

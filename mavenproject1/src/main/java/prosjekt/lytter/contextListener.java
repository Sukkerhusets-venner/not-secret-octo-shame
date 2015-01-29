package prosjekt.lytter;

import javax.servlet.ServletContextEvent;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

public class contextListener extends ContextLoaderListener {
    
    public contextListener(WebApplicationContext context){
        super(context);
    }
    
    @Override
    public void contextInitialized(ServletContextEvent event){
        System.gc();
    }
    @Override 
    public void contextDestroyed(ServletContextEvent event){
        System.gc(); // rydder opp - vil ikke bruke unødig data
    }
}

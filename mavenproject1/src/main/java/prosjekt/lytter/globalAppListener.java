package prosjekt.lytter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.web.context.support.RequestHandledEvent;

/**
 *
 * @author joooog
 */
public class globalAppListener implements ApplicationListener {
    
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        
        System.gc(); // fjærner søppel
        if (event instanceof ContextRefreshedEvent) {
            //ApplicationContext applicationContext = ((ContextRefreshedEvent) event).getApplicationContext();
            // Pagestatus = refresh
        }else if(event instanceof ContextStoppedEvent){
            // Pagestatus = stopped -- (pause)
        }else if(event instanceof ContextClosedEvent){
            // Pagestatus = closed -- appen er lukket
        }else if(event instanceof RequestHandledEvent){
            // Pagestatus = request -- appen spør etter fil
        }
    }
}

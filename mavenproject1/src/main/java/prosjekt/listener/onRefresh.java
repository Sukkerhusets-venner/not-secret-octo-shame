
package prosjekt.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class onRefresh implements ApplicationListener<ContextRefreshedEvent>{

    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent ) {
             System.gc();
    }
}
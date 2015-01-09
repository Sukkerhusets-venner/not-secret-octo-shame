/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    
    @Override
    public void contextInitialized(ServletContextEvent event){
        //File folder = getFileFromURL();
        //folder.getAbsolutePath());
    
    }
/*
    private File getFileFromURL() {
        URL url = this.getClass().getClassLoader().getResource("/style.css");
        File file = null;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            file = new File(url.getPath());
        } finally {
            return file;
        }
    }*/
}

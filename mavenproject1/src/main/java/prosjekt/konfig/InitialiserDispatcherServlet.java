package prosjekt.konfig;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import prosjekt.lytter.contextListener;

public class InitialiserDispatcherServlet implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        WebApplicationContext context = getContext();
        servletContext.addListener(new contextListener(context)); // <-- viktig
        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true); // <-- fallback
        // Selve dispatcherservletten - implementasjoner i dispatcher må foregå før denne legges til
        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", dispatcherServlet); 
        dispatcher.setLoadOnStartup(1); // default = 0, resource = -1
        dispatcher.addMapping("/");
    }
    private AnnotationConfigWebApplicationContext getContext(){
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(Konfigurasjon.class);
        return context;
    }
}
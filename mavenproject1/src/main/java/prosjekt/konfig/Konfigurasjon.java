package prosjekt.konfig;

import java.util.Properties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import prosjekt.Ui.EditUser;
import prosjekt.Ui.Loginform;
import prosjekt.database.DatabaseConnection;
import prosjekt.mailservice.emailer;

@Configuration
@EnableWebMvc  // mvc annotation
@ComponentScan(basePackages = {"prosjekt.kontroller"}) // pakken der controllerne ligger
public class Konfigurasjon extends WebMvcConfigurationSupport {
    
    @Bean
    public InternalResourceViewResolver getInternalResourceView() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        return resolver;
    }
    
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setBasename("/WEB-INF/messages/messages");
        return source;
    }
    
    //Setter statiske ressurser som bilder/ css/ js osv.
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
                //.addResourceLocations("classpath:/Web Pages/resources/")
                //.setCachePeriod(31556926); //vi bruker gc
    }
    
    @Override
    @Bean
    public HandlerMapping resourceHandlerMapping() {
        AbstractHandlerMapping handlerMapping = (AbstractHandlerMapping) super.resourceHandlerMapping();
        // VIKTIG : For at ressurser skal bli public å ikke bare kopieres må vi initialisere en contextloader 
        handlerMapping.setOrder(-1); // setter loadpriority til -1 (lavt nummer = laster først, default = 0)
        ((SimpleUrlHandlerMapping) handlerMapping).setInterceptors(getInterceptors()); // bug fix
        return handlerMapping;
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    
    @Bean 
    public DatabaseConnection database() {
        return new DatabaseConnection();
    }
    
    @Bean
    public emailer emailer () {
        return new emailer();
    }
    @Bean
    public Loginform loginform(){
        return new Loginform();
    }
    
    @Bean(name="simpleMappingExceptionResolver")
    public SimpleMappingExceptionResolver createSimpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();

        Properties mappings = new Properties();
        mappings.setProperty("DatabaseException", "databaseError");

        r.setExceptionMappings(mappings);
        r.setDefaultErrorView("error"); 
        r.setExceptionAttribute("exception");
        return r;
    }
    
    
}

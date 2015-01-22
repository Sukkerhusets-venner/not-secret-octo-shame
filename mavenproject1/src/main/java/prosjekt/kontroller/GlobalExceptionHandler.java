package prosjekt.kontroller;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author joooog
 */
@ControllerAdvice
class GlobalExceptionHandler implements  HandlerExceptionResolver, Thread.UncaughtExceptionHandler{
    /*
    *   Fanger feil.
    *   For at en feil skal intreffe må den først bli kastet, dersom feilen intreffer i kompilering vil 
    *   altså feilen ikke bli kastet og vi vil ikke bli returnert til feilsiden.
    *   Dersom mottar en responsestatus vi ikke vil ha (som 404), men det ikke er kastet en feil, vil vi heller ikke 
    *   returneres til feilsiden (ingen feil = ingen funksjoner som blir kjørt)
    *   
    *   Viktig: Dispatcherservlet må kaste feil ved request status ulik 1xx eller 2xx for at vi skal fange den
    *   Dette gjøres ved metoden dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
    *
    *   Vi prøver først å spesifisere hvor feilen intreffer - og returnere passende verdier. 
    *   Hvis vi ikke finner feilen vil vi returnere mer generelle objekter (det blir da mye å lese igjennom)
    */
    public static final String DEFAULT_ERROR_VIEW = "error";
    
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IOException.class)
    public ModelAndView handleIOError(HttpServletRequest req, Exception e){
        return createMV(e, req.getRequestURL(), "404");
    }
    @ResponseStatus(value = org.springframework.http.HttpStatus.NOT_FOUND)
    @ExceptionHandler(RuntimeException.class)
    public ModelAndView handleMyError(HttpServletRequest req, Exception e){
        return createMV(e, req.getRequestURL(), "404");
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadDataException.class)
    @ResponseBody ModelAndView handleBadRequest(HttpServletRequest req, Exception e) {
         return createMV(e, req.getRequestURL(), "400");
    } 
    private class BadDataException extends Exception{
        private String message;
        public BadDataException(){
            this.message = "Did not find the page you were looking for";
        }
        public BadDataException(String m){
            StringBuilder s = new StringBuilder(m);
            s.append(". Did not find the page you were looking for");
            this.message = s.toString();
        }
        @Override
        public String getMessage(){
            return this.message;
        }
    }
    
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView handleConflict(HttpServletRequest req, Exception e){
        return createMV(e, req.getRequestURL(), "409");
    }
    @ExceptionHandler({SQLException.class,DataAccessException.class})
    public ModelAndView handleSQLException(HttpServletRequest req, Exception e){
        return createMV(e, req.getRequestURL(), "408");
    }
    
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)// Default 500
    public ModelAndView handleServerError(HttpServletRequest req, Exception e) throws Exception {
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){
            throw e;
        }
        return createMV(e, req.getRequestURL(), "500");
    }

    @Override //Vi fant ikke feilen spesifiert, vi skjekker med interface
    public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse response, Object o, Exception e) {
        return createMV(e, req.getRequestURL(), "500");
    }
    
    private ModelAndView createMV(Exception e, StringBuffer url, String status){
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", url);
        mav.addObject("status", status);
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }

    @Override //Vi fant ikke feilen i spring :-(
    public void uncaughtException(Thread t, Throwable e){}
    @ExceptionHandler(Throwable.class) // siste utvei
    public ModelAndView handleThrown(Throwable e, HttpServletRequest req) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("Thread", e);
        mav.addObject("url", req.getRequestURL());
        mav.addObject("status", "417");
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }
} 
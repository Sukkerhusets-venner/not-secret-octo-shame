package prosjekt.kontroller;

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
    * Thread.UncaughtExceptionHandler VS implements HandlerExceptionResolver{
    *   Global java error vs Spring error handling 
    *   Førstnevnte vil fange Alle feil generert i runtime, mens sistnevnte vil fange feil spesifisert i spring
    */
    public static final String DEFAULT_ERROR_VIEW = "error";
    
    /** Http Status **
    @ResponseStatus(HttpStatus.NOT_FOUND) //404
    public ModelAndView notFoundError(HttpServletRequest req){
        ModelAndView mav = new ModelAndView();
        mav.addObject("unntak","404: Not found");
        mav.setViewName("error");
        return mav;
    }
    @ResponseStatus(HttpStatus.CONFLICT) // ved Request konflikt
    public ModelAndView conflictError(HttpServletRequest req, Exception e){
        return createMV(e, req.getRequestURL());
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView badRequestError(HttpServletRequest req, Exception e){
        return createMV(e, req.getRequestURL());
    }*/
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadDataException.class)
    @ResponseBody ModelAndView handleBadRequest(HttpServletRequest req, Exception e) {
         return createMV(e, req.getRequestURL(), "404");
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
    
    
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Data integrity violation")
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView handleConflict(HttpServletRequest req, Exception e){
        return createMV(e, req.getRequestURL(), "409");
    }
    @ExceptionHandler({SQLException.class,DataAccessException.class})
    public ModelAndView handleSQLException(HttpServletRequest req, Exception e){
        return createMV(e, req.getRequestURL(), "Database");
    }
    // Default
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        // If the exception is annotated with @ResponseStatus rethrow it and let
        // the framework handle it
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){
            throw e;
        }

        // Otherwise setup and send the user to a default error-view.
        return createMV(e, req.getRequestURL(), "Exception");
    }

    @Override //Vi fant ikke feilen spesifiert, vi skjekker med interface
    public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse response, Object o, Exception e) {
        return createMV(e, req.getRequestURL(), "Java error");
    }
    
    private ModelAndView createMV(Exception e, StringBuffer url, String status){
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", url);
        mav.addObject("status", status);
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }

    @Override //Vi fant ikke feilen i spring, skjekker med tråden
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println(t.toString());
        System.out.println("Throwable: " + e.getMessage());
    }
}

package prosjekt.kontroller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author joooog
 */
@ControllerAdvice
class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler, HandlerExceptionResolver{
    /*
    * Thread.UncaughtExceptionHandler VS implements HandlerExceptionResolver{
    *   Global java error vs Spring error handling 
    *   Førstnevnte vil fange Alle feil generert i runtime, mens sistnevnte vil fange feil spesifisert i spring
    */
    public static final String DEFAULT_ERROR_VIEW = "error";
    
    @ResponseStatus(HttpStatus.CONFLICT) // ved Request konflikt
    public ModelAndView conflictError(HttpServletRequest req, Exception e){
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }
    
    // Ved data integritets feil - Injection!
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ModelAndView handleConflict(HttpServletRequest req, Exception e){
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }
    
    @ResponseStatus(value=HttpStatus.NOT_FOUND) //404
    public ModelAndView error(HttpServletRequest req){
        ModelAndView mav = new ModelAndView();
        mav.addObject("unntak","404: Not found");
        mav.setViewName("error");
        return mav;
    }
    
    // Default
    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        // If the exception is annotated with @ResponseStatus rethrow it and let
        // the framework handle it
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null){
            throw e;
        }

        // Otherwise setup and send the user to a default error-view.
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", req.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }

    @Override //Vi fant ikke feilen spesifiert, vi skjekker med interface
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("url", request.getRequestURL());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }

    @Override //Vi fant ikke feilen i spring, skjekker med tråden
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println(t.toString());
        System.out.println("Throwable: " + e.getMessage());
        redir(t,e);
    }
    // Må redirecte til feilside
    private ModelAndView redir(Thread t, Throwable e){
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e);
        mav.addObject("Thread", t.toString());
        mav.setViewName(DEFAULT_ERROR_VIEW);
        return mav;
    }
}

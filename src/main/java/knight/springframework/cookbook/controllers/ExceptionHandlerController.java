package knight.springframework.cookbook.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice // Gloabal exception handler method annot.
public class ExceptionHandlerController {



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormatError(Exception exception){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("400error");//v iew==webpage
        modelAndView.addObject("exception",exception);
        return modelAndView;
    }






}

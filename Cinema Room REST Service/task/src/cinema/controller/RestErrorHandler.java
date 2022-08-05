package cinema.controller;

import cinema.exception.PasswordIsIncorrectException;
import cinema.exception.SeatBoundsException;
import cinema.exception.TicketPurchaseException;
import cinema.exception.TokenIsIncorrectException;
import cinema.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestErrorHandler {

    @ExceptionHandler(TicketPurchaseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleBuyingException(TicketPurchaseException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(SeatBoundsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleBuyingException(SeatBoundsException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(PasswordIsIncorrectException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorResponse handleBuyingException(PasswordIsIncorrectException e) {
        return new ErrorResponse(e.getMessage());
    }

    @ExceptionHandler(TokenIsIncorrectException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleBuyingException(TokenIsIncorrectException e) {
        return new ErrorResponse(e.getMessage());
    }
}

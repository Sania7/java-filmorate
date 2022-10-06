package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exception.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.ExceptionResponse;



/*   400 — если ошибка валидации: ValidationException (BAD_REQUEST)
     404 — для всех ситуаций, если искомый объект не найден (NOT_FOUND)
     500 — если возникло исключение (Internal Server Error) */



@RestControllerAdvice
@Slf4j
public class ExceptionHandlerMain {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleObjectNotFoundException(final ObjectNotFoundException e) {
        log.info("404: {}",e.getMessage());
        return new ExceptionResponse(e.getMessage());
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleValidationException(final ValidationException e) {
        log.info("400: {}", e.getMessage());
        return new ExceptionResponse(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse handleThrowable(final Throwable e) {
        log.info("500: {}", e.getMessage());
        return new ExceptionResponse("Произошла ошибка" + e.getMessage());
    }
}

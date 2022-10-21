package ru.carnumber.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.carnumber.exception.AllNumbersUsedException;
import ru.carnumber.exception.EmptyCarNumberListException;

@ControllerAdvice
@Log4j2
public class ControllerAdvisor {

    @ExceptionHandler(AllNumbersUsedException.class)
    public ResponseEntity<Object> handleAllNumbersUsedException(AllNumbersUsedException ex) {
        log.error("All car numbers used", ex);
        return new ResponseEntity<>("Все номера автомобилей уже использованы", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmptyCarNumberListException.class)
    public ResponseEntity<Object> handleEmptyCarNumberListException(EmptyCarNumberListException ex) {
        log.error("Empty car number list", ex);
        return new ResponseEntity<>("Еще не сгенерирован ни один номер автомобиля", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

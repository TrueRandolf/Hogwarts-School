package ru.hogwarts.school.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class SchoolControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<SchoolError> notFoundException(NotFoundException e) {
        return new ResponseEntity<SchoolError>
                (new SchoolError(HttpStatus.NOT_FOUND.value() + "", e.getMessage())
                        , HttpStatus.NOT_FOUND);
    }

}
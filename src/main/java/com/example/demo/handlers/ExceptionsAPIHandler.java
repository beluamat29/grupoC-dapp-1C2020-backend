package com.example.demo.handlers;
import com.example.demo.model.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestControllerAdvice
@EnableWebMvc
public class ExceptionsAPIHandler {

    @ExceptionHandler({ NotFoundUserException.class})
    public ResponseEntity<String> notFoundUser(Exception exception) {
        return new ResponseEntity<String>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({NotFoundStoreException.class})
    public ResponseEntity<String> notFoundStore(Exception exception) {
        return new ResponseEntity<String>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({InvalidUsernameOrPasswordException.class})
    public ResponseEntity<String> invalidUsernameOrPassword(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NotAvailableUserNameException.class})
    public ResponseEntity<String> notAvailableUserName(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidStoreException.class})
    public ResponseEntity<String> invalidStore(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidAddressException.class})
    public ResponseEntity<String> invalidAddress(Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({RepeatedMerchandiseInStore.class})
    public ResponseEntity<String> repeatedMerchandise(Exception exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({InvalidMerchandiseException.class})
    public ResponseEntity<String> invalidMerchandise(Exception exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

//package FoodDelivery.User_service.exception;
//
//public class GlobalExceptionHandler {
//}
package FoodDelivery.User_service.exception;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.BadCredentialsException;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.web.bind.annotation.ControllerAdvice;

import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@ControllerAdvice

public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)

    public ResponseEntity<?> handleUserAlreadyExistsException(UserAlreadyExistsException ex, WebRequest request) {

        return ResponseEntity.badRequest().body(Map.of(

                "success", false,

                "message", ex.getMessage(),

                "timestamp", System.currentTimeMillis()

        ));

    }

    @ExceptionHandler(UsernameNotFoundException.class)

    public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException ex, WebRequest request) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(

                "success", false,

                "message", ex.getMessage(),

                "timestamp", System.currentTimeMillis()

        ));

    }

    @ExceptionHandler(BadCredentialsException.class)

    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(

                "success", false,

                "message", "Invalid credentials",

                "timestamp", System.currentTimeMillis()

        ));

    }

    @ExceptionHandler(Exception.class)

    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(

                "success", false,

                "message", "An unexpected error occurred",

                "timestamp", System.currentTimeMillis()

        ));

    }

}

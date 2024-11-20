package by.javaguru.identityservice.infrastructure.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@ControllerAdvice
@RequiredArgsConstructor
public class RestErrorHandler {

   // 400 Validator exceptions
   @ExceptionHandler(MethodArgumentNotValidException.class)
   public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
      Map<String, String> errors = new HashMap<>();

      ex.getBindingResult().getAllErrors().forEach((error) -> {
         String fieldName = ((FieldError) error).getField();
         String errorMessage = error.getDefaultMessage();
         errors.put(fieldName, errorMessage);
      });
      return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
   }

   // 400
   @ExceptionHandler(BadCredentialsException.class)
   public ResponseEntity<Void> handleAllOtherExceptions(BadCredentialsException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400
   }

   // 401
   @ExceptionHandler(AuthenticationException.class)
   public ResponseEntity<Map<String, String>> handleAllOtherExceptions(AuthenticationException ex) {
      return new ResponseEntity<>(Map.of("reason", ex.getMessage()), HttpStatus.UNAUTHORIZED); // 401
   }

   // 404
   @ExceptionHandler(NotFoundException.class)
   public ResponseEntity<Map<String, String>> handleNotFoundException(NotFoundException ex) {
      return new ResponseEntity<>(Map.of("message", ex.getMessage()), HttpStatus.NOT_FOUND); // 404
   }

   // 409
   @ExceptionHandler(AlreadyExistsException.class)
   public ResponseEntity<Object> handleAlreadyExistsException(AlreadyExistsException ex) {
      return new ResponseEntity<>(Map.of("message", ex.getMessage()), HttpStatus.CONFLICT); // 409
   }

   // 500
   @ExceptionHandler(Throwable.class)
   public ResponseEntity<Void> handleAllOtherExceptions(Throwable ex) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // 500
   }
}
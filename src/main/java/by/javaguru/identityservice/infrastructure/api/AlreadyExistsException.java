package by.javaguru.identityservice.infrastructure.api;

/**
 * @author Sergey Stol
 * 2024-11-20
 */
public class AlreadyExistsException extends RuntimeException {
   public AlreadyExistsException(String message) {
      super(message);
   }

   public AlreadyExistsException(String message, Throwable cause) {
      super(message, cause);
   }
}

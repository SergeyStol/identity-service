package by.javaguru.identityservice.infrastructure.api;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Sergey Stol
 * 2024-11-20
 */
public class WrongPasswordException extends AuthenticationException {
   public WrongPasswordException(String msg) {
      super(msg);
   }

   public WrongPasswordException() {
      super("Wrong password");
   }
}

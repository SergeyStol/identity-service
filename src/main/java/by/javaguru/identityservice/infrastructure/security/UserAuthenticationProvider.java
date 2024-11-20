package by.javaguru.identityservice.infrastructure.security;

import by.javaguru.identityservice.features.users.User;
import by.javaguru.identityservice.infrastructure.api.WrongPasswordException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author Sergey Stol
 * 2024-10-28
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserAuthenticationProvider implements AuthenticationProvider {

   private final UserDetailsService userDetailsService;
   private final PasswordEncoder encoder;

   @Override
   public Authentication authenticate(Authentication authentication) throws AuthenticationException {
      if (authentication == null
          || Strings.isBlank(authentication.getName())
          || authentication.getCredentials() == null) {
         throw new BadCredentialsException("Please, specify correct username/email and password");
      }

      UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
      String password = authentication.getCredentials().toString();

      try {
         if (encoder.matches(password, userDetails.getPassword())) {
            return UsernamePasswordAuthenticationToken
              .authenticated(userDetails, authentication, userDetails.getAuthorities());
         }
      } catch (Exception e) {
         log.warn("Failed login. User: {}. Reason: wrong password.", authentication.getName());
         throw new WrongPasswordException("Wrong password");
      }
      log.warn("Failed login. User: {}. Reason: wrong password.", authentication.getName());
      throw new WrongPasswordException("Wrong password");
   }

   /**
    * Performs authentication by user email and password
    * .
    * @param email the user email.
    * @param password the user password.
    * @return a fully authenticated object including credentials.
    * @throws AuthenticationException if authentication fails.
    */
   public User authenticate(String email, String password) throws AuthenticationException {
      if (Strings.isBlank(email) || Strings.isBlank(password)) {
         throw new BadCredentialsException("Please, specify correct username/email and password");
      }
      Authentication authentication = authenticate(new UsernamePasswordAuthenticationToken(email, password));
      return (User) authentication.getPrincipal();
   }

   @Override
   public boolean supports(Class<?> authentication) {
      return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
   }
}

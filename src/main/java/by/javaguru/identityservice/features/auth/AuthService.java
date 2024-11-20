package by.javaguru.identityservice.features.auth;

import by.javaguru.identityservice.features.jwt.JwtTokenService;
import by.javaguru.identityservice.features.users.NewUserDto;
import by.javaguru.identityservice.features.users.User;
import by.javaguru.identityservice.features.users.UserLoginDto;
import by.javaguru.identityservice.features.users.UserService;
import by.javaguru.identityservice.infrastructure.security.UserAuthenticationProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Sergey Stol
 * 2024-11-18
 */
@Slf4j
@Service
@AllArgsConstructor
public class AuthService  {
   private final JwtTokenService jwtTokenService;
   private final UserAuthenticationProvider authenticationProvider;
   private final UserService userService;

   public String registerNewUser(NewUserDto newUserDto) {
      User savedUser = userService.addUser(newUserDto);
      return jwtTokenService.getToken(savedUser.getName(), savedUser.getId(), savedUser.getEmail());
   }

   public String loginUser(UserLoginDto userLoginDto) {
      User user = authenticationProvider.authenticate(userLoginDto.email(), userLoginDto.password());
      return jwtTokenService.getToken(user.getUsername(), user.getId(), user.getEmail());
   }

}

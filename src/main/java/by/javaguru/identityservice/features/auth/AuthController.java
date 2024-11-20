package by.javaguru.identityservice.features.auth;

import by.javaguru.identityservice.features.users.NewUserDto;
import by.javaguru.identityservice.features.users.UserLoginDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Sergey Stol
 * 2024-11-18
 */
@RestController
@RequestMapping("/v1.0/auth")
@AllArgsConstructor
public class AuthController {
   private final AuthService service;

   // POST /v1.0/auth/register  -> 201 CREATED
   //                           -> 400 BAD_REQUEST
   //                           -> 409 CONFLICT
   @PostMapping("/register")
   @ResponseStatus(HttpStatus.CREATED)
   public String addUser(@RequestBody @Validated NewUserDto newUserDto) {
      return service.registerNewUser(newUserDto);
   }

   // POST /v1.0/auth/token  -> 200 OK
   //                        -> 400 BAD_REQUEST
   //                        -> 401 UNAUTHORIZED
   @PostMapping("/token")
   @ResponseStatus(HttpStatus.OK)
   public String getToken(@RequestBody @Validated UserLoginDto userLoginDto) {
      return service.loginUser(userLoginDto);
   }

}

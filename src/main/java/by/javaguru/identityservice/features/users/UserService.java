package by.javaguru.identityservice.features.users;

import by.javaguru.identityservice.infrastructure.api.AlreadyExistsException;
import by.javaguru.identityservice.infrastructure.api.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Sergey Stol
 * 2024-11-20
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
   private final UserRepository repository;
   private final UserMapper userMapper;
   private final PasswordEncoder passwordEncoder;

   public User addUser(NewUserDto newUserDto) {
      String password = newUserDto.password();
      User user = userMapper.toEntity(newUserDto, passwordEncoder.encode(password));
      try {
         return repository.save(user);
      } catch (DataIntegrityViolationException e) {
         if (e.getMessage().contains("users_email_key")) {
            throw new AlreadyExistsException("User with specified email already exists. email=" + newUserDto.email());
         }
         throw e;
      }
   }

   @Override
   public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      User user = repository.findByEmailIgnoreCase(email);
      if (user == null) {
         String msg = String.format("Can't find user with email/username = %s", email);
         log.warn(msg);
         throw new UsernameNotFoundException(msg);
      }
      return user;
   }
}

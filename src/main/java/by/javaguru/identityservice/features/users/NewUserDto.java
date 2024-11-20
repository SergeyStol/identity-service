package by.javaguru.identityservice.features.users;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

/**
 * @author Sergey Stol
 * 2024-11-18
 */
public record NewUserDto(
  @Length(max = 50, message = "The name is too long, the max number of symbols is 50")
  @Pattern(regexp = "^(?=.+$)[a-zA-Z0-9]+(?:[-' ][a-zA-Z0-9]+)*$", message = "Invalid name")
  @NotEmpty
  String name,

  @Length(max = 50, message = "The email is too long, the max number of symbols is 50")
  @Pattern(
    regexp = "^(?=.{6,}$)\\s*[a-zA-Z0-9]+([!\"#$%&'()*+,\\-./:;<=>?\\[\\]^_{}][a-zA-z0-9]+)*@(\\w+(-\\w+)?)(\\.\\w+-\\w+)*(\\.[a-z]{2,})+\\s*$",
    message = "Invalid email. Example of the correct variant: user@email.com "
  )
  @NotEmpty
  String email,

  @NotEmpty
  String password
) {
}

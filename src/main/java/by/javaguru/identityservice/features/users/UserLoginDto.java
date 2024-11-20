package by.javaguru.identityservice.features.users;

import jakarta.validation.constraints.NotEmpty;

/**
 * @author Sergey Stol
 * 2024-11-19
 */
public record UserLoginDto(
  @NotEmpty
  String email,
  @NotEmpty
  String password
) {
}

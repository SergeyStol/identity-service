package by.javaguru.identityservice.features.users;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Sergey Stol
 * 2024-11-18
 */
public interface UserRepository extends JpaRepository<User, Long> {
   User findByEmailIgnoreCase(String email);
}

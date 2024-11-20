package by.javaguru.identityservice.features.users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * @author Sergey Stol
 * 2024-11-18
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
@Getter
@Setter
public class User implements UserDetails {
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)
   @Column(name = "id")
   private Long id;
   @Column(name = "uuid", unique = true, nullable = false)
   private UUID uuid;
   @Column(name = "name")
   private String name;
   @Column(name = "email", unique = true, nullable = false)
   private String email;
   @Column(name = "password")
   private String password;

   @PrePersist
   void onSave() {
      if (uuid == null) {
         uuid = UUID.randomUUID();
      }
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return List.of();
   }

   @Override
   public String getUsername() {
      return email;
   }

}

package by.javaguru.identityservice.features.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.AeadAlgorithm;
import io.jsonwebtoken.security.KeyAlgorithm;
import io.jsonwebtoken.security.Password;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Sergey Stol
 * 2024-11-18
 */
@Service
@AllArgsConstructor
@Slf4j
public class JwtTokenService {
   private final Password password;

   public String getToken(String subject, Long userId, String userEmail) {
      KeyAlgorithm<Password, Password> alg = Jwts.KEY.PBES2_HS512_A256KW;
      AeadAlgorithm enc = Jwts.ENC.A256GCM;
      String jwtToken = Jwts.builder()
        .issuer("profiler-application")
        .subject(subject)
        .claim("userId", userId)
        .claim("userEmail", userEmail)
        .encryptWith(password, alg, enc)
        .compact();
      log.info("Generated new jwt token for user={}", userEmail);
      return jwtToken;
   }
}

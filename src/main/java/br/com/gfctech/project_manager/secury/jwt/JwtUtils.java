package br.com.gfctech.project_manager.secury.jwt;

import java.security.Key;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import br.com.gfctech.project_manager.service.UserDetailsImpl;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
	
	@Value("${projeto.jwtSecret}")
	private String jwtSecret;
	
	@Value("${projeto.jwtExpirationMs}")
	private int jwtExpirationMs;
	
	public String generateTokenFromUserDetailsImpl(UserDetailsImpl userDetail) {

		String role = userDetail.getAuthorities().stream()
            .findFirst()
            .map(GrantedAuthority::getAuthority)
            .orElse("USER");  // Se não tiver, assume como USER
		return Jwts.builder()
				.setSubject(userDetail.getUsername())
				.claim("id", userDetail.getId())
				.claim("name", userDetail.getName())
				.claim("email", userDetail.getEmail())
				.claim("role", role)  // 👈 Corrige o formato esperado
				.claim("joinDate", userDetail.getJoinDate())
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
				.signWith(getSigninKey(), SignatureAlgorithm.HS512).compact();
	}
	
	public Key getSigninKey() {
		SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
		return key;
	}
	
	public String getUsernameToken(String token) {
		return Jwts.parser().setSigningKey(getSigninKey()).build()
				.parseClaimsJws(token).getBody().getSubject();
	}
	
	public boolean validateJwtToken (String authToken) {
		try {
			Jwts.parser().setSigningKey(getSigninKey()).build().parseClaimsJws(authToken);
			return true;
		}catch(MalformedJwtException e) {
			System.out.println("Token Invalido" + e.getMessage());
		}catch(ExpiredJwtException e) {
			System.out.println("Token expirado" + e.getMessage());
		}
		catch(UnsupportedJwtException e) {
			System.out.println("Token não suportado" + e.getMessage());
		}
		catch(IllegalArgumentException e) {
			System.out.println("Token Argumento inválido" + e.getMessage());
		}
		
		return false;
		
	}

}

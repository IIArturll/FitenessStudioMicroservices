package it.academy.productservice.security;

import io.jsonwebtoken.*;
import it.academy.productservice.core.properties.JwtProperty;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {

    private final JwtProperty jwtProperty;

    public JwtTokenUtil(JwtProperty jwtProperty) {
        this.jwtProperty = jwtProperty;
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtProperty.getJwtSecret())
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public String getUserRole(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtProperty.getJwtSecret())
                .parseClaimsJws(token)
                .getBody();
        return claims.get("role").toString();
    }


    public String getFio(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtProperty.getJwtSecret())
                .parseClaimsJws(token)
                .getBody();
        return claims.get("fio").toString();
    }

    public String getUserUUID(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtProperty.getJwtSecret())
                .parseClaimsJws(token)
                .getBody();
        return claims.get("uuid").toString();
    }

    public Date getExpirationDate(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtProperty.getJwtSecret())
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtProperty.getJwtSecret()).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            //logger.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            //logger.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            //logger.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            //logger.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            //logger.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }
}

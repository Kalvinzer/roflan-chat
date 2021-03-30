package kvinz.roflanchat.jwt;

import io.jsonwebtoken.*;
import kvinz.roflanchat.controller.error_handler.custom_errors.InvalidTokenExeption;
import kvinz.roflanchat.domain.User;
import kvinz.roflanchat.service.UserService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.springframework.util.StringUtils.hasText;

@Component
@Log
public class JwtProvider {


    @Autowired
    private UserService userService;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String generateToken(String login) {
        Date date = Date.from(LocalDate.now().plusDays(15).atStartOfDay(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setSubject(login)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.severe("Token expired");
            throw new InvalidTokenExeption("Token expired");
        } catch (UnsupportedJwtException unsEx) {
            log.severe("Unsupported jwt");
            throw new InvalidTokenExeption("Unsupported jwt");
        } catch (MalformedJwtException mjEx) {
            log.severe("Malformed jwt");
            throw new InvalidTokenExeption("Malformed jwt");
        } catch (SignatureException sEx) {
            log.severe("Invalid signature");
            throw new InvalidTokenExeption("Invalid signature");
        } catch (Exception e) {
            log.severe("invalid token");
            throw new InvalidTokenExeption("invalid token");
        }
    }


    public User getLoginFromBearer(String bearer) {
            bearer = bearer.substring(7);
            Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(bearer).getBody();
            String userLogin = claims.getSubject();

        return (User) userService.loadUserByUsername(userLogin);
    }

    public User getLoginFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        String userLogin = claims.getSubject();
        return (User) userService.loadUserByUsername(userLogin);
    }
}

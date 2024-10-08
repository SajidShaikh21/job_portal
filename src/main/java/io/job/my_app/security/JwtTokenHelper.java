package io.job.my_app.security;

import io.job.my_app.Entity.User;
import io.job.my_app.repos.UserRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtTokenHelper {

    @Autowired
    private UserRepo userRepo;
    private String SECRET_KEY = "secret";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

 /*   public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }*/

    private String createToken(Map<String, Object> claims, String username) {

        return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

   /* public String generateToken(UserDetails userDetails, User user) {
        Map<String, Object> claims = new HashMap<>();
        String username = userDetails.getUsername();
  //      User byEmail = userRepo.findByEmail(username);
        claims.put("userId", user.getUserId());
        claims.put("roles", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","))); // Convert roles to comma-separated string
        return createToken(claims, userDetails.getUsername());
    }*/


    public String generateToken(UserDetails userDetails, User user) {
        Map<String, Object> claims = new HashMap<>();

        String username = userDetails.getUsername();

        claims.put("userId", user.getUserId());

        // Debugging: Print the authorities
        System.out.println("Authorities: " + userDetails.getAuthorities());

        String roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(",")); // Convert roles to comma-separated string

        // Debugging: Print the roles
        System.out.println("Roles: " + roles);

        claims.put("roles", roles);

        return createToken(claims, username);
    }
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}

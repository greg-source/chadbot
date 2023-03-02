package chadbot.security;

import chadbot.model.ChadUserDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenService.class);

    @Value("${jwt_key}")
    private String jwtKey;
    @Value("${jwt_expire}")
    private int jwtExpirationMs;

    @Autowired
    public ObjectMapper objectMapper;

    public String generateJwtToken(ChadUserDetails chadUserDetails) {
        return Jwts.builder()
                .setSubject(chadUserDetails.getUsername())
                .setIssuedAt(chadUserDetails.getIssuedAt())
                .setExpiration(chadUserDetails.getExpiration()) // IF EXPIRED GENERATE NEW
                .signWith(SignatureAlgorithm.HS512, jwtKey)
                .compact();
    }

    public ChadUserDetails getChadUserDetailsFromJwtToken(String token) {
        Claims body = Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(token).getBody();
        return new ChadUserDetails(body.getSubject(), body.getIssuedAt(), body.getExpiration());
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        } catch (SignatureException e) {
            logger.error("JWT signature does not match locally computed signature. JWT validity cannot be asserted and should not be trusted.");
        }

        return false;
    }

    private String convertChadUserDetailsToJson(ChadUserDetails chadUserDetails){
        try{
            return objectMapper.writeValueAsString(chadUserDetails);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json is not correct");
        }
    }
}

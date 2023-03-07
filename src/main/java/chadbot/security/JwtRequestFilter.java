package chadbot.security;

import chadbot.model.ChadUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static chadbot.security.authorities.UserGrantedAuthority.userGrantedAuthorityList;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    public JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");
        String jwtToken = null;
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.split(" ")[1];
            if (jwtTokenService.validateJwtToken(jwtToken)){
                ChadUserDetails chadUserDetails = jwtTokenService.getChadUserDetailsFromJwtToken(jwtToken);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(chadUserDetails, null, userGrantedAuthorityList());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
            else {
                logger.warn("JWT token is invalid");
            }

        } else {
            logger.warn(String.format("JWT Token does not begin with Bearer String, user " +  request.getRemoteHost() + request.getRemotePort()));
        }
        chain.doFilter(request, response);
    }
}

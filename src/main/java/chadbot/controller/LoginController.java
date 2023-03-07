package chadbot.controller;

import chadbot.model.ChadUserDetails;
import chadbot.security.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    public JwtTokenService jwtTokenService;

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        ChadUserDetails chadUserDetails = (ChadUserDetails) authenticationManager.authenticate(usernamePasswordAuthenticationToken).getPrincipal();
        return jwtTokenService.generateJwtToken(chadUserDetails);
    }

    @GetMapping("/hi")
    public String log(){
        return "jwtTokenService.jwtKey + jwtTokenService.jwtExpirationMs";

    }
}

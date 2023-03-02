package chadbot.security;

import chadbot.model.ChadUserDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {JwtTokenService.class})
public class JwtTokenServiceTest {
    @Autowired
    public JwtTokenService jwtTokenService;

    @MockBean
    public ObjectMapper objectMapper;

    @Test
    public void generateJwtToken() throws JsonProcessingException {
        ChadUserDetails chadUserDetails = new ChadUserDetails("chad", "[PROTECTED]",
                new Date(1678128008), new Date(1678128020));
        assertEquals(jwtTokenService.generateJwtToken(chadUserDetails), "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjaGFkIiwiaWF0IjoxNjc4MTI4LCJleHAiOjE2NzgxMjh9.cplFADRsG0Bz-C_B-a1Ze2TBVfRUzHC-Y4C3Sg24xfOfvkm76Nm3MdTqI0nZo2KAVzcsJgN9RDtdLO6uU06fdA");
    }

    @Test
    public void generateJwtTokens() throws JsonProcessingException {
        System.out.println(245);
    }
}
package chadbot.security.authorities;

import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public class UserGrantedAuthority implements GrantedAuthority {
    @Override
    public String getAuthority() {
        return "USER";
    }

    public static List<GrantedAuthority> userGrantedAuthorityList(){
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        grantedAuthorities.add(new UserGrantedAuthority());
        return grantedAuthorities;
    }
}

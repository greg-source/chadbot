package chadbot.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

public class ChadUserDetails implements UserDetails {

    private String username;
    private String password;
    private Date issuedAt;
    private Date expiration;

    public ChadUserDetails(String username, String password, Date issuedAt, Date expiration) {
        this.username = username;
        this.password = password;
        this.issuedAt = issuedAt;
        this.expiration = expiration;
    }

    public ChadUserDetails(String username, Date issuedAt, Date expiration) {
        this.username = username;
        this.issuedAt = issuedAt;
        this.expiration = expiration;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public Date getExpiration() {
        return this.expiration;
    }

    public Date getIssuedAt() {
        return this.issuedAt;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return expiration.compareTo(issuedAt) > 0;
    }

    @Override
    public boolean isEnabled() {
        return isAccountNonExpired();
    }

}

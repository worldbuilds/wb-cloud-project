package org.worldbuild.cloud.auth.JPA.entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Setter
@Getter
@Entity
@Table(name = "user_info")
public class UserInfo implements UserDetails {

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String secretKey;
    private boolean enabled=true;
    private boolean googleAuth=true;
    private boolean accountNonLocked=true;
    private boolean accountNonExpired=true;
    private boolean credentialsNonExpired=true;

    public UserInfo(){
        super();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }
}

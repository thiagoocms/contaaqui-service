package com.thiagoocms.contaaqui.domain.auth;

import com.thiagoocms.contaaqui.contaaqui_core.domain.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class Auth extends User implements UserDetails {

    public Auth() {
    }

    public Auth(User user) {
        this.setId(user.getId());
        this.setName(user.getName());
        this.setDocumentNumber(user.getDocumentNumber());
        this.setDocumentType(user.getDocumentType());
        this.setLogin(user.getLogin());
        this.setPassword(user.getPassword());
        this.setEmail(user.getEmail());
        this.setProfile(user.getProfile());
        this.setDeleted(user.isDeleted());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return getLogin();
    }

}

package com.thiagoocms.contaaqui.service;

import com.thiagoocms.contaaqui.common.MessageService;
import com.thiagoocms.contaaqui.contaaqui_core.dto.auth.AuthLoginDTO;
import com.thiagoocms.contaaqui.contaaqui_core.dto.auth.AuthenticatedDTO;
import com.thiagoocms.contaaqui.contaaqui_core.exception.BadRequestException;
import com.thiagoocms.contaaqui.domain.auth.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service

public class AuthService {

    private final TokenService tokenService;
    private final AuthenticationManager manager;
    private final MessageService messageService;

    @Autowired
    public AuthService(TokenService tokenService, AuthenticationManager manager, MessageService messageService) {
        this.tokenService = tokenService;
        this.manager = manager;
        this.messageService = messageService;
    }

    public AuthenticatedDTO auth(AuthLoginDTO dto) {
        String[] decrypt = new String(Base64.getDecoder().decode(dto.getAuth())).split(":");
        if (decrypt.length < 2) {
            throw new BadRequestException();
        }
        var login = decrypt[0];
        var password = decrypt[1];
        if ((login == null || login.isEmpty()) || (password == null || password.isEmpty())) {
            throw new BadRequestException(messageService.getMessage("error.login.or.password"));
        }
        var auth = new UsernamePasswordAuthenticationToken(decrypt[0], decrypt[1]);
        Authentication authentication = manager.authenticate(auth);
        String token = tokenService.generateToken((Auth) authentication.getPrincipal());
        return new AuthenticatedDTO(token);
    }

}
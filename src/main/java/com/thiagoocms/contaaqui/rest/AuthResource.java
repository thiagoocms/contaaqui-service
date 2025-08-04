package com.thiagoocms.contaaqui.rest;

import com.thiagoocms.contaaqui.contaaqui_core.dto.auth.AuthLoginDTO;
import com.thiagoocms.contaaqui.contaaqui_core.dto.auth.AuthenticatedDTO;
import com.thiagoocms.contaaqui.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthResource {

    private final AuthService authService;

    @Autowired
    public AuthResource(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<AuthenticatedDTO> auth(@RequestBody AuthLoginDTO dto) {

        var token = authService.auth(dto);

        return ResponseEntity
                .ok(token);
    }
}

package com.thiagoocms.contaaqui.rest;

import com.thiagoocms.contaaqui.common.AbstractCrudResource;
import com.thiagoocms.contaaqui.contaaqui_core.dto.user.UserDTO;
import com.thiagoocms.contaaqui.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserResource extends AbstractCrudResource<Long, UserDTO> {

    @Autowired
    protected UserResource(UserService service) {
        super(service);
    }
}

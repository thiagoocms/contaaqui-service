package com.thiagoocms.contaaqui.service;

import com.thiagoocms.contaaqui.common.AbstractCrudService;
import com.thiagoocms.contaaqui.common.MessageService;
import com.thiagoocms.contaaqui.contaaqui_core.domain.user.User;
import com.thiagoocms.contaaqui.contaaqui_core.dto.user.UserDTO;
import com.thiagoocms.contaaqui.mapper.UserMapper;
import com.thiagoocms.contaaqui.contaaqui_core.repository.UserRepository;
import com.thiagoocms.contaaqui.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractCrudService<User, Long, UserDTO> {

    @Autowired
    protected UserService(UserRepository repository, UserMapper mapper, UserValidation validation, MessageService messageService) {
        super(repository, mapper, validation, messageService);
        setMessageNotFound("error.user.not.found");
    }
}

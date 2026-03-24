package com.thiagoocms.contaaqui.service;

import com.thiagoocms.contaaqui.common.AbstractCrudService;
import com.thiagoocms.contaaqui.common.MessageService;
import com.thiagoocms.contaaqui.contaaqui_core.domain.user.User;
import com.thiagoocms.contaaqui.contaaqui_core.dto.user.UserDTO;
import com.thiagoocms.contaaqui.contaaqui_core.exception.ResourceNotFoundException;
import com.thiagoocms.contaaqui.contaaqui_core.repository.UserRepository;
import com.thiagoocms.contaaqui.contaaqui_core.search.UserSpecification;
import com.thiagoocms.contaaqui.domain.auth.Auth;
import com.thiagoocms.contaaqui.mapper.UserMapper;
import com.thiagoocms.contaaqui.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractCrudService<User, Long, UserDTO, UserDTO> implements UserDetailsService {


    private UserRepository userRepository;
    @Autowired
    protected UserService(UserRepository repository, UserMapper mapper, UserValidation validation, MessageService messageService, UserSpecification userSpecification) {
        super(repository, mapper, validation, messageService, userSpecification);
        setMessageNotFound("error.user.not.found");
        userRepository = repository;
    }

    @Override
    public Auth loadUserByUsername(String username) throws UsernameNotFoundException {

        var user = userRepository.findByLoginAndDeletedIsFalse(username);
        if (user == null) {
            throw new ResourceNotFoundException(messageService.getMessage("error.user.not.found"));
        }

        return new Auth(user);
    }
}

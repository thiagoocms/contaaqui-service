package com.thiagoocms.contaaqui.mapper;

import com.thiagoocms.contaaqui.common.AbstractMapper;
import com.thiagoocms.contaaqui.contaaqui_core.domain.user.User;
import com.thiagoocms.contaaqui.contaaqui_core.dto.user.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends AbstractMapper<User, UserDTO> {

    @Override
    protected User toEntity(UserDTO source) {
        User user = new User();
        user.setId(source.id());
        user.setName(source.name());
        user.setEmail(source.email());
        user.setLogin(source.login());
        user.setPassword(source.password());
        user.setDocumentType(source.documentType());
        user.setDocumentNumber(source.documentNumber());
        user.setProfile(source.profile());
        user.setDeleted(source.deleted());
        return user;
    }

    @Override
    protected UserDTO toModel(User source) {
        return new UserDTO(
                source.getId(),
                source.getName(),
                source.getDocumentNumber(),
                source.getDocumentType(),
                source.getLogin(),
                source.getPassword(),
                source.getEmail(),
                source.getProfile(),
                source.isDeleted()
        );
    }

    @Override
    public void merge(User source, User target) {
        target.setName(source.getName());
        target.setEmail(source.getEmail());
        target.setLogin(source.getLogin());
        target.setPassword(source.getPassword());
        target.setDocumentType(source.getDocumentType());
        target.setDocumentNumber(source.getDocumentNumber());
        target.setProfile(source.getProfile());
        target.setDeleted(source.isDeleted());
    }
}

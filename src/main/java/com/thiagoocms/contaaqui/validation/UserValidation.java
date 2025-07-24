package com.thiagoocms.contaaqui.validation;

import com.thiagoocms.contaaqui.common.AbstractCrudValidation;
import com.thiagoocms.contaaqui.contaaqui_core.domain.user.User;
import com.thiagoocms.contaaqui.contaaqui_core.exception.BadRequestException;
import com.thiagoocms.contaaqui.mapper.UserMapper;
import com.thiagoocms.contaaqui.contaaqui_core.repository.UserRepository;
import com.thiagoocms.contaaqui.common.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserValidation extends AbstractCrudValidation<User, Long> {

    private final UserMapper mapper;

    @Autowired
    public UserValidation(UserMapper mapper, UserRepository repository, MessageService messageService) {
        super(repository, messageService);
        setMessageNotFound("error.user.not.found");
        this.mapper = mapper;
    }

    @Override
    protected void checkOwnerFieldsToCreate(User entity) {
        entity.setId(null);
        entity.setDeleted(false);
    }

    @Override
    protected User checkUpdateConsistence(Long aLong, User toUpdateEntity) {
        if (Objects.isNull(aLong) || Objects.isNull(toUpdateEntity.getId())) {
            throw new BadRequestException(messageService.getMessage("error.generic.bad.request"));
        }

        if (aLong.compareTo(toUpdateEntity.getId()) != 0) {
            throw new BadRequestException(messageService.getMessage("error.generic.bad.request"));
        }

        var persistedEntity = checkExist(aLong);

        mapper.merge(toUpdateEntity, persistedEntity);

        return persistedEntity;
    }
}

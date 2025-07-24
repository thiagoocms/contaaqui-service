package com.thiagoocms.contaaqui.validation;

import com.thiagoocms.contaaqui.common.AbstractCrudValidation;
import com.thiagoocms.contaaqui.contaaqui_core.domain.category.Category;
import com.thiagoocms.contaaqui.contaaqui_core.exception.BadRequestException;
import com.thiagoocms.contaaqui.mapper.CategoryMapper;
import com.thiagoocms.contaaqui.contaaqui_core.repository.CategoryRepository;
import com.thiagoocms.contaaqui.common.MessageService;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CategoryValidation extends AbstractCrudValidation<Category, Long> {

    private final UserValidation userValidation;
    private final CategoryMapper mapper;

    public CategoryValidation(CategoryRepository repository, MessageService messageService, UserValidation userValidation, CategoryMapper mapper) {
        super(repository, messageService);
        setMessageNotFound("error.category.not.found");
        this.userValidation = userValidation;
        this.mapper = mapper;
    }


    @Override
    protected void checkOwnerFieldsToCreate(Category entity) {
        entity.setId(null);
        entity.setDeleted(false);
    }

    @Override
    protected Category checkUpdateConsistence(Long aLong, Category toUpdateEntity) {
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

    @Override
    public Category checkRelations(Category toPersistEntity) {
        checkUser(toPersistEntity);
        return toPersistEntity;
    }

    private Category checkUser(Category entity) {

        if (Objects.isNull(entity.getUser())) {
            return entity;
        }

        if (Objects.isNull(entity.getUser().getId())) {
            entity.setUser(null);
            return entity;
        }

        var user = this.userValidation.checkExist(entity.getUser().getId());

        entity.setUser(user);

        return entity;
    }
}

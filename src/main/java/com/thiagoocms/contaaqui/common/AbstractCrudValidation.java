package com.thiagoocms.contaaqui.common;

import com.thiagoocms.contaaqui.contaaqui_core.common.AbstractJpaRepository;
import com.thiagoocms.contaaqui.contaaqui_core.exception.ResourceNotFoundException;

import java.io.Serializable;

public abstract class AbstractCrudValidation<E, ID extends Serializable> {

    private String messageNotFound = "error.entity.not.found";

    protected final AbstractJpaRepository<E, ID> repository;
    protected final MessageService messageService;

    protected AbstractCrudValidation(AbstractJpaRepository<E, ID> repository, MessageService messageService) {
        this.repository = repository;
        this.messageService = messageService;
    }

    protected abstract void checkOwnerFieldsToCreate(E entity);

    protected abstract E checkUpdateConsistence(ID id, E toUpdateEntity);

    protected void checkMandatoryFields(E entity) {
    }

    protected E checkRelations(E toPersistEntity) {
        return toPersistEntity;
    }

    public E checkExist(ID id) {
        var entityOpt = repository.findFirstByIdAndDeletedIsFalse(id);
        return entityOpt.orElseThrow(() -> new ResourceNotFoundException(messageService.getMessage(messageNotFound)));
    }

    public void setMessageNotFound(String messageNotFound) {
        this.messageNotFound = messageNotFound;
    }
}

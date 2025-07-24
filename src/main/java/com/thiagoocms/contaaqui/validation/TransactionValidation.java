package com.thiagoocms.contaaqui.validation;

import com.thiagoocms.contaaqui.common.AbstractCrudValidation;
import com.thiagoocms.contaaqui.common.MessageService;
import com.thiagoocms.contaaqui.contaaqui_core.domain.transaction.Transaction;
import com.thiagoocms.contaaqui.contaaqui_core.exception.BadRequestException;
import com.thiagoocms.contaaqui.contaaqui_core.repository.TransactionRepository;
import com.thiagoocms.contaaqui.mapper.TransactionMapper;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TransactionValidation extends AbstractCrudValidation<Transaction, Long> {

    private final TransactionMapper mapper;
    private final WalletValidation walletValidation;
    private final CategoryValidation categoryValidation;

    public TransactionValidation(TransactionRepository repository, MessageService messageService, TransactionMapper mapper, WalletValidation walletValidation, CategoryValidation categoryValidation) {
        super(repository, messageService);
        setMessageNotFound("error.transaction.not.found");
        this.mapper = mapper;
        this.walletValidation = walletValidation;
        this.categoryValidation = categoryValidation;
    }


    @Override
    protected void checkOwnerFieldsToCreate(Transaction entity) {
        entity.setId(null);
        entity.setDeleted(false);
    }

    @Override
    protected Transaction checkUpdateConsistence(Long aLong, Transaction toUpdateEntity) {
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
    public Transaction checkRelations(Transaction toPersistEntity) {
        checkWallet(toPersistEntity);
        checkCategory(toPersistEntity);
        return toPersistEntity;
    }

    private Transaction checkWallet(Transaction entity) {

        if (Objects.isNull(entity.getWallet())) {
            return entity;
        }

        if (Objects.isNull(entity.getWallet().getId())) {
            entity.setWallet(null);
            return entity;
        }

        var rel = this.walletValidation.checkExist(entity.getWallet().getId());

        entity.setWallet(rel);

        return entity;
    }

    private Transaction checkCategory(Transaction entity) {

        if (Objects.isNull(entity.getCategory())) {
            return entity;
        }

        if (Objects.isNull(entity.getCategory().getId())) {
            entity.setCategory(null);
            return entity;
        }

        var rel = this.categoryValidation.checkExist(entity.getCategory().getId());

        entity.setCategory(rel);

        return entity;
    }
}

package com.thiagoocms.contaaqui.validation;

import com.thiagoocms.contaaqui.common.AbstractCrudValidation;
import com.thiagoocms.contaaqui.contaaqui_core.domain.wallet.Wallet;
import com.thiagoocms.contaaqui.contaaqui_core.exception.BadRequestException;
import com.thiagoocms.contaaqui.mapper.WalletMapper;
import com.thiagoocms.contaaqui.contaaqui_core.repository.WalletRepository;
import com.thiagoocms.contaaqui.common.MessageService;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class WalletValidation extends AbstractCrudValidation<Wallet, Long> {

    private final UserValidation userValidation;
    private final WalletMapper mapper;

    public WalletValidation(WalletRepository repository, MessageService messageService, UserValidation userValidation, WalletMapper mapper) {
        super(repository, messageService);
        setMessageNotFound("error.wallet.not.found");
        this.userValidation = userValidation;
        this.mapper = mapper;
    }

    @Override
    protected void checkOwnerFieldsToCreate(Wallet entity) {
        entity.setId(null);
        entity.setDeleted(false);
    }

    @Override
    protected Wallet checkUpdateConsistence(Long aLong, Wallet toUpdateEntity) {
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
    public Wallet checkRelations(Wallet toPersistEntity) {
        checkUser(toPersistEntity);
        return toPersistEntity;
    }

    private Wallet checkUser(Wallet entity) {

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

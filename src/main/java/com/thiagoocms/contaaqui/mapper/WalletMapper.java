package com.thiagoocms.contaaqui.mapper;

import com.thiagoocms.contaaqui.common.AbstractMapper;
import com.thiagoocms.contaaqui.contaaqui_core.domain.user.User;
import com.thiagoocms.contaaqui.contaaqui_core.domain.wallet.Wallet;
import com.thiagoocms.contaaqui.contaaqui_core.dto.wallet.WalletDTO;
import org.springframework.stereotype.Component;

@Component
public class WalletMapper extends AbstractMapper<Wallet, WalletDTO> {

    @Override
    protected Wallet toEntity(WalletDTO source) {
        Wallet target = new Wallet();
        target.setId(source.id());
        target.setBalance(source.balance());
        target.setDeleted(source.deleted());
        target.setUser(new User(source.userId()));
        return target;
    }

    @Override
    protected WalletDTO toModel(Wallet source) {
        return new WalletDTO(
                source.getId(),
                source.getUser().getId(),
                source.getBalance(),
                source.isDeleted()
        );
    }

    @Override
    public void merge(Wallet source, Wallet target) {
        target.setBalance(source.getBalance());
        target.setDeleted(source.isDeleted());
    }
}

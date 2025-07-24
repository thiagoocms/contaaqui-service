package com.thiagoocms.contaaqui.service;

import com.thiagoocms.contaaqui.common.AbstractCrudService;
import com.thiagoocms.contaaqui.common.MessageService;
import com.thiagoocms.contaaqui.contaaqui_core.domain.wallet.Wallet;
import com.thiagoocms.contaaqui.contaaqui_core.dto.wallet.WalletDTO;
import com.thiagoocms.contaaqui.mapper.WalletMapper;
import com.thiagoocms.contaaqui.contaaqui_core.repository.WalletRepository;
import com.thiagoocms.contaaqui.validation.WalletValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService extends AbstractCrudService<Wallet, Long, WalletDTO> {

    @Autowired
    protected WalletService(WalletRepository repository, WalletMapper mapper, WalletValidation validation, MessageService messageService) {
        super(repository, mapper, validation, messageService);
        setMessageNotFound("error.wallet.not.found");
    }
}

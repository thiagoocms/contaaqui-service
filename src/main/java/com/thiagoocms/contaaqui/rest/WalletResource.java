package com.thiagoocms.contaaqui.rest;

import com.thiagoocms.contaaqui.common.AbstractCrudResource;
import com.thiagoocms.contaaqui.contaaqui_core.dto.wallet.WalletDTO;
import com.thiagoocms.contaaqui.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallets")
public class WalletResource extends AbstractCrudResource<Long, WalletDTO> {

    @Autowired
    protected WalletResource(WalletService service) {
        super(service);
    }
}

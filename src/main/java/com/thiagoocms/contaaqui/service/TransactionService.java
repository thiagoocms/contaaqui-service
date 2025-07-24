package com.thiagoocms.contaaqui.service;

import com.thiagoocms.contaaqui.common.AbstractCrudService;
import com.thiagoocms.contaaqui.common.MessageService;
import com.thiagoocms.contaaqui.contaaqui_core.domain.transaction.Transaction;
import com.thiagoocms.contaaqui.contaaqui_core.dto.transaction.TransactionDTO;
import com.thiagoocms.contaaqui.contaaqui_core.repository.TransactionRepository;
import com.thiagoocms.contaaqui.mapper.TransactionMapper;
import com.thiagoocms.contaaqui.validation.TransactionValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService extends AbstractCrudService<Transaction, Long, TransactionDTO> {

    @Autowired
    protected TransactionService(TransactionRepository repository, TransactionMapper mapper, TransactionValidation validation, MessageService messageService) {
        super(repository, mapper, validation, messageService);
        setMessageNotFound("error.transaction.not.found");
    }
}

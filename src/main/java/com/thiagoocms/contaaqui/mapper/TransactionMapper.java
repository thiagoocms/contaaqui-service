package com.thiagoocms.contaaqui.mapper;

import com.thiagoocms.contaaqui.common.AbstractMapper;
import com.thiagoocms.contaaqui.contaaqui_core.domain.category.Category;
import com.thiagoocms.contaaqui.contaaqui_core.domain.transaction.Transaction;
import com.thiagoocms.contaaqui.contaaqui_core.domain.wallet.Wallet;
import com.thiagoocms.contaaqui.contaaqui_core.dto.transaction.TransactionDTO;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper extends AbstractMapper<Transaction, TransactionDTO> {

    @Override
    protected Transaction toEntity(TransactionDTO source) {
        Transaction target = new Transaction();
        target.setId(source.id());
        target.setAmount(source.amount());
        target.setCategory(new Category(source.categoryId()));
        target.setWallet(new Wallet(source.walletId()));
        target.setType(source.type());
        target.setDeleted(source.deleted());
        return target;
    }

    @Override
    protected TransactionDTO toModel(Transaction source) {
        return new TransactionDTO(
                source.getId(),
                source.getWallet().getId(),
                source.getCategory().getId(),
                source.getType(),
                source.getAmount(),
                source.isDeleted()
        );
    }

    @Override
    public void merge(Transaction source, Transaction target) {
        target.setAmount(source.getAmount());
        target.setCategory(source.getCategory());
        target.setType(source.getType());
        target.setDeleted(source.isDeleted());
    }
}

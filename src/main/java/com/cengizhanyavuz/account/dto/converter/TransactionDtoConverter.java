package com.cengizhanyavuz.account.dto.converter;

import com.cengizhanyavuz.account.dto.TransactionDto;
import com.cengizhanyavuz.account.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionDtoConverter {

    public TransactionDto convert(Transaction from) {
        return new TransactionDto(from.getId(),
                from.getTransactionType(),
                from.getAmount(),
                from.getTransactionDate());
    }
}
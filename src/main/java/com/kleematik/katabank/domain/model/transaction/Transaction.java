package com.kleematik.katabank.domain.model.transaction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
@Getter
public class Transaction
{
    @RequiredArgsConstructor
    @Getter
    enum Type {
        DEPOSIT("DEPOSIT"),
        WITHDRAW("WITHDRAW");
        private final String value;
    }
    private final Transaction.Type type;
    private final Money amount;
    private final String date;
}

package com.kleematik.katabank.domain.model.transaction;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode
@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public final class Transaction {
    private final TransactionType transactionType;
    private final Money money;
    private final LocalDateTime date;

    public BigDecimal getMoneyValue() {
        switch (transactionType) {
            case DEPOSIT:
                return money.getValue();
            case WITHDRAW:
                return money.getValue().negate();
            default:
                throw new IllegalArgumentException(transactionType.getValue() + " not supported!");
        }
    }

    public String getTransactionTypeValue() {
        return transactionType.getValue();
    }

    public String getDate() {
        return String.format("%s/%s/%s %s:%s:%s",
                date.getDayOfMonth(), date.getMonth().getValue(), date.getYear(),
                date.getHour(), date.getMinute(), date.getSecond());
    }
}

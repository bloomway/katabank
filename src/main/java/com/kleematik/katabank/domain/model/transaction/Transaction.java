package com.kleematik.katabank.domain.model.transaction;

import lombok.*;

import java.time.LocalDateTime;

@EqualsAndHashCode
@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public final class Transaction {
    private final TransactionType transactionType;
    private final Money amount;
    private final LocalDateTime date;

    public String getDate() {
        return String.format("%s/%s/%s %s:%s:%s",
                date.getDayOfMonth(), date.getMonth().getValue(), date.getYear(),
                date.getHour(), date.getMinute(), date.getSecond());
    }
}

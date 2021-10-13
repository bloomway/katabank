package com.kleematik.katabank.domain.model.transaction;

import com.kleematik.katabank.application.common.DateTimeProvider;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@EqualsAndHashCode
@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class Transaction {
    @RequiredArgsConstructor
    @Getter
    public enum Type {
        DEPOSIT("DEPOSIT"),
        WITHDRAW("WITHDRAW");
        private final String value;
    }

    private final Transaction.Type type;
    private final Money amount;
    private final Date date;
}

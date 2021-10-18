package com.kleematik.katabank.domain.model.transaction;

import lombok.*;

import java.util.Date;
import java.util.Objects;

@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public final class Transaction {
    @RequiredArgsConstructor
    @Getter
    public enum Kind {
        DEPOSIT("DEPOSIT"),
        WITHDRAW("WITHDRAW");
        private final String value;
    }

    private final Kind kind;
    private final Money amount;
    private final String date;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return kind == that.kind &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kind, amount, date);
    }
}

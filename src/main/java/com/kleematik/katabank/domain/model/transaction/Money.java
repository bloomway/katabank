package com.kleematik.katabank.domain.model.transaction;

import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode
@ToString
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public final class Money {

    private BigDecimal value;

    public static Money of(String value) {
        return new Money(new BigDecimal(value));
    }
}

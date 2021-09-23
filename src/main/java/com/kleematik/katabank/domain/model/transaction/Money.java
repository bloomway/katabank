package com.kleematik.katabank.domain.model.transaction;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@RequiredArgsConstructor(staticName = "of")
@Getter
public class Money
{
    private final String value;
}

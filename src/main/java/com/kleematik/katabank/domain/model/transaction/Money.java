package com.kleematik.katabank.domain.model.transaction;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "of")
@Getter
public class Money
{
    private final String value;
}

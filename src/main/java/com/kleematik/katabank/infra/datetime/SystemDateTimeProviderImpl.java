package com.kleematik.katabank.infra.datetime;

import com.kleematik.katabank.application.common.DateTimeProvider;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class SystemDateTimeProviderImpl implements DateTimeProvider {

    @Override
    public Date now() {
        return Date.from(Instant.now());
    }
}

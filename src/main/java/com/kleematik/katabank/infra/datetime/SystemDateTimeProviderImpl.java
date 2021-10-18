package com.kleematik.katabank.infra.datetime;

import com.kleematik.katabank.application.common.DateTimeProvider;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@NoArgsConstructor(
        staticName = "of",
        access = AccessLevel.PRIVATE
)
public class SystemDateTimeProviderImpl implements DateTimeProvider {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    public static SystemDateTimeProviderImpl getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    public String now() {
        return DATE_FORMAT.format(Date.from(Instant.now()));
    }

    private static class Holder {
        public static final SystemDateTimeProviderImpl INSTANCE = SystemDateTimeProviderImpl.of();
    }
}

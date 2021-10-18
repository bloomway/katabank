package com.kleematik.katabank.domain.services.print;

import com.kleematik.katabank.domain.model.transaction.Transaction;
import lombok.RequiredArgsConstructor;

import java.io.PrintWriter;
import java.util.List;

@RequiredArgsConstructor
public abstract class PrintStatement {

    private final PrintWriter writer;

    public void print(final List<Transaction> transactions) {
        final String output = concat(transactions);
        writer.write(output);
        writer.flush();
        writer.close();
    }

    protected abstract String concat(List<Transaction> transactions);
}

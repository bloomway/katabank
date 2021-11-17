package com.kleematik.katabank.infra.logging;

import com.kleematik.katabank.domain.model.transaction.Transaction;
import com.kleematik.katabank.application.logging.PrintStatement;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

public final class ConsolePrintStatement extends PrintStatement {

    private static final String NEW_LINE = "\n";
    private static final String FORMAT= "%1$-10s | %2$-10s | %3$-10s | %4$-10s";

    private final StringBuilder output;
    private BigDecimal balance = BigDecimal.ZERO;

    private ConsolePrintStatement() {
        super(new PrintWriter(System.out));
        output = new StringBuilder()
                .append(String.format(FORMAT,
                        "Operation", "Date", "Amount", "Balance"))
                .append(NEW_LINE)
                .append("-".repeat(48))
                .append(NEW_LINE);
    }

    public static ConsolePrintStatement getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final ConsolePrintStatement INSTANCE = new ConsolePrintStatement();
    }

    @Override
    public String concatenate(final List<Transaction> transactions) {
        transactions.forEach(transaction -> {
            calculateCurrentBalance(transaction);
            write(transaction);
        });
        return output.toString();
    }

    private void calculateCurrentBalance(Transaction transaction) {
        BigDecimal amount = transaction.getMoneyValue();
        balance = balance.add(amount);
    }

    private void write(Transaction transaction) {
        final var content = String.format(FORMAT,
                transaction.getTransactionTypeValue(),
                transaction.getDate(),
                transaction.getMoneyValue(),
                balance
        );
        output.append(content).append(NEW_LINE);
    }
}

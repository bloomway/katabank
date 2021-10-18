package com.kleematik.katabank.domain.services.print;

import com.kleematik.katabank.domain.model.transaction.Transaction;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

public final class ConsolePrintStatement extends PrintStatement {

    private static final String NEW_LINE = "\n";
    private static final String FORMAT= "%1$-10s | %2$-10s | %3$-10s | %4$-10s";

    private final StringBuilder output;

    private static class Holder {
        private static final ConsolePrintStatement INSTANCE = new ConsolePrintStatement();
    }

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

    @Override
    public String concat(final List<Transaction> transactions) {

        BigDecimal balance = BigDecimal.ZERO;

        for (Transaction transaction : transactions) {

            BigDecimal amount = BigDecimal.ZERO;

            switch (transaction.getKind()) {
                case DEPOSIT:
                    amount = new BigDecimal(transaction.getAmount().getValue());
                    break;
                case WITHDRAW:
                    amount = new BigDecimal(transaction.getAmount().getValue()).negate();
                    break;
                default:
                    break;
            }

            balance = balance.add(amount);

            output.append(
                    String.format(FORMAT,
                            transaction.getKind().getValue(),
                            transaction.getDate(),
                            transaction.getAmount().getValue(),
                            balance
                    )
            ).append(NEW_LINE);
        }
        return output.toString();
    }
}

package com.kleematik.katabank.domain.model.account;

import com.kleematik.katabank.application.common.DateTimeProvider;
import com.kleematik.katabank.domain.model.transaction.Money;
import com.kleematik.katabank.domain.model.transaction.Transaction;
import com.kleematik.katabank.domain.repository.TransactionRepository;
import com.kleematik.katabank.domain.services.print.PrintStatement;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Account {

    private final TransactionRepository transactionRepository;
    private final DateTimeProvider dateTimeProvider;
    private final PrintStatement printStatement;

    public void make(final Transaction.Kind kind, final String amount) {

        final Transaction transaction = Transaction.builder()
                .kind(kind)
                .date(dateTimeProvider.now())
                .amount(Money.of(amount))
                .build();

        transactionRepository.save(transaction);
    }

    public void printStatement()
    {
        final List<Transaction> transactions = transactionRepository.findAll()
                .orElse(new ArrayList<>());
        printStatement.print(transactions);
    }


    /*private void printAllOperations(List<Transaction> transactions)
    {
        final String format = "%1$-10s | %2$-10s | %3$-10s | %4$-10s";
        final StringBuilder output = new StringBuilder()
                .append(String.format(format, "Operation", "Date", "Amount", "Balance"))
                .append(NEW_LINE)
                .append("-".repeat(48))
                .append(NEW_LINE);

        BigDecimal balance = BigDecimal.ZERO;
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        for (var transaction : transactions)
        {
            BigDecimal amount = BigDecimal.ZERO;
            switch (transaction.getKind())
            {
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
                    String.format(format,
                            transaction.getKind().getValue(),
                            simpleDateFormat.format(transaction.getDate()),
                            transaction.getAmount().getValue(),
                            balance
                    )
            )
                    .append(NEW_LINE);
        }
        System.out.println(output);
    }*/
}

package com.kleematik.katabank.domain.model.account;

import com.kleematik.katabank.domain.model.transaction.Money;
import com.kleematik.katabank.domain.model.transaction.Transaction;
import com.kleematik.katabank.domain.repository.TransactionRepository;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Account
{
    private static final String NEW_LINE = "\n";

    private final TransactionRepository transactionRepository;

    public void deposit(String date, String amount)
    {
        final Transaction transaction = createTransaction(Transaction.Type.DEPOSIT, date, amount);
        transactionRepository.save(transaction);
    }

    public void withdraw(String date, String amount)
    {
        final Transaction transaction = createTransaction(Transaction.Type.WITHDRAW, date, amount);
        transactionRepository.save(transaction);
    }

    public void printStatement()
    {
        final var transactions = transactionRepository.findAll()
                .orElse(new ArrayList<>());
        printAllOperations(transactions);
    }

    private Transaction createTransaction(Transaction.Type type, String date, String amount)
    {
        return Transaction.builder()
                .type(type)
                .amount(Money.of(amount))
                .date(date)
                .build();
    }

    private void printAllOperations(List<Transaction> transactions)
    {
        final String format = "%1$-10s | %2$-10s | %3$-10s | %4$-10s";
        final StringBuilder output = new StringBuilder()
                .append(String.format(format, "Operation", "Date", "Amount", "Balance"))
                .append(NEW_LINE)
                .append("-".repeat(48))
                .append(NEW_LINE);

        BigDecimal balance = BigDecimal.ZERO;

        for (var transaction : transactions)
        {
            BigDecimal amount = BigDecimal.ZERO;
            switch (transaction.getType())
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
            output.append(String.format(format, transaction.getType().getValue(),
                                    transaction.getDate(),
                                    transaction.getAmount().getValue(), balance))
                    .append(NEW_LINE);
        }
        System.out.println(output);
    }
}

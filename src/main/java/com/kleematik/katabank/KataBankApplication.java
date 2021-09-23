package com.kleematik.katabank;

import com.kleematik.katabank.domain.model.account.Account;
import com.kleematik.katabank.domain.repository.TransactionRepository;
import com.kleematik.katabank.infra.data.persistence.inmemory.InMemoryTransactionRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class KataBankApplication
{
	public static void main(String[] args)
	{
		final ConfigurableApplicationContext context = SpringApplication.run(KataBankApplication.class, args);

		final TransactionRepository repository = context.getBean(InMemoryTransactionRepositoryImpl.class);
		final Account account = Account.builder()
				.transactionRepository(repository)
				.build();

		account.deposit("05/11/2019", "500");
		account.withdraw("10/11/2019", "200");
		account.withdraw("15/11/2019", "150");
		account.deposit("11/11/2019", "200");
		account.withdraw("20/11/2019", "300");
		account.withdraw("25/11/2019", "150");

		account.printStatement();
	}

}

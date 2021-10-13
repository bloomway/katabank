package com.kleematik.katabank;

import com.kleematik.katabank.application.common.DateTimeProvider;
import com.kleematik.katabank.domain.model.account.Account;
import com.kleematik.katabank.domain.repository.TransactionRepository;
import com.kleematik.katabank.infra.data.persistence.inmemory.InMemoryTransactionRepositoryImpl;
import com.kleematik.katabank.infra.datetime.SystemDateTimeProviderImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class KataBankApplication {
	public static void main(String[] args) throws InterruptedException {
		final ConfigurableApplicationContext context = SpringApplication.run(KataBankApplication.class, args);

		final TransactionRepository repository = context.getBean(InMemoryTransactionRepositoryImpl.class);
		final DateTimeProvider dateTimeProvider = context.getBean(SystemDateTimeProviderImpl.class);

		final Account account = Account.builder()
				.transactionRepository(repository)
				.dateTimeProvider(dateTimeProvider)
				.build();

		account.deposit("500");
		Thread.sleep(4000);

		account.withdraw("200");
		Thread.sleep(2000);

		account.withdraw("150");
		Thread.sleep(3000);

		account.deposit("200");
		Thread.sleep(1000);

		account.withdraw("300");
		Thread.sleep(2000);

		account.withdraw("150");

		account.printStatement();
	}

}

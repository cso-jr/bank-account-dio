package br.com.dio.repositories;

import static br.com.dio.model.BankService.ACCOUNT;
import static lombok.AccessLevel.PRIVATE;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import br.com.dio.exception.NoFundsEnoughException;
import br.com.dio.model.AccountWallet;
import br.com.dio.model.Money;
import br.com.dio.model.MoneyAudit;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public final class CommonsRepository {

	//private static final BankService ACCOUNT = null;

	public static void checkFundsForTransaction(final AccountWallet source, final long amount) {
		if(source.getFunds() < amount) {
			throw new NoFundsEnoughException("sua conta não tem dinheiro o suficiente para realizar essa transação");
		}
	}
	
	public static List<Money> generateMoney(final UUID transactionId, final long funds, final String description){
		var history = new MoneyAudit(transactionId, ACCOUNT, description, OffsetDateTime.now());
		return Stream.generate(()-> new Money(history)).limit(funds).toList();
	}
	
}

package br.com.dio.repositories;

import static br.com.dio.repositories.CommonsRepository.checkFundsForTransaction;

import java.util.ArrayList;
import java.util.List;

import br.com.dio.exception.AccountsWithInvestmentException;
import br.com.dio.exception.InvestmentNotFoundException;
import br.com.dio.exception.PixInUseException;
import br.com.dio.exception.WalletNotFoundException;
import br.com.dio.model.AccountWallet;
import br.com.dio.model.Investment;
import br.com.dio.model.InvestmentWallet;

public class InvestmentRepository {

	private long nextId;
	private final List<Investment> investments = new ArrayList<>();
	private final List<InvestmentWallet> wallets = new ArrayList<>();
	
	
	
	public Investment create(final long tax, final long initialFunds) {
		this.nextId++;
		var investment = new Investment(this.nextId, tax, initialFunds);
		investments.add(investment);
		return investment;
	}
	
	
	public InvestmentWallet initInvestment(final AccountWallet account, final long id) {
		var accountsInUse = wallets.stream().map(a -> a.getAccount()).toList();
		
			if (accountsInUse.contains(account)) {
				throw new AccountsWithInvestmentException("A conta '" + account + "' já possui investimento");
		}
		
		var investment = findById(id);
		checkFundsForTransaction(account, investment.initialFunds());
		var wallet = new InvestmentWallet(investment, account, investment.initialFunds());
		wallets.add(wallet);
		return wallet;
		
	}
	
	
	
	public InvestmentWallet deposit(final String pix, final long funds) {
		var wallet = findWalletByAccountPix(pix);
		wallet.addMoney(wallet.getAccount().reduceMoney(funds), wallet.getService(), "Investimento");
		return wallet;
	}
	
	
	
	
	public Investment findById(final long id) {
		return investments.stream()
		.filter(a -> a.id() == id)
		.findFirst()
		.orElseThrow(
				() -> new InvestmentNotFoundException("A carteira não foi encontrada"));
	}
	
	
	public InvestmentWallet wihdraw(final String pix, final long funds) {
		var wallet = findWalletByAccountPix(pix);
		checkFundsForTransaction(wallet,funds);
		wallet.getAccount().addMoney(wallet.reduceMoney(funds),wallet.getService(), "saque de investimentos");
		if (wallet.getFunds() == 0) {
			wallets.remove(wallet);
		}
		return wallet;
	}
	
	
	public void updateAmount(final long percent) {
		wallets.forEach(w -> w.updateAmount(percent));
	}
	
	
	public InvestmentWallet findWalletByAccountPix(final String pix) {
		return wallets.stream()
				.filter(w -> w.getAccount().getPix().contains(pix))
				.findFirst()
				.orElseThrow(
						() -> new WalletNotFoundException("A carteira não foi encontrada"));
	}
	
	
	public List<InvestmentWallet> listWallets(){
		return this.wallets;
	}
	
	
	public List<Investment> list(){
		return this.investments;
	}
	
	
	
	
}

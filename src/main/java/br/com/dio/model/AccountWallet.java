package br.com.dio.model;

import static br.com.dio.model.BankService.ACCOUNT;

import java.util.List;
public class AccountWallet extends Wallet{

	private final List<String> pix;
	
	
	
	
	
	public AccountWallet(final List<String> pix) {
		super(ACCOUNT);
		this.pix = pix;
	}





	public AccountWallet(final long amount, List<String> pix) {
		super(ACCOUNT);
		this.pix = pix;
		addMoney(amount, "Valor de criação da conta");
	}
	
	// valor que é passado na criação da conta >>> diferente do addMoney da classe Wallet que trata do addMoney quando a conta já existe
	public void addMoney(final long amount, final String description) {
		var money = generateMoney(amount, description);
		this.money.addAll(money);
	}
	
	
	

}

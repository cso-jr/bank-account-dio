package br.com.dio;

import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.Scanner;

import br.com.dio.exception.AccountNotFoundException;
import br.com.dio.exception.NoFundsEnoughException;
import br.com.dio.model.AccountWallet;
import br.com.dio.repositories.AccountRepository;
import br.com.dio.repositories.InvestmentRepository;

public class Main {

	
	private static final TemporalUnit SECONDS = null;
	private static final String ISO_DATE_TIME = null;
	private static AccountRepository accountRepository = new AccountRepository();
	private static InvestmentRepository investmentRepository = new InvestmentRepository();
	private static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		System.out.println("Ola seja bem-vindo ao DIO bank");
		
	
		
		while (true) {
			System.out.println("selecione a operação desejada");
			System.out.println("1 - Criar uma conta");
			System.out.println("2 - Criar um investimento");
			System.out.println("3 - Fazer um investimento");
			System.out.println("4 - Depositar na conta");
			System.out.println("5 - Sacar da conta");
			System.out.println("6 - Transferência entre contas");
			System.out.println("7 - Investir");
			System.out.println("8 - Resgatar investimento");
			System.out.println("9 - Listar contas");
			System.out.println("10 - Listar investimentos");
			System.out.println("11 - Listar carteiras de investimentos");
			System.out.println("12 - Listar contas de investimento");
			System.out.println("13 - Atualizar investimentos");
			System.out.println("14 - Histórico de conta");
			System.out.println("15 - Sair");
			
			var option = sc.nextInt();
			switch (option) {
				case 1: createAccount();
				case 2: createInvestment();
				case 3: createWalletInvestment();
				case 4: deposit();
				case 5: withdraw();
				case 6: transferToAccount();
				case 7: incInvestment();
				case 8: rescueInvestment();
				case 9: accountRepository.list().forEach(System.out::println);
				case 10: investmentRepository.list().forEach(System.out::println);
				case 11: investmentRepository.listWallets().forEach(System.out::println);
				case 12: {
					investmentRepository.updateAmount();
					System.out.println("Investimentos atualizados");
				}
				case 13: checkHistory();
				case 14: System.exit(0);
				default: System.out.println("Opção inválida");
			}			
		}
	}
	





	private static void checkHistory() {
		System.out.println("Informe a chave pix da conta para verificar o extrato: ");
		var pix = sc.next();
		AccountWallet wallet;
		try {
			var sortedHistory = accountRepository.getHistory(pix);

		} catch (AccountNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
	}






	private static void createAccount() {
		System.out.println("Informe as chaves pix (separadas por ';'");
		var pix = Arrays.stream(sc.next().split(";")).toList();
		System.out.println("Informe o valor inicial do depósito");
		var amount = sc.nextLong();
		var wallet = accountRepository.create(pix, amount);
		System.out.println("Conta criada: " + wallet);
	}

	private static void createInvestment() {
		System.out.println("Informe a taxca do investimento");
		var tax = sc.nextInt();
		System.out.println("Informe o valor inicial do depósito");
		var initialFunds = sc.nextLong();
		var investment = investmentRepository.create(tax, initialFunds);
		System.out.println("Investimento criado: " + investment);
	}
	
	private static void withdraw() {
		System.out.println("Informe a chave pix da conta para saque: ");
		var pix = sc.next();
		System.out.println("Informe o valor que será sacado: ");
		var amount = sc.nextLong();
		
		try {
			accountRepository.withdraw(pix, amount);	
		} catch (NoFundsEnoughException | AccountNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
		
	private static void deposit() {
		System.out.println("Informe a chave pix da conta para deposito: ");
		var pix = sc.next();
		System.out.println("Informe o valor que será depositado: ");
		var amount = sc.nextLong();
		try {
			accountRepository.deposit(pix, amount);
		} catch (NoFundsEnoughException | AccountNotFoundException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println("O valor R$ " + amount + " foi depositado");
	}
	
	private static void transferToAccount() {
		System.out.println("Informe a chave pix da conta de origem: ");
		var pixOrigem = sc.next();
		System.out.println("Informe a chave pix da conta de destino: ");
		var pixDestino = sc.next();
		System.out.println("Informe o valor que será transferido: ");
		var amount = sc.nextLong();
		try {
			accountRepository.transferMoney(pixOrigem, pixDestino, amount);
		} catch (NoFundsEnoughException | AccountNotFoundException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println("O valor R$ " + amount + " foi transferido da conta " + pixOrigem + " para a conta " + pixDestino + ".");
	}
	

	private static void createWalletInvestment() {
		System.out.println("Informe a chave pix da conta:");
		var pix = sc.next();
		var account = accountRepository.findByPix(pix);
		System.out.println("Informe o identificador do investimento:");
		var investmentId = sc.nextInt();
		var investmentWallet = investmentRepository.initInvestment(account, investmentId);
		System.out.println("Conta de investimento criada: " + investmentWallet);
	}
	
	private static void incInvestment() {
		System.out.println("Informe a chave pix da conta para investimento: ");
		var pix = sc.next();
		System.out.println("Informe o valor que será investido: ");
		var amount = sc.nextLong();
		try { 
			investmentRepository.deposit(pix, amount);
		} catch (NoFundsEnoughException | AccountNotFoundException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println("O valor R$ " + amount + " foi depositado");
	}
	
	private static void rescueInvestment() {
		System.out.println("Informe a chave pix da conta para resgate do investimento: ");
		var pix = sc.next();
		System.out.println("Informe o valor que será resgatado: ");
		var amount = sc.nextLong();
		
		try {
			investmentRepository.withdraw(pix, amount);	
		} catch (NoFundsEnoughException | AccountNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
}

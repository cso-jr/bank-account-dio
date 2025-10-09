package br.com.dio;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

import java.util.Arrays;
import java.util.Scanner;

import br.com.dio.exception.AccountNotFoundException;
import br.com.dio.exception.AccountsWithInvestmentException;
import br.com.dio.exception.NoFundsEnoughException;
import br.com.dio.exception.PixInUseException;
import br.com.dio.exception.WalletNotFoundException;
import br.com.dio.repositories.AccountRepository;
import br.com.dio.repositories.InvestmentRepository;

public class Main {

	private static AccountRepository accountRepository = new AccountRepository();
	private static InvestmentRepository investmentRepository = new InvestmentRepository();
	
	private static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		System.out.println("Olá! Seja bem-vindo ao DIO bank do Carlos");
		
		while (true) {
			System.out.println("selecione a operação desejada:");
			System.out.println("1 - Criar uma conta");
			System.out.println("2 - Criar um investimento");
			System.out.println("3 - Criar uma carteira de investimento");
			System.out.println("4 - Depositar na conta");
			System.out.println("5 - Sacar da conta");
			System.out.println("6 - Transferência entre contas");
			System.out.println("7 - Investir");
			System.out.println("8 - Resgatar investimento");
			System.out.println("9 - Listar contas");
			System.out.println("10 - Listar investimentos");
			System.out.println("11 - Listar carteiras de investimentos");
			System.out.println("12 - Atualizar investimentos");
			System.out.println("13 - Histórico de conta");
			System.out.println("14 - Sair");
			
			var option = sc.nextInt();
			switch (option) {
				case 1-> createAccount();
				case 2-> createInvestment();
				case 3-> createWalletInvestment();
				case 4-> deposit();
				case 5-> withdraw();
				case 6-> transferToAccount();
				case 7-> incInvestment();
				case 8-> rescueInvestment();
				case 9-> accountRepository.list().forEach(System.out::println);
				case 10-> investmentRepository.list().forEach(System.out::println);
				case 11-> investmentRepository.listWallets().forEach(System.out::println);
				case 12-> {
					investmentRepository.updateAmount();
					System.out.println("Investimentos atualizados");
					}
				case 13-> checkHistory();
				case 14-> System.exit(0);
				default-> System.out.println("Opção inválida");
			}			
		}
	}
	
	// cria conta a partir de um valor de chave pix que é o identificador da conta e registra um valor inicial associado.
	private static void createAccount() {
		System.out.println("Informe as chaves pix (separadas por ';')");
		var pix = Arrays.stream(sc.next().split(";")).toList();
		System.out.println("Informe o valor inicial do depósito");
		var amount = sc.nextLong();
		try {
			var wallet = accountRepository.create(pix, amount);
			System.out.println("Conta criada: " + wallet);	
		} catch (PixInUseException e) {
			e.getMessage();
		}
	}

	// cria um investimento como produto do banco a ser futuramente associado a uma conta atraves de uma carteira de investimentos.
	private static void createInvestment() {
		System.out.println("Informe a taxa do investimento");
		var tax = sc.nextInt();
		System.out.println("Informe o valor inicial do depósito");
		var initialFunds = sc.nextLong();
		var investment = investmentRepository.create(tax, initialFunds); // essa etapa não está num bloco try-catch porque tem outro método de validação no método que evita lançar uma exceção.
		System.out.println("Investimento criado: " + investment);
	}
	// reduz a quantidade de 'dinheiro' na conta. Como a quantidade é uma lista de centavos, a quantidade reduzida da lista de Money será a quantidade informada por 'amount'.
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
	
	// acrescenta o valor na conta
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
		System.out.println("O valor R$ " + (amount/100) + "," + (amount % 100) + " foi depositado");
	}
	
	// transfere valor de uma conta origem para uma conta destino.
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
		System.out.println("O valor R$ " + (amount/100) + "," + (amount%100) + " foi transferido da conta " + pixOrigem + " para a conta " + pixDestino + ".");
	}
	
	//cria uma carteira de investimento , que vai ser associada a uma conta e vai conter investimentos nela.
	private static void createWalletInvestment() {
		System.out.println("Informe a chave pix da conta:");
			var pix = sc.next();
		try {
			var account = accountRepository.findByPix(pix);
			System.out.println("Informe o identificador do investimento:");
			var investmentId = sc.nextInt();
			var investmentWallet = investmentRepository.initInvestment(account, investmentId);
			System.out.println("Carteira de investimento criada: " + investmentWallet);	
		} catch (NoFundsEnoughException | WalletNotFoundException | AccountsWithInvestmentException | AccountNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	//incrementa o valor do investimento, retirando da conta.
	private static void incInvestment() {
		System.out.println("Informe a chave pix da conta para investimento: ");
		var pix = sc.next();
		System.out.println("Informe o valor que será investido: ");
		var amount = sc.nextLong();
		try { 
			investmentRepository.deposit(pix, amount);
		} catch (NoFundsEnoughException | WalletNotFoundException | AccountNotFoundException e) {
			System.out.println(e.getMessage());
		}		
		System.out.println("O valor R$ " + amount + " foi depositado");
	}
	
	//resgata o valor de um investimento e o retorna para a conta?
	private static void rescueInvestment() {
		System.out.println("Informe a chave pix da conta pra resgate do investimento");
		var pix = sc.next();
		System.out.println("Informe o valor que será resgatado: ");
		var amount = sc.nextLong();
		
		try {
			investmentRepository.withdraw(pix, amount);	
		} catch (NoFundsEnoughException | AccountNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	// lista as movimentações da conta.
	private static void checkHistory() {
		System.out.println("Informe a chave pix da conta para verificar o extrato: ");
		var pix = sc.next();
		try {
			var sortedHistory = accountRepository.getHistory(pix);
			sortedHistory.forEach((k,v) -> {
				System.out.println(k.format(ISO_DATE_TIME));
				System.out.println(v.getFirst().transactionId());
				System.out.println(v.getFirst().description());
				System.out.println("R$ " + (v.size() / 100) + "," + (v.size() % 100));				
			});

		} catch (AccountNotFoundException e) {
			System.out.println(e.getMessage());
		}	
		
	}

}

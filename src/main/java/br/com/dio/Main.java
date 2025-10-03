package br.com.dio;

import java.util.Arrays;
import java.util.Scanner;

import javax.security.auth.login.AccountNotFoundException;

import br.com.dio.repositories.AccountRepository;
import br.com.dio.repositories.InvestmentRepository;

public class Main {

	
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
				case 3:
				case 4:
				case 5:
				case 6:
				case 7:
				case 8:
				case 9: accountRepository.list().forEach(System.out::println);
				case 10: investmentRepository.list().forEach(System.out::println);
				case 11: investmentRepository.listWallets().forEach(System.out::println);
				case 12: {
					investmentRepository.updateAmount();
					System.out.println("Investimentos atualizados");
				}
				case 13: 
				case 14: System.exit(0);
				default: System.out.println("Opção inválida");
			}			
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
	
	private void deposit() throws AccountNotFoundException {
		System.out.println("Informe a chave pix da conta para deposito: ");
		var pix = sc.next();
		System.out.println("Informe o valor que será depositado: ");
		var amount = sc.nextLong();
		accountRepository.deposit(pix, amount);
	}
	
	
}

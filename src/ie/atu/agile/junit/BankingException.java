package ie.atu.agile.junit;

public class BankingException extends Exception {
	public BankingException(String message) {
		super(message);
	}
}

class AccountNotFoundException extends BankingException {
	private String accountHolder;

	public AccountNotFoundException(String accountHolder) {
		super("Account not found: " + accountHolder);
		this.accountHolder = accountHolder;
	}

	public String getAccountHolder() {
		return this.accountHolder;
	}
}

class InvalidAmountException extends BankingException {
	private double amount;

	public InvalidAmountException(double amount) {
		super("Invalid amount: " + amount);
		this.amount = amount;
	}

	public double getAmount() {
		return this.amount;
	}
}

class InsufficientFundsException extends BankingException {
	private double balance;
	private double amount;

	public InsufficientFundsException(double balance, double amount) {
		super("Insufficient funds: balance= " + balance + " requested= " + amount);
		this.balance = balance;
		this.amount = amount;
	}

	public double getBalance() {
		return this.balance;
	}

	public double getAmount() {
		return this.amount;
	}
}
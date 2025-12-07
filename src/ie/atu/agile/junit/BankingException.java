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
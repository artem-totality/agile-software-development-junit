package ie.atu.agile.junit;

import java.util.stream.DoubleStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AccountTest {
	Account account;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("Starting Account tests...");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		System.out.println("All Account tests completed.");
	}

	@BeforeEach
	void setUp() throws Exception {
		this.account = new Account("Artem", 500);
	}

	@AfterEach
	void tearDown() throws Exception {
		this.account = null;
	}

	@Test
	void checkAccountCreatingInitialValues() {
		Assertions.assertEquals(this.account.getAccountHolder(), "Artem");
		Assertions.assertEquals(this.account.getBalance(), 500);
		Assertions.assertEquals(this.account.getLoan(), 0);
	}

	@ParameterizedTest
	@CsvSource({ "200, 700", "300, 800", "1000, 1500" })
	void checkDepositMoney(double amount, double total) {
		this.account.deposit(amount);
		Assertions.assertEquals(this.account.getBalance(), total);
	}

	@ParameterizedTest
	@CsvSource({ "200, 300", "300, 200", "500, 0" })
	void checkWithdrawMoney(double amount, double total) {
		this.account.withdraw(amount);
		Assertions.assertEquals(this.account.getBalance(), total);
	}

	@ParameterizedTest
	@CsvSource({ "200, 300, 400", "300, 200, 600", "500, 1000, 0" })
	void checkDepositThanWithdrawMoney(double amountPlus, double amountMinus, double total) {
		this.account.deposit(amountPlus);
		this.account.withdraw(amountMinus);
		Assertions.assertEquals(this.account.getBalance(), total);
	}

	@ParameterizedTest
	@CsvSource({ "200, 200", "300, 300", "1000, 1000" })
	void checkApproveLoan(double amount, double totalLoan) {
		this.account.approveLoan(amount);
		Assertions.assertEquals(this.account.getLoan(), totalLoan);
	}

	@Test
	void checkApproveLoansSeries() {
		final double[] loans = { 100, 300, 400, 1000 };

		for (var loan : loans)
			this.account.approveLoan(loan);

		Assertions.assertEquals(this.account.getLoan(), DoubleStream.of(loans).sum());
	}

	@ParameterizedTest
	@CsvSource({ "200, 100, 100", "300, 200, 100", "1000, 1000, 0" })
	void checkApproveThanRepayLoan(double approve, double repay, double totalLoan) {
		this.account.approveLoan(approve);
		this.account.repayLoan(repay);
		Assertions.assertEquals(this.account.getLoan(), totalLoan);
	}
}

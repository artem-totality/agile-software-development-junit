package ie.atu.agile.junit;

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
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
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
}

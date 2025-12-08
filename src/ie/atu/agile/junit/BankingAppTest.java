package ie.atu.agile.junit;

import java.util.Random;
import java.util.stream.DoubleStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BankingAppTest {

	static BankingApp bulkBankingApp;
	static final int MAX_USER_COUNT = 10_000;
	static final String AVERAGE_USER_PREFIX = "User-";
	static final double AVERAGE_USER_DEPOSIT_AMOUNT = 500;
	static final String IMPOSSIBLE_USER = "IMPOSSIBLE_USER";
	static final double INVALID_AMOUNT = -42;
	static final double ENORMOUS_AMOUNT = 1_000_000_000;
	static final String[] SMALL_BANNKING_APP_USERS = { "Artem", "Andrii", "Oleksandr" };
	BankingApp smallBankingApp;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		System.out.println("Starting BankingApp tests...");
		BankingAppTest.bulkBankingApp = new BankingApp();

		for (var i = 0; i < BankingAppTest.MAX_USER_COUNT; i++)
			BankingAppTest.bulkBankingApp.addAccount(BankingAppTest.AVERAGE_USER_PREFIX + i,
					BankingAppTest.AVERAGE_USER_DEPOSIT_AMOUNT);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		BankingAppTest.bulkBankingApp = null;
		System.out.println("All BankingApp tests completed.");
	}

	@BeforeEach
	void setUp() throws Exception {
		this.smallBankingApp = new BankingApp();

		for (var user : BankingAppTest.SMALL_BANNKING_APP_USERS)
			this.smallBankingApp.addAccount(user, AVERAGE_USER_DEPOSIT_AMOUNT);
	}

	@AfterEach
	void tearDown() throws Exception {
		this.smallBankingApp = null;
	}

	// Helper method for getting random user name for Bulk Banking App
	static String getRandomUserForBulkBannkingApp() {
		Random random = new Random();
		var randomIndex = random.nextInt(BankingAppTest.MAX_USER_COUNT);

		return BankingAppTest.AVERAGE_USER_PREFIX + randomIndex;
	}

	// Helper method for getting random user name for Bulk Banking App
	static String getRandomUserForSmallBannkingApp() {
		Random random = new Random();
		var randomIndex = random.nextInt(BankingAppTest.SMALL_BANNKING_APP_USERS.length);

		return BankingAppTest.SMALL_BANNKING_APP_USERS[randomIndex];
	}

	@Test
	void checkBankingAppsInitialValues() {
		// Test Bulk Banking App initial values
		Assertions.assertAll("Test Bulk Banking App initial values",
				() -> Assertions.assertNotNull(BankingAppTest.bulkBankingApp),
				() -> Assertions.assertEquals(
						BankingAppTest.MAX_USER_COUNT * BankingAppTest.AVERAGE_USER_DEPOSIT_AMOUNT,
						BankingAppTest.bulkBankingApp.getTotalDeposits()));

		// Test Small Banking App initial values
		Assertions.assertAll("Test Small Banking App initial values",
				() -> Assertions.assertNotNull(this.smallBankingApp),
				() -> Assertions.assertEquals(
						BankingAppTest.SMALL_BANNKING_APP_USERS.length * BankingAppTest.AVERAGE_USER_DEPOSIT_AMOUNT,
						this.smallBankingApp.getTotalDeposits()));
	}

	@ParameterizedTest
	@CsvSource({ "200, 700", "300, 800", "1000, 1500" })
	void checkRandomUserDepositMoneyToAccount(double amount, double total) throws BankingException {
		var randomUser = BankingAppTest.getRandomUserForSmallBannkingApp();

		this.smallBankingApp.deposit(randomUser, amount);
		Assertions.assertEquals(total, this.smallBankingApp.getBalance(randomUser));
	}

	@ParameterizedTest
	@CsvSource({ "200, 300", "300, 200", "500, 0" })
	void checkRandomUserWithdrawMoney(double amount, double total) throws BankingException {
		var randomUser = BankingAppTest.getRandomUserForSmallBannkingApp();

		this.smallBankingApp.withdraw(randomUser, amount);
		Assertions.assertEquals(total, this.smallBankingApp.getBalance(randomUser));
	}

	@ParameterizedTest
	@CsvSource({ "200, 300, 400", "300, 200, 600", "500, 1000, 0" })
	void checkRandomUserDepositThanWithdrawMoney(double amountPlus, double amountMinus, double total)
			throws BankingException {
		var randomUser = BankingAppTest.getRandomUserForSmallBannkingApp();

		this.smallBankingApp.deposit(randomUser, amountPlus);
		this.smallBankingApp.withdraw(randomUser, amountMinus);
		Assertions.assertEquals(total, this.smallBankingApp.getBalance(randomUser));
	}

	@ParameterizedTest
	@CsvSource({ "200, 200", "300, 300", "1000, 1000" })
	void checkRandomUserApproveLoan(double amount, double totalLoan) throws BankingException {
		var randomUser = BankingAppTest.getRandomUserForSmallBannkingApp();

		this.smallBankingApp.approveLoan(randomUser, amount);
		Assertions.assertEquals(totalLoan, this.smallBankingApp.getLoan(randomUser));
	}

	@Test
	void checkRandomUserApproveLoansSeries() throws BankingException {
		final double[] LOANS = { 100, 150, 250, 700 };
		var randomUser = BankingAppTest.getRandomUserForSmallBannkingApp();

		for (var loan : LOANS)
			this.smallBankingApp.approveLoan(randomUser, loan);

		Assertions.assertEquals(DoubleStream.of(LOANS).sum(), this.smallBankingApp.getLoan(randomUser));
	}

	@ParameterizedTest
	@CsvSource({ "200, 100, 100", "300, 200, 100", "1000, 1000, 0" })
	void checkRandomUserApproveThanRepayLoan(double approve, double repay, double totalLoan) throws BankingException {
		var randomUser = BankingAppTest.getRandomUserForSmallBannkingApp();

		this.smallBankingApp.approveLoan(randomUser, approve);
		this.smallBankingApp.repayLoan(randomUser, repay);
		Assertions.assertEquals(totalLoan, this.smallBankingApp.getLoan(randomUser));
	}

}

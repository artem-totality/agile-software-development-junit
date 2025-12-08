package ie.atu.agile.junit;

import java.util.Random;

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

	@Test
	void checkBankingAppsInitialValues() {
		// Test Bulk Banking App initial values
		Assertions.assertNotNull(BankingAppTest.bulkBankingApp);
		Assertions.assertEquals(BankingAppTest.MAX_USER_COUNT * BankingAppTest.AVERAGE_USER_DEPOSIT_AMOUNT,
				BankingAppTest.bulkBankingApp.getTotalDeposits());

		// Test Small Banking App initial values
		Assertions.assertNotNull(this.smallBankingApp);
		Assertions.assertEquals(
				BankingAppTest.SMALL_BANNKING_APP_USERS.length * BankingAppTest.AVERAGE_USER_DEPOSIT_AMOUNT,
				this.smallBankingApp.getTotalDeposits());
	}

	@ParameterizedTest
	@CsvSource({ "200, 700", "300, 800", "1000, 1500" })
	void checkRandomUserDepositMoneyToAccount(double amount, double total) throws BankingException {
		Random random = new Random();
		var randomUserIndex = random.nextInt(BankingAppTest.SMALL_BANNKING_APP_USERS.length);
		var randomUser = BankingAppTest.SMALL_BANNKING_APP_USERS[randomUserIndex];

		this.smallBankingApp.deposit(randomUser, amount);
		Assertions.assertEquals(total, this.smallBankingApp.getBalance(randomUser));
	}
}

package ie.atu.agile.junit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
	void checkAccountCreatingValues() {
		Assertions.assertEquals(this.account.getAccountHolder(), "Artem");
		Assertions.assertEquals(this.account.getBalance(), 500);
		Assertions.assertEquals(this.account.getLoan(), 0);
	}

}

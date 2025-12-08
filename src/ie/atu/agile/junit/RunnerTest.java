package ie.atu.agile.junit;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({ BankingAppTest.class, AccountTest.class })
class RunnerTest {
}

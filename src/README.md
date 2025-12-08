# AGILE SOFTWARE DEVELOPMENT
## Assignment One (JUnit)
**Artem Vasylevytskyi - G00473379**

---

## Project Overview

This project developed a JUnit automated test suite capable of testing the Banking Java application.

The provided code for testing was refactored to improve quality and facilitate further testing. The following changes were implemented:

## Refactoring Changes

### 1. Class Separation
The `Account` class was extracted into a separate file to comply with the Single Responsibility Principle (SRP).

### 2. Business Logic Restructuring
All business logic was removed from the `Account` class and transferred to the application level in `BankingApp`.

### 3. Exception Handling
To handle corresponding negative scenarios, the following exception classes were added to the project:
- Base class: `BankingException`
- Derived classes:
  - `AccountNotFoundException`
  - `InvalidAmountException`
  - `InsufficientFundsException`
  - `RepaymentExceedsLoanException`

### 4. Test Units
- `AccountTest` - created for testing the `Account` class
- `BankingAppTest` - created for testing the `BankingApp` class
- `RunnerTest` - created as a test suite

## Version Control

Development was conducted using the GIT version control system. The complete project history can be explored in the repository at:

**https://github.com/artem-totality/agile-software-development-junit**

---

Feel free to ask any questions :)

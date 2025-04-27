# Banking Events API

A simple RESTful API built with Spring Boot to simulate basic banking operations: deposit, withdraw, and transfer.  
Supports balance checks and account resets. Designed for test-driven development and RESTful interaction.

## Features

- Create deposit, withdraw, and transfer events
- Query account balances
- Reset system state
- Input validation and error handling
- Simple in-memory data store

## Technologies Used
- Java 17+
- Spring Boot 3
- JUnit 5
- MockMvc for integration tests

## Installation

This project does not require any external libraries or dependencies.

## How to Build and Run

1.  Ensure you have Java installed on your system.
2.  Compile the Java source files.
3.  Run the `Main` class to start the application.

## Usage

1.  **Create an Account:** To create a new account, use the `createAccount` method in the `AccountService` class and provide the account ID and initial balance.
2.  **Deposit Funds:** Use the `deposit` method of the `AccountService` class, specifying the account ID and the amount to deposit.
3.  **Withdraw Funds:** Use the `withdraw` method of the `AccountService` class, specifying the account ID and the amount to withdraw.
4.  **Transfer Funds:** Use the `transfer` method of the `AccountService` class, specifying the origin account ID, destination account ID, and the amount to transfer.
5.  **Check the Balance:** Use the `getBalance` method of the `AccountService` class, specifying the account ID.

## Endpoints

### `POST /event`
Creates a new banking event. Supported event types:
- `deposit`
- `withdraw`
- `transfer`

**Request JSON:**
```json
   {
      "type": "deposit",
      "destination": "100",
      "amount": 10
   }
```
## Success Response (201 Created):
```json
   {
      "destination": {
      "id": "100",
      "balance": 10
      }
   }
```

## Error Responses:

- 400 Bad Request - Invalid input data
- 404 Not Found - Account not found or insufficient funds

### `GET /balance?account_id=100`

Returns the current balance of the specified account.

- Success Response (200 OK): 10
- Error Response (404 Not Found): 0

### `POST /reset`
Resets all account data to initial state (empty).
Used for test scenarios.

- Success Response (200 OK): OK

## Validation Rules
- amount must be greater than zero
- Account IDs must not be null, blank, or contain spaces
- Transfers cannot occur between the same account
- Withdrawals cannot exceed the account balance

## Further improvements
* Add a log system.
* Use a database to manage the data.

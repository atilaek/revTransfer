package io.swagger.api;

import io.swagger.api.model.Transaction;
import io.swagger.api.service.AccountService;
import io.swagger.api.service.TransferService;
import io.swagger.api.service.exception.AccountNotFoundException;
import io.swagger.api.service.exception.InsufficientFundsException;
import io.swagger.api.service.impl.AccountServiceImpl;
import io.swagger.api.service.impl.TransferServiceImpl;
import io.swagger.api.service.repository.AccountDb;
import io.swagger.api.web.impl.AccountApiServiceImpl;
import io.swagger.api.web.impl.TransferApiServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TransferApiServiceTest {

    private AccountDb accountDb = new AccountDb();
    private TransferService transferService = new TransferServiceImpl(accountDb);
    private TransferApiService transferApiServiceTest = new TransferApiServiceImpl(transferService);
    private AccountService accountService = new AccountServiceImpl(accountDb);
    private AccountApiService accountApiServiceTest = new AccountApiServiceImpl(accountService);

    @Test
    void makeTransfer_ShouldCompleteTransfer() throws AccountNotFoundException, InsufficientFundsException {
        transferApiServiceTest.makeTransfer(createDefaultTransaction());

        final Response fromAccountGetAccountResponse = accountApiServiceTest.getAccountById(createDefaultTransaction().getFromAccountId());
        assertTrue(((ApiResponseMessage) fromAccountGetAccountResponse.getEntity()).getMessage()
                .contains("balance: 0"));

        final Response toAccountGetAccountResponse = accountApiServiceTest.getAccountById(createDefaultTransaction().getToAccountId());
        assertTrue(((ApiResponseMessage) toAccountGetAccountResponse.getEntity()).getMessage()
                .contains("balance: 100"));
    }

    @Test
    void makeTransfer_ShouldThrowException_WhenFromAccountIsNotFound() {
        Transaction transaction = createDefaultTransaction();
        transaction.setFromAccountId(444);
        Assertions.assertThrows(AccountNotFoundException.class, () -> {
            transferApiServiceTest.makeTransfer(transaction);
        });
    }

    @Test
    void makeTransfer_ShouldThrowException_WhenToAccountIsNotFound() {
        Transaction transaction = createDefaultTransaction();
        transaction.setToAccountId(555);
        Assertions.assertThrows(AccountNotFoundException.class, () -> {
            transferApiServiceTest.makeTransfer(transaction);
        });
    }

    @Test
    void makeTransfer_ShouldThrowException_WhenFromAccountHasInsufficientBalance() {
        Transaction transaction = createDefaultTransaction();
        transaction.setAmount(50000);
        Assertions.assertThrows(InsufficientFundsException.class, () -> {
            transferApiServiceTest.makeTransfer(transaction);
        });
    }

    private Transaction createDefaultTransaction() {
        Transaction transaction = new Transaction();
        transaction.setId(1);
        transaction.setAmount(100);
        transaction.fromAccountId(102);
        transaction.toAccountId(101);
        return transaction;
    }
}
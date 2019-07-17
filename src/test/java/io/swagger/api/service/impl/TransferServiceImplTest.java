package io.swagger.api.service.impl;

import io.swagger.api.model.Transaction;
import io.swagger.api.service.AccountService;
import io.swagger.api.service.TransferService;
import io.swagger.api.service.exception.AccountNotFoundException;
import io.swagger.api.service.exception.InsufficientFundsException;
import io.swagger.api.service.repository.AccountDb;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

class TransferServiceImplTest {

    private AccountDb accountDb = new AccountDb();
    private TransferService transferServiceTest = new TransferServiceImpl(accountDb);
    private AccountService accountService = new AccountServiceImpl(accountDb);

    @Test
    void makeTransfer_ShouldCompleteTransfer() throws AccountNotFoundException, InsufficientFundsException {
        transferServiceTest.makeTransfer(createDefaultTransaction());
        assertEquals(100,
                accountService.getAccountById(createDefaultTransaction().getToAccountId()).getBalance());
        assertEquals(0,
                accountService.getAccountById(createDefaultTransaction().getFromAccountId()).getBalance());
    }

    @Test
    void makeTransfer_ShouldThrowException_WhenFromAccountIsNotFound() {
        Transaction transaction = createDefaultTransaction();
        transaction.setFromAccountId(444);
        Assertions.assertThrows(AccountNotFoundException.class, () -> {
            transferServiceTest.makeTransfer(transaction);
        });
    }

    @Test
    void makeTransfer_ShouldThrowException_WhenToAccountIsNotFound() {
        Transaction transaction = createDefaultTransaction();
        transaction.setToAccountId(555);
        Assertions.assertThrows(AccountNotFoundException.class, () -> {
            transferServiceTest.makeTransfer(transaction);
        });
    }

    @Test
    void makeTransfer_ShouldThrowException_WhenFromAccountHasInsufficientBalance() {
        Transaction transaction = createDefaultTransaction();
        transaction.setAmount(50000);
        Assertions.assertThrows(InsufficientFundsException.class, () -> {
            transferServiceTest.makeTransfer(transaction);
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
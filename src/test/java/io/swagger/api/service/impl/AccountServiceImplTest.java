package io.swagger.api.service.impl;

import io.swagger.api.model.Account;
import io.swagger.api.service.AccountService;
import io.swagger.api.service.exception.AccountNotFoundException;
import io.swagger.api.service.exception.InsufficientFundsException;
import io.swagger.api.service.repository.AccountDb;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class AccountServiceImplTest {

    private AccountDb accountDb = new AccountDb();
    private AccountService accountServiceTest = new AccountServiceImpl(accountDb);

    @Test
    void createAccount_ShouldCreateAccount() {
        final Account newAccount = new Account(204, "Can Dundar", 0L);
        accountServiceTest.createAccount(newAccount);
    }

    @Test
    void createAccount_ShouldCreateAccount_WithNewAccountId() {
        int accountId = 798;
        final Account newAccount = new Account(accountId, "Bjornar Moxnes", 400L);
        int registeredAccountID = accountServiceTest.createAccount(newAccount);
        assertNotEquals(accountId, registeredAccountID);
    }

    @Test
    void deleteAccount_ShouldDeleteAccount() throws AccountNotFoundException {
        accountServiceTest.deleteAccount(101);

        Assertions.assertThrows(AccountNotFoundException.class, () -> {
            accountServiceTest.getAccountById(101);
        });
    }

    @Test
    void deleteAccount_ShouldThrowException_IfAccountDoesNotExist() throws AccountNotFoundException {
        Assertions.assertThrows(AccountNotFoundException.class, () -> {
            accountServiceTest.deleteAccount(555);
        });
    }

    @Test
    void getAccountById_ShouldReturnAccount() throws AccountNotFoundException {
        assertEquals("Agata Borek",
                accountServiceTest.getAccountById(102).getName());
    }

    @Test
    void getAccountById_ShouldThrowException_IfAccountDoesNotExist() {
        Assertions.assertThrows(AccountNotFoundException.class, () -> {
            accountServiceTest.getAccountById(555);
        });
    }

    @Test
    void updateAccount_ShouldUpdateAccount() throws AccountNotFoundException {
        Account updatedAccount = new Account(103, "Zlatan Ibrahimovic", 400);
        accountServiceTest.updateAccount(updatedAccount);
        assertEquals("Zlatan Ibrahimovic",
                accountServiceTest.getAccountById(103).getName());
    }

    @Test
    void updateAccount_ShouldThrowException_IfAccountDoesNotExist() {
        Account updatedAccount = new Account(555, "Zlatan Ibrahimovic", 400);
        Assertions.assertThrows(AccountNotFoundException.class, () -> {
            accountServiceTest.updateAccount(updatedAccount);
        });
    }

    @Test
    void depositToAccount_ShouldAddAmountToBalance() throws AccountNotFoundException {
        accountServiceTest.depositToAccount(104, 5000);
        assertEquals(6000,
                accountServiceTest.getAccountById(104).getBalance());
    }

    @Test
    void depositToAccount_ShouldThrowException_IfAccountDoesNotExist() {
        Assertions.assertThrows(AccountNotFoundException.class, () -> {
            accountServiceTest.depositToAccount(555, 10000);
        });
    }

    @Test
    void withdrawFromAccount_ShouldWithdrawFromAccount() throws AccountNotFoundException, InsufficientFundsException {
        accountServiceTest.withdrawFromAccount(105, 50);
        assertEquals(4950,
                accountServiceTest.getAccountById(105).getBalance());
    }

    @Test
    void withdrawFromAccount_ShouldThrowException_IfAccountDoesNotExist() {
        Assertions.assertThrows(AccountNotFoundException.class, () -> {
            accountServiceTest.withdrawFromAccount(555, 10000);
        });
    }

    @Test
    void withdrawFromAccount_ShouldThrowException_IfBalanceIsLowerThanWithdrawAmount() {
        Assertions.assertThrows(InsufficientFundsException.class, () -> {
            accountServiceTest.withdrawFromAccount(105, 10000);
        });
    }

}
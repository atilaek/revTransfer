package io.swagger.api;

import io.swagger.api.model.Account;
import io.swagger.api.service.AccountService;
import io.swagger.api.service.exception.AccountNotFoundException;
import io.swagger.api.service.exception.InsufficientFundsException;
import io.swagger.api.service.impl.AccountServiceImpl;
import io.swagger.api.service.repository.AccountDb;
import io.swagger.api.web.impl.AccountApiServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.*;

class AccountApiServiceTest {

    private AccountDb accountDb = new AccountDb();
    private AccountService accountService = new AccountServiceImpl(accountDb);
    private AccountApiService accountApiServiceTest = new AccountApiServiceImpl(accountService);

    @Test
    void createAccount_ShouldCreateAccount() {
        final int defaultAccountId = 204;
        final Account newAccount = new Account(defaultAccountId, "Can Dundar", 0L);
        final Response createAccountResponse = accountApiServiceTest.createAccount(newAccount);
        final String createdAccountIdString = ((ApiResponseMessage) createAccountResponse.getEntity()).getMessage();
        assertNotEquals(defaultAccountId, Integer.valueOf(createdAccountIdString));
    }

    @Test
    void createAccount_ShouldCreateAccount_WithNewAccountId() throws AccountNotFoundException {
        final int accountId = 798;
        final Account newAccount = new Account(accountId, "Bjornar Moxnes", 400L);
        final Response createAccountResponse = accountApiServiceTest.createAccount(newAccount);
        assertEquals(ApiResponseMessage.OK,
                ((ApiResponseMessage) createAccountResponse.getEntity()).getCode());
        final String createdAccountIdString = ((ApiResponseMessage) createAccountResponse.getEntity()).getMessage();
        final Response getAccountResponse = accountApiServiceTest.getAccountById(Integer.valueOf(createdAccountIdString));

        assertTrue(((ApiResponseMessage) getAccountResponse.getEntity()).getMessage()
                .contains("Bjornar Moxnes"));

        assertFalse(((ApiResponseMessage) getAccountResponse.getEntity()).getMessage()
                .contains(String.valueOf(accountId)));
    }

    @Test
    void deleteAccount_ShouldDeleteAccount() throws AccountNotFoundException {
        accountApiServiceTest.deleteAccount(101);

        Assertions.assertThrows(AccountNotFoundException.class, () -> {
            accountApiServiceTest.getAccountById(101);
        });
    }

    @Test
    void deleteAccount_ShouldThrowException_IfAccountDoesNotExist() throws AccountNotFoundException {
        Assertions.assertThrows(AccountNotFoundException.class, () -> {
            accountApiServiceTest.deleteAccount(555);
        });
    }

    @Test
    void getAccountById_ShouldReturnAccount() throws AccountNotFoundException {
        final Response getAccountResponse = accountApiServiceTest.getAccountById(102);
        assertTrue(((ApiResponseMessage) getAccountResponse.getEntity()).getMessage()
                .contains("Agata Borek"));
    }

    @Test
    void getAccountById_ShouldThrowException_IfAccountDoesNotExist() {
        Assertions.assertThrows(AccountNotFoundException.class, () -> {
            accountApiServiceTest.getAccountById(555);
        });
    }

    @Test
    void updateAccount_ShouldUpdateAccount() throws AccountNotFoundException {
        Account updatedAccount = new Account(103, "Zlatan Ibrahimovic", 400);
        accountApiServiceTest.updateAccount(updatedAccount);
        final Response getAccountResponse = accountApiServiceTest.getAccountById(103);
        assertTrue(((ApiResponseMessage) getAccountResponse.getEntity()).getMessage()
                .contains("Zlatan Ibrahimovic"));
    }

    @Test
    void updateAccount_ShouldThrowException_IfAccountDoesNotExist() {
        Account updatedAccount = new Account(555, "Zlatan Ibrahimovic", 400);
        Assertions.assertThrows(AccountNotFoundException.class, () -> {
            accountApiServiceTest.updateAccount(updatedAccount);
        });
    }

    @Test
    void depositToAccount_ShouldAddAmountToBalance() throws AccountNotFoundException {
        accountApiServiceTest.depositToAccount(104, 5000);
        final Response getAccountResponse = accountApiServiceTest.getAccountById(104);
        assertTrue(((ApiResponseMessage) getAccountResponse.getEntity()).getMessage()
                .contains("6000"));
    }

    @Test
    void depositToAccount_ShouldThrowException_IfAccountDoesNotExist() {
        Assertions.assertThrows(AccountNotFoundException.class, () -> {
            accountApiServiceTest.depositToAccount(555, 10000);
        });
    }

    @Test
    void withdrawFromAccount_ShouldWithdrawFromAccount() throws AccountNotFoundException, InsufficientFundsException {
        accountApiServiceTest.withdrawFromAccount(105, 50);
        final Response getAccountResponse = accountApiServiceTest.getAccountById(105);
        assertTrue(((ApiResponseMessage) getAccountResponse.getEntity()).getMessage()
                .contains("4900"));
    }

    @Test
    void withdrawFromAccount_ShouldThrowException_IfAccountDoesNotExist() {
        Assertions.assertThrows(AccountNotFoundException.class, () -> {
            accountApiServiceTest.withdrawFromAccount(555, 10000);
        });
    }

    @Test
    void withdrawFromAccount_ShouldThrowException_IfBalanceIsLowerThanWithdrawAmount() {
        Assertions.assertThrows(InsufficientFundsException.class, () -> {
            accountApiServiceTest.withdrawFromAccount(105, 10000);
        });
    }
}
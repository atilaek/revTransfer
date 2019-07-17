package io.swagger.api.service;


import io.swagger.api.model.Account;
import io.swagger.api.service.exception.AccountNotFoundException;
import io.swagger.api.service.exception.InsufficientFundsException;


/**
 * AccountService for account related operations.
 *
 * @author Atila Ekimci
 */
public interface AccountService {

    int createAccount(final Account account);

    void deleteAccount(final int accountId) throws AccountNotFoundException;

    Account getAccountById(final int accountId) throws AccountNotFoundException;

    void updateAccount(final Account account) throws AccountNotFoundException;

    Account depositToAccount(final int accountId, final long amount) throws AccountNotFoundException;

    /**
     * Withdraws a given amount from an account.
     *
     * @param accountId accountId of the account
     * @param amount    amount to be withdrawn
     * @return the new balance of account.
     */
    Account withdrawFromAccount(final int accountId, final long amount) throws AccountNotFoundException, InsufficientFundsException;

}

package io.swagger.api.service.impl;

import io.swagger.api.model.Account;
import io.swagger.api.service.AccountService;
import io.swagger.api.service.exception.AccountNotFoundException;
import io.swagger.api.service.exception.InsufficientFundsException;
import io.swagger.api.service.repository.AccountDb;


/**
 * AccountService for account related operations.<p>
 * Uses @{@link AccountDb} as repository and performs operations
 * such as withdraw.
 *
 * @author Atila Ekimci
 */
public class AccountServiceImpl implements AccountService {

    private AccountDb accountDb;

    public AccountServiceImpl(AccountDb accountDb) {
        this.accountDb = accountDb;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int createAccount(Account account) {
        Integer accountId = accountDb.getAccounts().lastKey() + 1;
        accountDb.getAccounts().put(accountId, new Account(accountId, account.getName(),
                account.getBalance()));
        return accountId.intValue();
    }

    @Override
    public void deleteAccount(int accountId) throws AccountNotFoundException {
        if (!accountExists(accountId)) {
            throw new AccountNotFoundException("Account with Account id:" + accountId + " is not found!");
        }
        accountDb.getAccounts().remove(accountId);
    }

    @Override
    public Account getAccountById(int accountId) throws AccountNotFoundException {
        if (!accountExists(accountId)) {
            throw new AccountNotFoundException("Account with Account id:'" + accountId + "' is not found!");
        }
        return accountDb.getAccounts().get(accountId);
    }

    @Override
    public void updateAccount(Account account) throws AccountNotFoundException {
        Account existingAccount = getAccountById(account.getId());
        accountDb.getAccounts().put(existingAccount.getId(),
                new Account(existingAccount.getId(), account.getName(), account.getId()));
    }

    @Override
    public Account depositToAccount(int accountId, long amount) throws AccountNotFoundException {
        Account existingAccount = getAccountById(accountId);
        accountDb.getAccounts().get(accountId).
                setBalance(existingAccount.getBalance() + amount);
        return getAccountById(accountId);
    }

    @Override
    public Account withdrawFromAccount(int accountId, long amount) throws InsufficientFundsException, AccountNotFoundException {
        Account existingAccount = getAccountById(accountId);
        final long balance = existingAccount.getBalance();
        if (amount > balance) {
            throw new InsufficientFundsException("Withdraw amount is more than current balance! "
                    + "Balance='" + balance + "', requested amount = " + amount + "!");
        } else {
            final long newBalance = balance - amount;
            accountDb.getAccounts().get(accountId).setBalance(newBalance);
            return getAccountById(accountId);
        }
    }

    private boolean accountExists(final int accountId) {
        return accountDb.getAccounts().containsKey(accountId);
    }

}

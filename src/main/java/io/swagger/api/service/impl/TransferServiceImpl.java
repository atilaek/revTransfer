package io.swagger.api.service.impl;

import io.swagger.api.model.Account;
import io.swagger.api.model.Transaction;
import io.swagger.api.service.TransferService;
import io.swagger.api.service.exception.AccountNotFoundException;
import io.swagger.api.service.exception.InsufficientFundsException;
import io.swagger.api.service.repository.AccountDb;


/**
 * Transfer Service for transaction operations.<p>
 *
 * @author Atila Ekimci
 */
public class TransferServiceImpl implements TransferService {

    private AccountDb accountDb;

    public TransferServiceImpl(AccountDb accountDb) {
        this.accountDb = accountDb;
    }

    private Account getAccountById(int accountId) throws AccountNotFoundException {
        if (!accountExists(accountId)) {
            throw new AccountNotFoundException("Account with Account id:'" + accountId + "' is not found!");
        }
        return accountDb.getAccounts().get(accountId);
    }

    private boolean accountExists(final int accountId) {
        return accountDb.getAccounts().containsKey(accountId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void makeTransfer(Transaction transfer) throws AccountNotFoundException, InsufficientFundsException {
        Account fromAccount = getAccountById(transfer.getFromAccountId());
        final long balance = fromAccount.getBalance();
        if (transfer.getAmount() > balance) {
            throw new InsufficientFundsException("Withdraw amount is more than current balance! "
                    + "Balance='" + balance + "', requested amount = " + transfer.getAmount() + "!");
        } else {
            final long newBalance = balance - transfer.getAmount();
            accountDb.getAccounts().get(transfer.getFromAccountId()).setBalance(newBalance);

            Account toAccount = getAccountById(transfer.getToAccountId());
            accountDb.getAccounts().get(transfer.getToAccountId()).
                    setBalance(toAccount.getBalance() + transfer.getAmount());
        }
    }
}

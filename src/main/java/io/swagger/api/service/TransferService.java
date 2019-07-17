package io.swagger.api.service;


import io.swagger.api.model.Transaction;
import io.swagger.api.service.exception.AccountNotFoundException;
import io.swagger.api.service.exception.InsufficientFundsException;


/**
 * TransferService for transfer related operations.
 *
 * @author Atila Ekimci
 */
public interface TransferService {

    /**
     * Transfer a given amount from an account to another.
     *
     * @param transaction account to account money transfer
     *                   transaction object containigof certain amount
     * @exception AccountNotFoundException if account does not exist.
     * @exception InsufficientFundsException if amount is higher
     * than from account's balance.
     */
    void makeTransfer(final Transaction transaction) throws AccountNotFoundException, InsufficientFundsException;

}

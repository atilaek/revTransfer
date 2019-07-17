package io.swagger.api;

import io.swagger.api.model.Transaction;
import io.swagger.api.service.exception.AccountNotFoundException;
import io.swagger.api.service.exception.InsufficientFundsException;

import javax.ws.rs.core.Response;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2019-07-15T10:22:46.842Z[GMT]")
public abstract class TransferApiService {
    /**
     * Transfer a given amount from an account to another.
     *
     * @param transaction account to account money transfer
     *                   transaction object containigof certain amount
     * @exception AccountNotFoundException if account does not exist.
     * @exception InsufficientFundsException if amount is higher
     * than from account's balance.
     */
    public abstract Response makeTransfer(Transaction transaction) throws AccountNotFoundException, InsufficientFundsException;
}

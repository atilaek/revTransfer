package io.swagger.api;

import io.swagger.api.model.Account;
import io.swagger.api.service.exception.AccountNotFoundException;
import io.swagger.api.service.exception.InsufficientFundsException;

import javax.ws.rs.core.Response;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2019-07-15T10:22:46.842Z[GMT]")
public abstract class AccountApiService {
    public abstract Response createAccount(Account body);

    public abstract Response deleteAccount(int accountId) throws AccountNotFoundException;

    public abstract Response depositToAccount(int accountId, long amount) throws AccountNotFoundException;

    public abstract Response getAccountById(int accountId) throws AccountNotFoundException;

    public abstract Response updateAccount(Account body) throws AccountNotFoundException;

    public abstract Response withdrawFromAccount(int accountId, long amount) throws AccountNotFoundException, InsufficientFundsException;
}

package io.swagger.api.web.impl;

import io.swagger.api.AccountApiService;
import io.swagger.api.ApiResponseMessage;
import io.swagger.api.model.Account;
import io.swagger.api.service.AccountService;
import io.swagger.api.service.exception.AccountNotFoundException;
import io.swagger.api.service.exception.InsufficientFundsException;

import javax.ws.rs.core.Response;


@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2019-07-15T10:22:46.842Z[GMT]")
public class AccountApiServiceImpl extends AccountApiService {

    private AccountService accountService;

    public AccountApiServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public Response createAccount(Account account) {
        final int accountId = accountService.createAccount(account);
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK,
                String.valueOf(accountId)
        )).build();
    }

    @Override
    public Response deleteAccount(int accountId) throws AccountNotFoundException {
        accountService.deleteAccount(accountId);
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK,
                "Account created with account id:" + accountId + "!!!"
        )).build();
    }

    @Override
    public Response depositToAccount(int accountId, long amount) throws AccountNotFoundException {
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK,
                accountService.depositToAccount(accountId, amount).toString()
        )).build();
    }

    @Override
    public Response getAccountById(int accountId) throws AccountNotFoundException {
        // do some magic!
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK,
                accountService.getAccountById(accountId).toString())
        ).build();
    }

    @Override
    public Response updateAccount(Account account) throws AccountNotFoundException {
        accountService.updateAccount(account);
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK,
                "Account updated!"
        )).build();
    }

    @Override
    public Response withdrawFromAccount(int accountId, long amount) throws AccountNotFoundException, InsufficientFundsException {
        accountService.withdrawFromAccount(accountId, amount).toString();
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK,
                accountService.withdrawFromAccount(accountId, amount).toString()
        )).build();
    }
}

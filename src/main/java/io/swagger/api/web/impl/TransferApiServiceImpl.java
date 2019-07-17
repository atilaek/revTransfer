package io.swagger.api.web.impl;

import io.swagger.api.ApiResponseMessage;
import io.swagger.api.TransferApiService;
import io.swagger.api.model.Transaction;
import io.swagger.api.service.TransferService;
import io.swagger.api.service.exception.AccountNotFoundException;
import io.swagger.api.service.exception.InsufficientFundsException;

import javax.ws.rs.core.Response;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2019-07-15T10:22:46.842Z[GMT]")
public class TransferApiServiceImpl extends TransferApiService {

    private TransferService transferService;

    public TransferApiServiceImpl(TransferService transferService) {
        this.transferService = transferService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response makeTransfer(Transaction transaction) throws AccountNotFoundException, InsufficientFundsException {
        transferService.makeTransfer(transaction);
        return Response.ok().entity(new ApiResponseMessage(ApiResponseMessage.OK, "Transfer completed!")).build();
    }
}

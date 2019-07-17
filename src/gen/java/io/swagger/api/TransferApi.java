package io.swagger.api;

import io.swagger.api.model.Transaction;
import io.swagger.api.service.exception.AccountNotFoundException;
import io.swagger.api.service.exception.InsufficientFundsException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import javax.servlet.ServletConfig;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;


@Path("/transfer")


@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2019-07-15T10:22:46.842Z[GMT]")
public class TransferApi {
    private final TransferApiService delegate;

    public TransferApi(@Context ServletConfig servletContext) {
        TransferApiService delegate = null;

        if (servletContext != null) {
            String implClass = servletContext.getInitParameter("TransferApi.implementation");
            if (implClass != null && !"".equals(implClass.trim())) {
                try {
                    delegate = (TransferApiService) Class.forName(implClass).newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        this.delegate = delegate;
    }

    @POST
    @Path("/")
    @Consumes({"application/json"})

    @Operation(summary = "Create a new transfer transaction between two accounts", description = "", tags = {"Transaction"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "405", description = "Invalid input")})
    public Response makeTransfer(@Parameter(description = "Transaction object for account balance related actions", required = true) Transaction body)
            throws AccountNotFoundException, InsufficientFundsException {
        return delegate.makeTransfer(body);
    }
}

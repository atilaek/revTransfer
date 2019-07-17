package io.swagger.api;

import io.swagger.api.model.Account;
import io.swagger.api.service.exception.AccountNotFoundException;
import io.swagger.api.service.exception.InsufficientFundsException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import javax.servlet.ServletConfig;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;


@Path("/account")


@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2019-07-15T10:22:46.842Z[GMT]")
public class AccountApi {
    private final AccountApiService delegate;

    public AccountApi(@Context ServletConfig servletContext) {
        AccountApiService delegate = null;

        if (servletContext != null) {
            String implClass = servletContext.getInitParameter("AccountApi.implementation");
            if (implClass != null && !"".equals(implClass.trim())) {
                try {
                    delegate = (AccountApiService) Class.forName(implClass).newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }


        this.delegate = delegate;
    }

    @POST

    @Consumes({"application/json"})

    @Operation(summary = "Create a new account", description = "", tags = {"Account"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "405", description = "Invalid input")})
    public Response createAccount(@Parameter(description = "Account Object owned by a User", required = true) Account body)
            throws ApiException {
        return delegate.createAccount(body);
    }

    @DELETE
    @Path("/{accountId}")


    @Operation(summary = "Deletes an account", description = "", tags = {"Account"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),

            @ApiResponse(responseCode = "404", description = "Account not found")})
    public Response deleteAccount(@Parameter(description = "Account id to delete", required = true) @PathParam("accountId") int accountId
    )
            throws AccountNotFoundException {
        return delegate.deleteAccount(accountId);
    }

    @PUT
    @Path("/{accountId}/deposit/{amount}")

    @Produces({"application/json"})
    @Operation(summary = "Deposit to Account by ID and Amount", description = "Returns a single Account", tags = {"Account"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Account.class))),

            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),

            @ApiResponse(responseCode = "404", description = "Account not found")})
    public Response depositToAccount(@Parameter(description = "ID of Account to return", required = true) @PathParam("accountId") int accountId
            , @Parameter(description = "Amount to deposit to the balance of an account", required = true) @PathParam("amount") long amount
    )
            throws AccountNotFoundException {
        return delegate.depositToAccount(accountId, amount);
    }

    @GET
    @Path("/{accountId}")

    @Produces({"application/json"})
    @Operation(summary = "Find Account by ID", description = "Returns a single Account", tags = {"Account"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Account.class))),

            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),

            @ApiResponse(responseCode = "404", description = "Account not found")})
    public Response getAccountById(@Parameter(description = "ID of Account to return", required = true) @PathParam("accountId") int accountId
    )
            throws AccountNotFoundException {
        return delegate.getAccountById(accountId);
    }

    @PUT

    @Consumes({"application/json"})

    @Operation(summary = "Update an existing Account", description = "", tags = {"Account"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),

            @ApiResponse(responseCode = "404", description = "Account not found"),

            @ApiResponse(responseCode = "405", description = "Validation exception")})
    public Response updateAccount(@Parameter(description = "Account Object owned by a User", required = true) Account body

    )
            throws AccountNotFoundException {
        return delegate.updateAccount(body);
    }

    @PUT
    @Path("/{accountId}/withdraw/{amount}")

    @Produces({"application/json"})
    @Operation(summary = "Withdraw From Account by ID and Amount", description = "Returns a single Account", tags = {"Account"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Account.class))),

            @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),

            @ApiResponse(responseCode = "404", description = "Account not found")})
    public Response withdrawFromAccount(@Parameter(description = "ID of Account to return", required = true) @PathParam("accountId") int accountId
            , @Parameter(description = "Amount to withdraw from the balance of an account", required = true) @PathParam("amount") long amount
    )
            throws AccountNotFoundException, InsufficientFundsException {
        return delegate.withdrawFromAccount(accountId, amount);
    }
}

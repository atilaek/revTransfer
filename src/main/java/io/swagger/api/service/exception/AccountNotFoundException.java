package io.swagger.api.service.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


/**
 * Exception throwing class for giving controlled exceptions
 * if account looked for does not exist.<p>
 * (e.g. "Account with account number = XX is not found!").
 *
 * @author Atila Ekimci
 */


@Provider
public class AccountNotFoundException extends Exception implements
        ExceptionMapper<AccountNotFoundException> {
    private static final long serialVersionUID = 1L;

    public AccountNotFoundException() {
        super("No Account found with given id!!");
    }

    public AccountNotFoundException(String string) {
        super(string);
    }

    @Override
    public Response toResponse(AccountNotFoundException exception) {
        return Response.status(404).entity(exception.getMessage())
                .type("text/plain").build();
    }
}

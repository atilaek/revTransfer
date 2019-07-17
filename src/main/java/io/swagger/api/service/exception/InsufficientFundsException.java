package io.swagger.api.service.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


/**
 * Exception throwing class for giving controlled exceptions
 * if amount is bigger than the balance of account.<p>
 * (e.g. "Account balance is insufficient!!").
 *
 * @author Atila Ekimci
 */


@Provider
public class InsufficientFundsException extends Exception implements
        ExceptionMapper<InsufficientFundsException> {
    private static final long serialVersionUID = 1L;

    public InsufficientFundsException() {
        super("Account balance is insufficient!!");
    }

    public InsufficientFundsException(String string) {
        super(string);
    }

    @Override
    public Response toResponse(InsufficientFundsException exception) {
        return Response.status(406).entity(exception.getMessage())
                .type("text/plain").build();
    }
}

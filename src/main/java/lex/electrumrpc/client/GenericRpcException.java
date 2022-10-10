package lex.electrumrpc.client;

@SuppressWarnings("serial")
public class GenericRpcException extends RuntimeException {

    public GenericRpcException() {
    }

    public GenericRpcException(String msg) {
        super(msg);
    }

    public GenericRpcException(Throwable cause) {
        super(cause);
    }

    public GenericRpcException(String message, Throwable cause) {
        super(message, cause);
    }

}

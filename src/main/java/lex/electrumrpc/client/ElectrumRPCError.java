package lex.electrumrpc.client;
import java.util.Map;

public class ElectrumRPCError {
    private int code;
    private String message;
    private Object id;

    @SuppressWarnings({ "rawtypes" })
    public ElectrumRPCError(Map errorResponse) {
        this.id = errorResponse.get("id");
        Map error = (Map)errorResponse.get("error");
        if (error != null) {
          Number n = (Number) error.get("code");
          this.code    = n != null ? n.intValue() : 0;
          this.message = (String) error.get("message");
        }
    }
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getId(){
        return id;
    }
}

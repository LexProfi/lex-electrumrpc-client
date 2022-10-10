package lex.electrumrpc.client;

import java.util.Map;

import lex.electrumrpc.krotjson.JSON;
@SuppressWarnings("serial")
public class ElectrumRPCException extends GenericRpcException {
  
  private String rpcMethod;
  private String rpcParams;
  private int responseCode;
  private String responseMessage;
  private String response;
  private ElectrumRPCError rpcError;

  @SuppressWarnings("rawtypes")
  public ElectrumRPCException(String method,
                              String params,
                              int    responseCode,
                              String responseMessage,
                              String response) {
    super("RPC Query Failed (method: " + method + ", params: " + params + ", response code: " + responseCode + " responseMessage " + responseMessage + ", response: " + response);
    this.rpcMethod = method;
    this.rpcParams = params;
    this.responseCode = responseCode;
    this.responseMessage = responseMessage;
    this.response = response;
    if ( responseCode == 500 ) {
        Map error = (Map) ((Map)JSON.parse(response)).get("error");
        if ( error != null ) {
            rpcError = new ElectrumRPCError(error);
        }
    }
  }
  
  public ElectrumRPCException(String method, String params, Throwable cause) {
    super("RPC Query Failed (method: " + method + ", params: " + params + ")", cause);
    this.rpcMethod = method;
    this.rpcParams = params;
  }

  public ElectrumRPCException(String msg) {
    super(msg);
  }

  public ElectrumRPCException(ElectrumRPCError error) {
      super(error.getMessage());
      this.rpcError = error;
  }
  
  public ElectrumRPCException(String message, Throwable cause) {
    super(message, cause);
  }

  public int getResponseCode() {
    return responseCode;
  }

  public String getRpcMethod() {
    return rpcMethod;
  }

  public String getRpcParams() {
    return rpcParams;
  }

  public String getResponseMessage() {
    return responseMessage;
  }

  public String getResponse() {
      return this.response;
  }

  public ElectrumRPCError getRPCError() {
      return this.rpcError;
  }
}

package lex.electrumrpc.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import lex.electrumrpc.client.wrapers.*;
import lex.electrumrpc.krotjson.Base64Coder;
import lex.electrumrpc.krotjson.JSON;


public class ElectrumJSONRPCClient implements ElectrumRpcClient {

  private static final Logger logger = Logger.getLogger(ElectrumRpcClient.class.getPackage().getName());

  public static URL DEFAULT_JSONRPC_URL;
  public static URL DEFAULT_JSONRPC_TESTNET_URL;

  public static final Charset QUERY_CHARSET = Charset.forName("ISO8859-1");
  public static final int CONNECT_TIMEOUT = (int) TimeUnit.MINUTES.toMillis(1);

  public final URL rpcURL;

  private URL noAuthURL;
  private String authStr;

  public int readTimeout = (int) TimeUnit.MINUTES.toMillis(5);

  public ElectrumJSONRPCClient(String rpcUrl) throws MalformedURLException {
    this(new URL(rpcUrl));
  }

  public ElectrumJSONRPCClient(URL rpc) {
    this.rpcURL = rpc;
    try {
      noAuthURL = new URI(rpc.getProtocol(), null, rpc.getHost(), rpc.getPort(), rpc.getPath(), rpc.getQuery(), null).toURL();
    } catch (MalformedURLException | URISyntaxException ex) {
      throw new IllegalArgumentException(rpc.toString(), ex);
    }
    authStr = rpc.getUserInfo() == null ? null : String.valueOf(Base64Coder.encode(rpc.getUserInfo().getBytes(Charset.forName("ISO8859-1"))));
  }

  public ElectrumJSONRPCClient(boolean testNet) {
    this(testNet ? DEFAULT_JSONRPC_TESTNET_URL : DEFAULT_JSONRPC_URL);
  }

  public ElectrumJSONRPCClient() {
    this(DEFAULT_JSONRPC_TESTNET_URL);
  }

  @SuppressWarnings("serial")
  protected byte[] prepareRequest(final String method, final Object... params) {
    return JSON.stringify(new LinkedHashMap<String, Object>() {
      {
        put("method", method);
        put("params", params);
        put("id", "1");
      }
    }).getBytes(QUERY_CHARSET);
  }

  @SuppressWarnings("serial")
  protected byte[] prepareBatchRequest(final String method, final List<BatchParam> paramsList) {
    return JSON.stringify(paramsList.stream().map(batchParam-> new LinkedHashMap<String, Object>() {
      {
        put("method", method);
        put("params", batchParam.params);
        put("id", batchParam.id);
      }
    }).collect(Collectors.toList())).getBytes(QUERY_CHARSET);
  }

  private static byte[] loadStream(InputStream in, boolean close) throws IOException {
    ByteArrayOutputStream o = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024];
    for (;;) {
      int nr = in.read(buffer);

      if (nr == -1)
        break;
      if (nr == 0)
        throw new IOException("Read timed out");

      o.write(buffer, 0, nr);
    }
    return o.toByteArray();
  }

  @SuppressWarnings("rawtypes")
  public Object loadResponse(InputStream in, Object expectedID, boolean close) throws IOException, lex.electrumrpc.client.GenericRpcException {
    try {
      String r = new String(loadStream(in, close), QUERY_CHARSET);
      logger.log(Level.FINE, "Electrum JSON-RPC response:\n{0}", r);
      try {
        Map response = (Map) JSON.parse(r);

        return getResponseObject(expectedID, response);
      } catch (ClassCastException ex) {
        throw new ElectrumRPCException("Invalid server response format (data: \"" + r + "\")");
      }
    } finally {
      if (close)
        in.close();
    }
  }

    @SuppressWarnings("rawtypes")
    public Object loadBatchResponse(InputStream in, List<BatchParam> batchParams, boolean close) throws IOException,lex.electrumrpc.client.GenericRpcException {
        try {
            String r = new String(loadStream(in, close), QUERY_CHARSET);
            logger.log(Level.FINE, "Electrum JSON-RPC response:\n{0}", r);
            try {
                List<Map> response = (List<Map>) JSON.parse(r);

                return response.stream().map(item-> {
                  try {
                    Object expectedId = batchParams.stream()
                            .filter(batchParam -> batchParam.id.equals(item.get("id")))
                            .findFirst().orElseGet(()->new BatchParam(null, null)).id;
                    return getResponseObject(expectedId, item);
                  } catch (ElectrumRPCException e) {
                    return e;
                  }
                  }).collect(Collectors.toList());
            } catch (ClassCastException ex) {
                throw new ElectrumRPCException("Invalid server response format (data: \"" + r + "\")");
            }
        } finally {
            if (close)
                in.close();
        }
    }

  private Object getResponseObject(Object expectedID, Map response) {
    if (!expectedID.equals(response.get("id")))
      throw new ElectrumRPCException("Wrong response ID (expected: " + String.valueOf(expectedID) + ", response: " + response.get("id") + ")");

    if (response.get("error") != null)
      throw new ElectrumRPCException(new ElectrumRPCError(response));

    return response.get("result");
  }

  /**
   * Set an authenticated connection with Electrum Daemon
   */
  private HttpURLConnection setConnection() {
    HttpURLConnection conn;
    try {
      conn = (HttpURLConnection) noAuthURL.openConnection();
      conn.setDoOutput(true);
      conn.setDoInput(true);
      conn.setConnectTimeout(CONNECT_TIMEOUT);
      conn.setReadTimeout(readTimeout);
      conn.setRequestProperty("Authorization", "Basic " + authStr);
      return conn;
    } catch (IOException ex) {
      throw new ElectrumRPCException("Fail to set authenticated connection with server");
    }
  }

  public Object query(String method, Object... o) throws lex.electrumrpc.client.GenericRpcException {
    try {
      return loadResponse(queryForStream(method, o), "1", true);
    } catch (IOException ex) {
      throw new ElectrumRPCException(method, Arrays.deepToString(o), ex);
    }
  }

  public InputStream queryForStream(String method, Object... o) throws lex.electrumrpc.client.GenericRpcException {
    HttpURLConnection conn = setConnection();
    try {
      byte[] r = prepareRequest(method, o);
      logger.log(Level.FINE, "Bitcoin JSON-RPC request:\n{0}", new String(r, QUERY_CHARSET));
      conn.getOutputStream().write(r);
      conn.getOutputStream().close();
      int responseCode = conn.getResponseCode();
      if (responseCode != 200) {
        InputStream errorStream = conn.getErrorStream();
        throw new ElectrumRPCException(method,
                                      Arrays.deepToString(o),
                                      responseCode,
                                      conn.getResponseMessage(),
                                      errorStream == null ? null : new String(loadStream(errorStream, true)));
      }
      return conn.getInputStream();
    } catch (IOException ex) {
      throw new ElectrumRPCException(method, Arrays.deepToString(o), ex);
    }
  }

  public Object batchQuery(String method, List<BatchParam> batchParams) throws lex.electrumrpc.client.GenericRpcException {
    HttpURLConnection conn = setConnection();
    try {
      byte[] r = prepareBatchRequest(method, batchParams);
      logger.log(Level.FINE, "Bitcoin JSON-RPC request:\n{0}", new String(r, QUERY_CHARSET));
      conn.getOutputStream().write(r);
      conn.getOutputStream().close();
      int responseCode = conn.getResponseCode();
      if (responseCode != 200) {
        InputStream errorStream = conn.getErrorStream();
        throw new ElectrumRPCException(method,
                batchParams.stream().map(param->Arrays.deepToString(param.params)).collect(Collectors.joining()),
                responseCode,
                conn.getResponseMessage(),
                errorStream == null ? null : new String(loadStream(errorStream, true)));
      }
      return loadBatchResponse((conn.getInputStream()), batchParams, true);
    } catch (IOException ex) {
      throw new ElectrumRPCException(method, batchParams.stream()
              .map(param->Arrays.deepToString(param.params)).collect(Collectors.joining()), ex);
    }
  }

  @Override
  public String dumpPrivateKey(String address) throws GenericRpcException {
    return (String) query("getprivatekeys", address);
  }

  @Override
  public void importPrivateKey(String privateKey) throws GenericRpcException {
    query("importprivkey", privateKey);
  }

  @Override
  public AddressBalanceInfo getBalanceByAddress(String address) throws GenericRpcException {
    return new AddressBalanceInfoWrapper((Map<String, ?>) query("getaddressbalance", address));
  }

  @Override
  public String getNewAddress() throws GenericRpcException {
    return (String) query("createnewaddress");
  }

  @Override
  public boolean validateAddress(String address) throws GenericRpcException {
    return (boolean) query("validateaddress", address);
  }

  @Override
  public boolean isMine(String address) throws GenericRpcException {
    return (boolean) query("ismine", address);
  }
  @Override
  public PayToInfo payTo(String address, BigDecimal amount, BigDecimal fee, String fromAddress, String remainderAddress)
          throws GenericRpcException {
    return new PayToInfoWrapper((Map<String, ?>) query("payto", address, amount, fee, fromAddress, remainderAddress));
  }

  @Override
  public PayToInfo payTo(String address, BigDecimal amount, BigDecimal fee, String fromAddress) throws GenericRpcException {
    return new PayToInfoWrapper((Map<String, ?>) query("payto", address, amount, fee, fromAddress, fromAddress));
  }

  @Override
  public String payTo(String address, BigDecimal amount) throws GenericRpcException {
    return (String) query("payto", address, amount);
  }

  @Override
  public String addTransaction(String tx) throws GenericRpcException {
    return (String) query("addtransaction", tx);
  }

  @Override
  public String broadcastTransaction(String tx) throws GenericRpcException {
    return (String) query("broadcast", tx);
  }

  @Override
  public TransactionStatus transactionStatus(String txId) throws GenericRpcException {
    return new TransactionStatusWrapper((Map<String, ?>) query("get_tx_status", txId));
  }

  @Override
  public boolean notify(String address, String url) throws GenericRpcException {
    return (boolean) query("notify", address, url);
  }

  @Override
  public BigDecimal getFeeRate() throws GenericRpcException {
    return BigDecimal.valueOf(((Double) query("getfeerate")) * 0.00000001);
  }

  @Override
  public List<AddressUnspent> getAddressUnspent(String address) {
    return new ListMapWrapper<AddressUnspent>((List<Map<String, ?>>) query("getaddressunspent")) {
      protected AddressUnspent wrap(final Map m) {
        return new AddressUnspentWrapper(m);
      }
    };
  }

  @Override
  public List<AddressHistory> getAddressHistory(String address) {
    return new ListMapWrapper<AddressHistory>((List<Map<String, ?>>) query("getaddresshistory")) {
      protected AddressHistory wrap(final Map m) {
        return new AddressHistoryWrapper(m);
      }
    };
  }

  @Override
  public WalletHistory getWalletHistory() throws GenericRpcException {
    return new WalletHistoryWrapper((Map<String, ?>) query("onchain_history"));
  }

  @Override
  public WalletHistory getWalletHistory(Long fromHeight) throws GenericRpcException {
    return new WalletHistoryWrapper((Map<String, ?>) query("onchain_history", fromHeight));
  }

  private class BatchParam {
      public final String id;
      public final Object[] params;

      BatchParam(String id, Object[] params) {
        this.id=id;
        this.params=params;
      }
    }
}

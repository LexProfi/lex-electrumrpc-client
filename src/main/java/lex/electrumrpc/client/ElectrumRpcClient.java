package lex.electrumrpc.client;

import lex.electrumrpc.client.wrapers.AddressBalanceInfo;
import lex.electrumrpc.client.wrapers.TransactionStatus;

import java.math.BigDecimal;


/**
 * @author Lex itconsult70@gmail.com
 */
public interface ElectrumRpcClient {
    public String dumpPrivateKey(String address) throws GenericRpcException;

    public void importPrivateKey(String privateKey) throws GenericRpcException;

    public AddressBalanceInfo getBalanceByAddress(String address) throws GenericRpcException;

    String getNewAddress() throws GenericRpcException;

    boolean validateAddress(String address) throws GenericRpcException;

    boolean isMine(String address) throws GenericRpcException;

    String payTo(String address, BigDecimal amount, BigDecimal fee, String fromAddress) throws GenericRpcException;
    String payTo(String toAddress, BigDecimal amount) throws GenericRpcException;

    String signTransaction(String tx) throws GenericRpcException;

    String addTransaction(String tx) throws GenericRpcException;

    String broadcastTransaction(String tx) throws GenericRpcException;

    public TransactionStatus transactionStatus(String txId) throws GenericRpcException;

    public boolean notify(String address, String url) throws GenericRpcException;

    public BigDecimal getFeeRate() throws GenericRpcException;
}

package lex.electrumrpc.client;

import lex.electrumrpc.client.wrapers.AddressBalanceInfo;
import lex.electrumrpc.client.wrapers.PayToInfo;
import lex.electrumrpc.client.wrapers.TransactionStatus;

import java.math.BigDecimal;

/**
 * @author Lex itconsult70@gmail.com
 */
public interface ElectrumRpcClient {

    /**
     * Returns a dump of the private key for the given address.
     * @param address
     * @return privateKey
     * @throws GenericRpcException
     */
    public String dumpPrivateKey(String address) throws GenericRpcException;

    /**
     * Imports the private key into the wallet.
     * @param privateKey
     * @throws GenericRpcException
     */
    public void importPrivateKey(String privateKey) throws GenericRpcException;

    /**
     * Returns the balance of the address in the wallet.
     * @param address
     * @return AddressBalanceInfo
     * @throws GenericRpcException
     */
    public AddressBalanceInfo getBalanceByAddress(String address) throws GenericRpcException;

    /**
     * Generates a new wallet address.
     * @return address
     * @throws GenericRpcException
     */
    String getNewAddress() throws GenericRpcException;

    /**
     * Checks if an address is a valid bitcoin address.
     * @param address
     * @return boolean
     * @throws GenericRpcException
     */
    boolean validateAddress(String address) throws GenericRpcException;

    /**
     * Checks if the specified address belongs to a wallet
     * @param address
     * @return boolean
     * @throws GenericRpcException
     */
    boolean isMine(String address) throws GenericRpcException;

    /**
     * Creates and signs a new transaction.
     * @param address
     * @param amount
     * @param fee
     * @param fromAddress
     * @param remainderAddress
     * @return PayToInfo
     * @throws GenericRpcException
     */
    PayToInfo payTo(String address, BigDecimal amount, BigDecimal fee, String fromAddress, String remainderAddress)
            throws GenericRpcException;

    /**
     * Creates and signs a new transaction.
     * @param address
     * @param amount
     * @param fee
     * @param fromAddress
     * @return
     * @throws GenericRpcException
     */
    PayToInfo payTo(String address, BigDecimal amount, BigDecimal fee, String fromAddress) throws GenericRpcException;

    /**
     * Creates and signs a new transaction.
     * @param toAddress
     * @param amount
     * @return
     * @throws GenericRpcException
     */
    String payTo(String toAddress, BigDecimal amount) throws GenericRpcException;

    /**
     * Adds a transaction to the wallet.
     * @param tx
     * @return
     * @throws GenericRpcException
     */
    String addTransaction(String tx) throws GenericRpcException;

    /**
     * Sends a transaction to the network.
     * @param tx
     * @return
     * @throws GenericRpcException
     */
    String broadcastTransaction(String tx) throws GenericRpcException;

    /**
     * Returns information about a transaction.
     * @param txId
     * @return
     * @throws GenericRpcException
     */
    public TransactionStatus transactionStatus(String txId) throws GenericRpcException;

    /**
     * Creates a subscription to notifications about balance changes in an addressю
     * @param address
     * @param url
     * @return
     * @throws GenericRpcException
     */
    public boolean notify(String address, String url) throws GenericRpcException;

    /**
     * Returns information about the current commission.
     * @return
     * @throws GenericRpcException
     */
    public BigDecimal getFeeRate() throws GenericRpcException;
}

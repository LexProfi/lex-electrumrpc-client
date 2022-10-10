package lex.electrumrpc.client.wrapers;

import lex.electrumrpc.client.MapWrapperType;

import java.io.Serializable;

public interface TransactionStatus extends MapWrapperType, Serializable {
    int confirmations();
}
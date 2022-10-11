package lex.electrumrpc.client.wrapers;

import lex.electrumrpc.client.MapWrapperType;

import java.io.Serializable;

public interface AddressHistory extends MapWrapperType, Serializable {

    long fee();

    long height();

    String txHash();
}

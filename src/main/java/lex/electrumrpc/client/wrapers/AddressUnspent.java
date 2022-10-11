package lex.electrumrpc.client.wrapers;

import lex.electrumrpc.client.MapWrapperType;

import java.io.Serializable;

public interface AddressUnspent extends MapWrapperType, Serializable {

    long height();

    String hash();

    long pos();

    long txValue();

}

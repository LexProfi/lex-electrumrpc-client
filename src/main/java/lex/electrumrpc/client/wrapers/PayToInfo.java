package lex.electrumrpc.client.wrapers;

import lex.electrumrpc.client.MapWrapperType;
import java.io.Serializable;

public interface PayToInfo extends MapWrapperType, Serializable {

    String hex();

    boolean complete();

    boolean finality();

}
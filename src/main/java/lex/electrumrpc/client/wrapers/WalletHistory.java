package lex.electrumrpc.client.wrapers;

import lex.electrumrpc.client.MapWrapperType;

import java.io.Serializable;
import java.util.List;

public interface WalletHistory extends MapWrapperType, Serializable {

    List<WalletHistoryTransaction> transactions();

}

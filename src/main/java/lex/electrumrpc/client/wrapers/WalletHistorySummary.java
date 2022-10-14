package lex.electrumrpc.client.wrapers;

import lex.electrumrpc.client.MapWrapperType;

import java.io.Serializable;

public interface WalletHistorySummary  extends MapWrapperType, Serializable {

    WalletHistoryBase begin();

    WalletHistoryBase end();

    WalletHistoryFlow flow();

}

package lex.electrumrpc.client.wrapers;

import lex.electrumrpc.client.MapWrapperType;

import java.io.Serializable;
import java.math.BigDecimal;

public interface WalletHistoryBase extends MapWrapperType, Serializable {

    BigDecimal balance();

    Long height();

    String date();

}

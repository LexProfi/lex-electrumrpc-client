package lex.electrumrpc.client.wrapers;

import lex.electrumrpc.client.MapWrapperType;

import java.io.Serializable;
import java.math.BigDecimal;

public interface WalletHistoryFlow extends MapWrapperType, Serializable {

    BigDecimal incoming();

    BigDecimal outgoing();

}

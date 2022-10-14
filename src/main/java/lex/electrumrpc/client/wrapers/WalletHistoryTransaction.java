package lex.electrumrpc.client.wrapers;

import lex.electrumrpc.client.MapWrapperType;

import java.io.Serializable;
import java.math.BigDecimal;

public interface WalletHistoryTransaction extends MapWrapperType, Serializable {

    BigDecimal bcBalance();

    BigDecimal bcValue();

    Long confirmations();

    String date();

    BigDecimal fee();

    Long feeSat();

    Long height();

    boolean incoming();

    String label();

    Long monotonicTimestamp();

    Long timestamp();

    String txId();

    Long txPosInBlock();

}

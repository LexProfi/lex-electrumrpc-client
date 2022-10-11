package lex.electrumrpc.client.wrapers;

import lex.electrumrpc.client.MapWrapperType;

import java.io.Serializable;
import java.math.BigDecimal;

public interface BalanceInfo extends MapWrapperType, Serializable {

    BigDecimal confirmed();

}
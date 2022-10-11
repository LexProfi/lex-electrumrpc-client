package lex.electrumrpc.client.wrapers;

import lex.electrumrpc.client.MapWrapperType;

import java.io.Serializable;
import java.math.BigDecimal;

public interface AddressBalanceInfo extends MapWrapperType, Serializable {

    BigDecimal confirmed();

    BigDecimal unconfirmed();

}
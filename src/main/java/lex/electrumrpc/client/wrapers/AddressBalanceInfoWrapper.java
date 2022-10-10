package lex.electrumrpc.client.wrapers;

import lex.electrumrpc.client.MapWrapper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

@SuppressWarnings("serial")
public class AddressBalanceInfoWrapper extends MapWrapper implements AddressBalanceInfo, Serializable {
    public AddressBalanceInfoWrapper(Map<String, ?> m) {
        super(m);
    }

    @Override
    public BigDecimal confirmed(){ return mapBigDecimal("confirmed");}

    @Override
    public BigDecimal unconfirmed(){ return mapBigDecimal("unconfirmed");}
}
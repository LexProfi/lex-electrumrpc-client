package lex.electrumrpc.client.wrapers;

import lex.electrumrpc.client.MapWrapper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

@SuppressWarnings("serial")
public class BalanceInfoWrapper extends MapWrapper implements BalanceInfo, Serializable {

    public BalanceInfoWrapper(Map<String, ?> m) {
        super(m);
    }

    @Override
    public BigDecimal confirmed(){
        return mapBigDecimal("confirmed");
    }

}
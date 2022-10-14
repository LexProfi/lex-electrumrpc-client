package lex.electrumrpc.client.wrapers;

import lex.electrumrpc.client.MapWrapper;

import java.math.BigDecimal;
import java.util.Map;

@SuppressWarnings("serial")
public class WalletHistoryFlowWrapper extends MapWrapper implements WalletHistoryFlow {

    public WalletHistoryFlowWrapper(Map<String, ?> m) {
        super(m);
    }

    @Override
    public BigDecimal incoming() {
        return mapBigDecimal("BTC_incoming");
    }

    @Override
    public BigDecimal outgoing() {
        return mapBigDecimal("BTC_outgoing");
    }
}

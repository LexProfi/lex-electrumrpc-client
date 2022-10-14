package lex.electrumrpc.client.wrapers;

import lex.electrumrpc.client.MapWrapper;

import java.math.BigDecimal;
import java.util.Map;

@SuppressWarnings("serial")
public class WalletHistoryBaseWrapper extends MapWrapper implements WalletHistoryBase {

    public WalletHistoryBaseWrapper(Map<String, ?> m) {
        super(m);
    }

    @Override
    public BigDecimal balance() {
        return mapBigDecimal("BTC_balance");
    }

    @Override
    public Long height() {
        return mapLong("block_height");
    }

    @Override
    public String date() {
        return mapStr("date");
    }
}

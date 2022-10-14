package lex.electrumrpc.client.wrapers;

import lex.electrumrpc.client.MapWrapper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

@SuppressWarnings("serial")
public class WalletHistoryTransactionWrapper extends MapWrapper implements WalletHistoryTransaction, Serializable {

    public WalletHistoryTransactionWrapper(Map<String, ?> m)
    {
        super(m);
    }

    @Override
    public BigDecimal bcBalance() {
        return mapBigDecimal("bc_balance");
    }

    @Override
    public BigDecimal bcValue() {
        return mapBigDecimal("bc_value");
    }

    @Override
    public Long confirmations() {
        return mapLong("confirmations");
    }

    @Override
    public String date() {
        return mapStr("date");
    }

    @Override
    public BigDecimal fee() {
        return mapBigDecimal("fee");
    }

    @Override
    public Long feeSat() {
        return mapLong("fee_sat");
    }

    @Override
    public Long height() {
        return mapLong("height");
    }

    @Override
    public boolean incoming() {
        return mapBool("incoming");
    }

    @Override
    public String label() {
        return mapStr("label");
    }

    @Override
    public Long monotonicTimestamp() {
        return mapLong("monotonic_timestamp");
    }

    @Override
    public Long timestamp() {
        return mapLong("timestamp");
    }

    @Override
    public String txId() {
        return mapStr("txid");
    }

    @Override
    public Long txPosInBlock() {
        return mapLong("txpos_in_block");
    }
}

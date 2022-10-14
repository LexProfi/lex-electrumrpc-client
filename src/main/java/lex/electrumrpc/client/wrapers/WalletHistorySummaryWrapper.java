package lex.electrumrpc.client.wrapers;

import lex.electrumrpc.client.MapWrapper;

import java.io.Serializable;
import java.util.Map;

@SuppressWarnings("serial")
public class WalletHistorySummaryWrapper extends MapWrapper implements WalletHistorySummary, Serializable {

    public WalletHistorySummaryWrapper(Map<String, ?> m)
    {
        super(m);
    }

    @Override
    public WalletHistoryBase begin() {
        return new WalletHistoryBaseWrapper((Map<String, ?>) m.get("begin"));
    }

    @Override
    public WalletHistoryBase end() {
        return new WalletHistoryBaseWrapper((Map<String, ?>) m.get("end"));
    }

    @Override
    public WalletHistoryFlow flow() {
        return new WalletHistoryFlowWrapper((Map<String, ?>) m.get("flow"));
    }
}

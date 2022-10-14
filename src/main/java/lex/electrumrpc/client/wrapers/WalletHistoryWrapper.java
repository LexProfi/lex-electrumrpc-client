package lex.electrumrpc.client.wrapers;

import lex.electrumrpc.client.MapWrapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class WalletHistoryWrapper extends MapWrapper implements WalletHistory {

    public WalletHistoryWrapper(Map<String, ?> m) {
        super(m);
    }

    public WalletHistorySummary summary(){
        return new WalletHistorySummaryWrapper((Map<String, ?>) m.get("summary"));
    }

    @Override
    public List<WalletHistoryTransaction> transactions() {
        List<WalletHistoryTransaction> transactions = new LinkedList<>();
        List<Map<String, ?>> maps = (List<Map<String, ?>>) m.get("transactions");
        for (Map<String, ?> m : maps) {
            WalletHistoryTransaction transaction = new WalletHistoryTransactionWrapper(m);
            transactions.add(transaction);
        }
        return transactions;
    }
}

package lex.electrumrpc.client.wrapers;

import lex.electrumrpc.client.MapWrapper;

import java.io.Serializable;
import java.util.Map;

@SuppressWarnings("serial")
public class AddressHistoryWrapper extends MapWrapper implements AddressHistory, Serializable {

    public AddressHistoryWrapper(Map<String, ?> m) {
        super(m);
    }

    @Override
    public long fee(){
        return mapLong("fee");
    }

    @Override
    public long height(){
        return mapLong("height");
    }

    @Override
    public String txHash(){
        return mapStr("tx_hash");
    }

}

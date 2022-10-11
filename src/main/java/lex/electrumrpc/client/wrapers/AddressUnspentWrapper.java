package lex.electrumrpc.client.wrapers;

import lex.electrumrpc.client.MapWrapper;

import java.io.Serializable;
import java.util.Map;

@SuppressWarnings("serial")
public class AddressUnspentWrapper extends MapWrapper implements AddressUnspent, Serializable {

    public AddressUnspentWrapper(Map<String, ?> m) {
        super(m);
    }

    @Override
    public long height() {
        return mapLong("height");
    }

    @Override
    public String hash() {

        return mapStr("tx_hash");
    }

    @Override
    public long pos() {

        return mapLong("tx_pos");
    }

    @Override
    public long txValue() {

        return mapLong("value");
    }

}
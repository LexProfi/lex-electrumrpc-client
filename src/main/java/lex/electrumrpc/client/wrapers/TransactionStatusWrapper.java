package lex.electrumrpc.client.wrapers;

import lex.electrumrpc.client.MapWrapper;

import java.io.Serializable;
import java.util.Map;

@SuppressWarnings("serial")
public class TransactionStatusWrapper extends MapWrapper implements TransactionStatus, Serializable {

    public TransactionStatusWrapper(Map<String, ?> m) {
        super(m);
    }

    @Override
    public int confirmations(){
        return mapInt("confirmations");
    }

}
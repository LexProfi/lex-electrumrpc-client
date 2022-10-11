package lex.electrumrpc.client.wrapers;

import lex.electrumrpc.client.MapWrapper;

import java.io.Serializable;
import java.util.Map;

@SuppressWarnings("serial")
public class PayToInfoWrapper extends MapWrapper implements PayToInfo, Serializable {

    public PayToInfoWrapper(Map<String, ?> m) {
        super(m);
    }

    @Override
    public String hex(){
        return mapStr("hex");
    }

    @Override
    public boolean complete(){
        return mapBool("complete");
    }

    @Override
    public boolean finality(){
        return mapBool("final");
    }

}
package com.icix.code.challenge;

/**
 * Created by preeti on 4/8/14.
 */
public class ListenerTestImpl implements Listener {

    private String lastMsg = null;

    @Override
    public void callback(String msg) {
        lastMsg = msg;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }
}

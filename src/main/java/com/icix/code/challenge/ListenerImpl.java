package com.icix.code.challenge;

/**
 * Created by preeti on 4/8/14.
 */
public class ListenerImpl implements Listener {

    private String name;

    public ListenerImpl(String name) {
        this.name = name;
    }

    @Override
    public void callback(String msg) {
        System.out.println(name + " -> " + msg);
    }
}

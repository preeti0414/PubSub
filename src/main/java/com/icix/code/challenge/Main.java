package com.icix.code.challenge;

/**
 * Hello world!
 */
public class Main {
    public static void main(String[] args) {

        Listener listener1 = new ListenerImpl("listener1");
        Listener listener2 = new ListenerImpl("listener2");

        PubSub pubsub = PubSub.getInstance();

        pubsub.publish("foo", "This is foo before subscribe");
        pubsub.publish("far", "This is bar before subscribe");

        pubsub.subscribe("foo", listener1, 1);
        pubsub.subscribe("foo", listener1); // duplicate listner

        pubsub.subscribe("foo", listener2, 1);
        pubsub.subscribe("far", listener2, 2);

        pubsub.publish("foo", "This is foo");
        pubsub.publish("far", "This is bar");
    }
}

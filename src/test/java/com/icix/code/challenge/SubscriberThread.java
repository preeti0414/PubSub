package com.icix.code.challenge;

/**
 * Created by preeti on 4/8/14.
 */
public class SubscriberThread extends Thread {
    private String topic;

    public SubscriberThread(String topic) {
        this.topic = topic;
    }

    @Override
    public void run() {
        PubSub pubsub = PubSub.getInstance();

        for (int i = 0; i < 1000; i++) {
            ListenerTestImpl listener1 = new ListenerTestImpl();
            pubsub.subscribe(topic, listener1);
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("subscribe " + topic + " thread complete");
    }
}

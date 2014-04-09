package com.icix.code.challenge;

/**
 * Created by preeti on 4/8/14.
 */
public class PublisherThread extends Thread {
    private String topic;

    public PublisherThread(String topic) {
        this.topic = topic;
    }

    @Override
    public void run() {
        PubSub pubsub = PubSub.getInstance();
        String msg = "this msg is for " + topic;

        for (int i = 0; i < 10; i++) {
            pubsub.publish(topic, msg);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("publisher " + topic + " thread complete");

    }
}

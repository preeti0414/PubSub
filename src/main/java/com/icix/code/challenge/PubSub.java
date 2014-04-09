package com.icix.code.challenge;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * This is a singleton class used by interested parties to
 * subscribe on topics. Publishers will publish messages
 * in this class and it will callback interested listeners.
 *
 * TODO:
 * 1) Need to work on purging history when certain limit is reached.
 * 2) Wildcard handling.
 *
 * Created by preeti on 4/8/14.
 */
public class PubSub {

    private static PubSub instance = null;

    /** Following map keeps track of lisnteners subscribed to topics **/
    private Map<String, Set<Listener>> listnerMap = new ConcurrentHashMap<String, Set<Listener>>();

    /** Following map maintains the history of published messages **/
    private Map<String, List<String>> historyMap = new ConcurrentHashMap<String, List<String>>();

    private PubSub() {
        // private constructor for Singleton
    }

    /**
     * Provides singleton instance of PubSub.
     * This method is threadsafe.
     *
     * @return
     */
    public static synchronized PubSub getInstance() {
        if (instance == null) {
            instance = new PubSub();
        }
        return instance;
    }

    /**
     * Subscribe to a topic with listener object. Listeners must
     * implement callback method which will be called when topics
     * are published. This method is threadsafe.
     *
     * @param topic    Topic interested to subscribe
     * @param listener Listener object
     */
    public synchronized void subscribe(String topic, Listener listener) {

        if (!listnerMap.containsKey(topic)) {
            Map<Listener, Boolean> map = new ConcurrentHashMap<Listener, Boolean>();
            Set<Listener> set = Collections.newSetFromMap(map);
            listnerMap.put(topic, set);
        }
        Set<Listener> listeners = listnerMap.get(topic);
        listeners.add(listener);
    }

    /**
     * Subscribe to a topic with listener object. Additionally this method
     * checks for historical messages published earlier on given topic.
     * If historical messages are found, it will call callback method
     * on listener for those messages.
     *
     * @param topic                  Topic interested to subscribe
     * @param listener               Listener object
     * @param historicalMessageCount Number of historical messages needed.
     */
    public void subscribe(String topic, Listener listener, int historicalMessageCount) {
        this.subscribe(topic, listener);

        // callback with historical messages
        if (historyMap.containsKey(topic)) {
            List<String> messages = historyMap.get(topic);

            if (messages.size() < historicalMessageCount) {
                historicalMessageCount = messages.size();
            }

            int startIndex = messages.size() - historicalMessageCount;
            int endIndex = messages.size();
            for (int i = startIndex; i < endIndex; i++) {
                listener.callback(messages.get(i));
            }
        }
    }

    /**
     * Publish messages on given topics. If Listners are subscribed for
     * this topic then it calls callback method on each listner. It also
     * saves history to messages on given topic.
     *
     * @param topic
     * @param message
     */
    public void publish(String topic, String message) {

        // Add message to history
        if (!historyMap.containsKey(topic)) {
            historyMap.put(topic, Collections.synchronizedList(new ArrayList<String>()));
        }
        historyMap.get(topic).add(message);

        // publish message
        if (!listnerMap.containsKey(topic)) {
            // no listeners subscribed for this topic
            return;
        }

        Set<Listener> listeners = listnerMap.get(topic);
        for (Listener listener : listeners) {
            listener.callback(message);
        }
    }
}

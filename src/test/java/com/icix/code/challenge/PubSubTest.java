package com.icix.code.challenge;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for PubSub.
 */
public class PubSubTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public PubSubTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(PubSubTest.class);
    }

    PubSub pubsub = null;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        pubsub = PubSub.getInstance();
    }

    public void test1() {
        ListenerTestImpl listener1 = new ListenerTestImpl();
        ListenerTestImpl listener2 = new ListenerTestImpl();

        pubsub.subscribe("topic1", listener1);
        pubsub.subscribe("topic1", listener2);

        String msg = "this msg is for topic1";
        pubsub.publish("topic1", msg);

        // last message in test listeners should be same
        assertEquals(listener1.getLastMsg(), msg);
        assertEquals(listener2.getLastMsg(), msg);
    }

    public void test2() {
        ListenerTestImpl listener1 = new ListenerTestImpl();
        ListenerTestImpl listener2 = new ListenerTestImpl();

        pubsub.subscribe("topic1", listener1);
        pubsub.subscribe("topic2", listener2);

        String msg = "this msg is for topic1";
        pubsub.publish("topic1", msg);

        // last message in test listeners should be same
        assertEquals(listener1.getLastMsg(), msg);
        assertEquals(listener2.getLastMsg(), null);
    }

    public void test3() {
        ListenerTestImpl listener1 = new ListenerTestImpl();
        ListenerTestImpl listener2 = new ListenerTestImpl();

        pubsub.subscribe("topic1", listener1);
        pubsub.subscribe("topic2", listener2);

        String msg = "this msg is for topic1";
        String msg2 = "this msg is for topic2";
        pubsub.publish("topic1", msg);
        pubsub.publish("topic2", msg2);

        // last message in test listeners should be same
        assertEquals(listener1.getLastMsg(), msg);
        assertEquals(listener2.getLastMsg(), msg2);
    }

    public void test4() {
        // test history

        String msg = "this msg is for topic1";
        pubsub.publish("topic1", msg);

        ListenerTestImpl listener1 = new ListenerTestImpl();
        assertEquals(listener1.getLastMsg(), null);

        pubsub.subscribe("topic1", listener1, 1);

        // historical message should have been called on listener
        assertEquals(listener1.getLastMsg(), msg);
    }
}

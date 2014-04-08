PubSub
======

Publisher Subscriber Code

This is an implementation of a simple in-memory publish/subscribe bus interface.  

Interested parties may “subscribe” to “topics”.  Publishers may “publish” a message to topics.  

Each listener will be called back with the message if a message is published to a topic for which it has previously subscribed.

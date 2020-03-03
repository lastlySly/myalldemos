package com.lastlysly.javacustomlistener.listener;

import com.lastlysly.javacustomlistener.event.Event;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2020-03-02 15:10
 **/
public interface EventListener {
    String getEventListenerName();
    void onEvent(Event event);
    void doEvent(Event event);
}

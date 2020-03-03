package com.lastlysly.javacustomlistener.listener;

import com.lastlysly.javacustomlistener.event.Event;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2020-03-02 15:18
 **/
public abstract class RobotEventListener implements EventListener {
    @Override
    public String getEventListenerName() {
        return "机器人事件监听器";
    }
    @Override
    public void doEvent(Event event) {
        System.out.println("==============================这里是doEvent接口实现，现在执行接口onEvent方法start======================");
        onEvent(event);
        System.out.println("==============================这里是doEvent接口实现，现在执行接口onEvent方法end====================");
    }
}

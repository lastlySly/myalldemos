package com.lastlysly.javacustomlistener.listener;

import com.lastlysly.javacustomlistener.event.Event;
import com.lastlysly.javacustomlistener.event.RobotDancingEvent;
import com.lastlysly.javacustomlistener.event.RobotWorkingEvent;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2020-03-02 15:20
 **/
public class MyNewRobotEventListener extends RobotEventListener {
    @Override
    public void onEvent(Event event) {
        if (event instanceof RobotDancingEvent) {
            System.out.println("现在在执行onEvent接口的实现，在抽象类RobotEventListener中的doEvent接口实现中调用了这个接口");
            System.out.println("现在尝试调用Event中的接口getEventName，如果成功，则会返回事件名start：");
            String eventName = event.getEventName();
            System.out.println("事件名：" + eventName);
            System.out.println(getEventListenerName() +"：我的机器人会跳舞,执行事件");
        } else if (event instanceof RobotWorkingEvent) {
            System.out.println(getEventListenerName() +"：我的机器人会工作");
        }
    }
}

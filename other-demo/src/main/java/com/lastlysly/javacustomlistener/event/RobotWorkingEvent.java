package com.lastlysly.javacustomlistener.event;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2020-02-12 10:58
 **/
public class RobotWorkingEvent extends RobotEvent{
    /**
     * 事件名
     *
     * @return
     */
    @Override
    public String getEventName() {
        return "机器人工作事件";
    }
}

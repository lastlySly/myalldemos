package com.lastlysly.javacustomlistener.runner;

import com.lastlysly.javacustomlistener.event.Event;
import com.lastlysly.javacustomlistener.listener.EventListener;

import java.time.LocalDateTime;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2020-03-02 15:23
 **/
public class RobotExecutor implements EventExecutor {
    private String executorName;
    private LocalDateTime executorTime;

    private EventListener eventListener;

    public RobotExecutor(String executorName, LocalDateTime executorTime) {
        this.executorName = executorName;
        this.executorTime = executorTime;
    }

    @Override
    public String getExecutorName() {
        return executorName;
    }

    @Override
    public LocalDateTime getExecutorTime() {
        return executorTime;
    }

    /**
     * 注册监听
     * @param eventListener
     */
    public void registerListener(EventListener eventListener) {
        this.eventListener = eventListener;
    }

    /**
     * 执行监听器
     * @param event
     */
    public void runListener(Event event){
        eventListener.doEvent(event);
        System.out.println("现在是：" + executorTime + "，当前" + executorName + "在运行");
    }
}

package com.lastlysly.javacustomlistener;

import com.google.common.collect.Maps;
import com.lastlysly.javacustomlistener.event.RobotDancingEvent;
import com.lastlysly.javacustomlistener.event.RobotWorkingEvent;
import com.lastlysly.javacustomlistener.listener.MyNewRobotEventListener;
import com.lastlysly.javacustomlistener.runner.RobotExecutor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2020-02-12 10:51
 **/
public class MainDemo {
    public static void main(String[] args) {
        RobotExecutor robotExecutor = new RobotExecutor("机器人执行器", LocalDateTime.now());
        robotExecutor.registerListener(new MyNewRobotEventListener());
        robotExecutor.runListener(new RobotDancingEvent());
//        robotExecutor.runListener(new RobotWorkingEvent());
    }
}

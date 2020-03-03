package com.lastlysly.javacustomlistener.event;

import java.time.LocalDateTime;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2020-02-12 10:54
 *
 **/
public abstract class RobotEvent implements Event {

    private LocalDateTime happenTime = LocalDateTime.now();
    /**
     * 事件生成时间
     *
     * @return
     */
    @Override
    public LocalDateTime getHappenTime() {
        return happenTime;
    }
}

package com.lastlysly.javacustomlistener.event;

import java.time.LocalDateTime;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2020-02-12 10:52
 **/
public interface Event {
    /**
     * 事件生成时间
     * @return
     */
    LocalDateTime getHappenTime();

    /**
     * 事件名
     * @return
     */
    String getEventName();
}

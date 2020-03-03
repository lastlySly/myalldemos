package com.lastlysly.javacustomlistener.runner;

import java.time.LocalDateTime;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2020-03-02 15:26
 * 事件源
 **/
public interface EventExecutor {
    String getExecutorName();
    LocalDateTime getExecutorTime();
}

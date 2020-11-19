package com.lastlysly.cronclient;

import org.quartz.TriggerUtils;
import org.quartz.impl.triggers.CronTriggerImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2020-04-02 10:22
 **/
public class CronClient {

    public static void main(String[] args) {
        String cronExpression = "0 0 0 * * ? ";
        Integer numTimes = 5;
        List<String> dateStrList = getNextExecTime(cronExpression,numTimes);
        for (String s : dateStrList) {
            System.out.println(s);
        }
    }

    public static List<String> getNextExecTime(String cronExpression, Integer numTimes) {
        List<String> list = new ArrayList<>();
        CronTriggerImpl cronTriggerImpl = new CronTriggerImpl();
        try {
            cronTriggerImpl.setCronExpression(cronExpression);
        } catch(ParseException e) {
            e.printStackTrace();
        }
        // 这个是重点，一行代码搞定
        List<Date> dates = TriggerUtils.computeFireTimes(cronTriggerImpl, null, numTimes);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Date date : dates) {
            list.add(dateFormat.format(date));
        }
        return list;
    }
}

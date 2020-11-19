package com.lastlysly.utils;

import java.util.List;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2020-04-27 09:57
 **/
public class CustomUtils {
    /**
     * 对list分页
     * @param list
     * @param pageNum
     * @param pageSize
     * @return
     */
    public static List listStartPage(List list,Integer pageNum,Integer pageSize) {
        if (list == null) {
            return null;
        }
        if (list.size() == 0) {
            return null;
        }
        /**
         * 记录总数
         */
        Integer count = list.size();
        /**
         * 页数
         */
        Integer pageCount = 0;

        if (count % pageSize == 0) {
            pageCount = count / pageSize;
        } else {
            pageCount = count / pageSize + 1;
        }
        /**
         * 开始索引
         */
        int fromIndex = 0;
        /**
         * 结束索引
         */
        int toIndex = 0;

        if (pageNum > pageCount) {
            /**
             * 页码大于总页数，显示最后一页
             */
            pageNum = pageCount;
        }
        if (!pageCount.equals(pageNum)) {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = fromIndex + pageSize;
        } else {
            fromIndex = (pageNum - 1) * pageSize;
            toIndex = count;
        }
        List pageList = list.subList(fromIndex, toIndex);
        return pageList;
    }
}

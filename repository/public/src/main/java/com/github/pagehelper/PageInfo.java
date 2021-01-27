
package com.github.pagehelper;

import java.util.List;

/**
 * 分页信息
 */
public class PageInfo<T> {
    /**
     * 当前页
     */
    private int pageNum;
    /**
     * 每页的数量
     */

    private int pageSize;
    /**
     * 当前页的数量
     */
    private int size;
    /**
     * 总页数
     */
    private int pages;
    /**
     * 总行数
     */
    protected long total;
    /**
     * 数据
     */
    protected List<T> list;
}

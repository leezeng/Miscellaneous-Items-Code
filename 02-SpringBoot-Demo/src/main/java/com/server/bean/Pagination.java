package com.server.bean;

/**
 * 分页参数对象
 *
 * @author CYX
 * @create 2018-07-14-19:36
 */
public class Pagination {

    /**
     * 分页页码,默认1
     */
    private int page = 1;

    /**
     * 分页每页数量,默认20
     */
    private int pageSize = 20;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "Page{" +
                "page=" + page +
                ", pageSize=" + pageSize +
                '}';
    }
}

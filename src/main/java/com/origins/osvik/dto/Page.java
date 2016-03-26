package com.origins.osvik.dto;

/**
 * Created by Amila-Kumara on 3/19/2016.
 */
public class Page {
    private Long count;
    private Long pageSize;

    public Page(Long count, Long pageSize) {
        this.count = count;
        this.pageSize = pageSize;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return String.format("Page{count=%d, pageSize=%d}", count, pageSize);
    }
}

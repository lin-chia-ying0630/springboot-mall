package com.kujudy.springbootmall.unit;

import lombok.Data;

import java.util.List;

public class Page<T> {
    private Integer limit;
    private Integer offset;
    private Integer total;
    private List<T> results;

    public Page() {
    }

    public Page(Integer limit, Integer offset, Integer total, List<T> results) {
        this.limit = limit;
        this.offset = offset;
        this.total = total;
        this.results = results;
    }

    /**
     * 获取
     * @return limit
     */
    public Integer getLimit() {
        return limit;
    }

    /**
     * 设置
     * @param limit
     */
    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    /**
     * 获取
     * @return offset
     */
    public Integer getOffset() {
        return offset;
    }

    /**
     * 设置
     * @param offset
     */
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    /**
     * 获取
     * @return total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * 设置
     * @param total
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * 获取
     * @return results
     */
    public List<T> getResults() {
        return results;
    }

    /**
     * 设置
     * @param results
     */
    public void setResults(List<T> results) {
        this.results = results;
    }

    public String toString() {
        return "Page{limit = " + limit + ", offset = " + offset + ", total = " + total + ", results = " + results + "}";
    }
}


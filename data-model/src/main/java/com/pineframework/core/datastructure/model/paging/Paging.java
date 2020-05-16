package com.pineframework.core.datastructure.model.paging;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pineframework.core.datastructure.model.FlatTransient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Paging<I extends Serializable, M extends FlatTransient<I>> {

    private int start = 0;

    private int max = 0;

    private int current = 0;

    private int size = 0;

    private Long count = 0L;

    private List<M> content = new ArrayList<>();

    private Set<Order> orders = new HashSet<>();

    private List<Filter> filters = new ArrayList<>();

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<M> getContent() {
        return content;
    }

    public void setContent(List<M> content) {
        this.content = content;
    }

    public List<Filter> getFilters() {
        return filters;
    }

    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }
}

package com.pineframework.core.datamodel.paging;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.pineframework.core.datamodel.filter.Filter;

import static java.lang.Math.ceil;
import static java.lang.Math.min;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Page implements Pageable {

    @JsonView(PageMetadataView.class)
    private int offset = 0;

    @JsonView(PageMetadataView.class)
    private int limit = 10;

    private int index = 1;

    private int length = 0;

    private Long count = 0L;

    private Object[] content = new Object[0];

    @JsonView(PageMetadataView.class)
    private Filter[] filters = new Filter[0];

    public Page() {
    }

    @JsonIgnore
    public Page(int offset, int limit) {
        this.offset = offset;
        this.limit = limit;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public Object[] getContent() {
        return content;
    }

    public void setContent(Object[] content) {
        this.content = content;
    }

    @Override
    public Filter[] getFilters() {
        return filters;
    }

    public void setFilters(Filter[] filters) {
        this.filters = filters;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @JsonIgnore
    public void next() {
        length = (int) ceil((count.doubleValue() / limit));
        index = min(((offset / limit) + 1), length);
        offset = min((offset + limit), count.intValue());
    }

}

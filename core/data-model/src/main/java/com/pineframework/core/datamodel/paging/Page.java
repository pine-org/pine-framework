package com.pineframework.core.datamodel.paging;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.pineframework.core.datamodel.filter.Filter;

/**
 * @author Saman Alishiri, samanalishiri@gmail.com
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Page implements Pageable {

    @JsonIgnore
    private int offset = 0;

    @JsonView(PageMetadataView.class)
    private int size = 10;

    @JsonView(PageMetadataView.class)
    private int index = 0;

    @JsonView(PageMetadataView.class)
    private int[] indices = new int[0];

    @JsonView(PageMetadataView.class)
    private Filter[] filters = new Filter[0];

    private Object[] content = new Object[0];

    public Page() {
    }

    @JsonIgnore
    public Page(int offset, int size) {
        this.offset = offset;
        this.size = size;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
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

    public int[] getIndices() {
        return indices;
    }

    public void setIndices(int[] indices) {
        this.indices = indices;
    }

    @JsonIgnore
    public Page next() {
        Page page = new Page(-1, size);
        page.setIndex(index + 1);
        page.setFilters(filters);
        return page;
    }

    @JsonIgnore
    public Page previous() {
        Page page = new Page(-1, size);
        page.setIndex(index - 1);
        page.setFilters(filters);
        return page;
    }
}

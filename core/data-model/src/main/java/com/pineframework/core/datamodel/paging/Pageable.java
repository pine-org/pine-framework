package com.pineframework.core.datamodel.paging;

import com.pineframework.core.datamodel.filter.Filter;

public interface Pageable {
    int getOffset();

    int getSize();

    Object[] getContent();

    Filter[] getFilters();

}

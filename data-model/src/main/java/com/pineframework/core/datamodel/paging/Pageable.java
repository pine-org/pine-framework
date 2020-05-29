package com.pineframework.core.datamodel.paging;

import com.pineframework.core.datamodel.filter.Filter;

public interface Pageable {
    int getOffset();

    int getLimit();

    Object[] getContent();

    Filter[] getFilters();

}

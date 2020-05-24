package com.pineframework.core.datamodel.model.paging;

import com.pineframework.core.datamodel.model.filter.Filter;

public interface Pageable {
    int getOffset();

    int getLimit();

    Object[] getContent();

    Filter[] getFilters();

}

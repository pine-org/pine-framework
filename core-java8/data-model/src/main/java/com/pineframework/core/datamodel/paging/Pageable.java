package com.pineframework.core.datamodel.paging;

import com.pineframework.core.datamodel.filter.Filter;
import com.pineframework.core.datamodel.sort.Sort;

public interface Pageable {
    int getOffset();

    int getSize();

    Object[] getContent();

    Filter[] getFilters();

    String[] getProjections();

    Sort[] getSorts();
}

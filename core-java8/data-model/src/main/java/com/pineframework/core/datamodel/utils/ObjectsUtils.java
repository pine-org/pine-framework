package com.pineframework.core.datamodel.utils;

import com.pineframework.core.datamodel.model.Transient;
import com.pineframework.core.datamodel.persistence.Persistable;

import java.util.Objects;

public final class ObjectsUtils {

    private ObjectsUtils() {
    }

    public static boolean areEquivalence(Transient m, Persistable e) {
        return Objects.equals(m.getId(), e.getId());
    }

    public static boolean areEquivalence(Persistable e, Transient m) {
        return Objects.equals(m.getId(), e.getId());
    }
}

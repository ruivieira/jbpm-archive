package org.jbpm.prediction.randomforest;


import org.jbpm.prediction.randomforest.features.Value;

import java.util.Objects;

public class CacheEntry {

    private String attribute;
    private Value value;

    private CacheEntry(String attribute, Value value) {
        this.attribute = attribute;
        this.value = value;
    }

    public static CacheEntry create(String attribute, Value value) {
        return new CacheEntry(attribute, value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attribute, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CacheEntry that = (CacheEntry) o;
        return Objects.equals(attribute, that.attribute) &&
                Objects.equals(value, that.value);
    }
}

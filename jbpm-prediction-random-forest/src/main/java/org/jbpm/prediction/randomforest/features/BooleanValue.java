package org.jbpm.prediction.randomforest.features;

import java.util.Objects;

public class BooleanValue implements Value<Boolean> {

    private final Boolean data;

    public BooleanValue(Boolean data) {
        this.data = data;
    }

    @Override
    public boolean compare(Value other) {
        return this.data.equals(other.getData());
    }

    @Override
    public Value clone() {
        return new BooleanValue(data);
    }

    @Override
    public Boolean getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BooleanValue that = (BooleanValue) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    @Override
    public String toString() {
        return "BooleanValue{" +
                "data=" + data +
                '}';
    }

}

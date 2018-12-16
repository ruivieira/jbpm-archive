package org.jbpm.prediction.randomforest.features;

import java.util.Objects;

public class IntValue implements Value<Integer> {

    private final Integer data;

    public IntValue(int data) {
        this.data = data;
    }

    @Override
    public boolean compare(Value other) {
        if (other instanceof DoubleValue) {
            return this.data >= (Integer) other.getData() ;
        } else {
            return false;
        }
    }

    @Override
    public Value clone() {
        return new IntValue(data);
    }

    @Override
    public Integer getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntValue that = (IntValue) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }

    @Override
    public String toString() {
        return "IntValue{" +
                "data=" + data +
                '}';
    }
}

package org.jbpm.prediction.randomforest.features;

import java.util.Objects;

public class DoubleValue implements Value<Double> {

    private final Double data;

    public DoubleValue(Double data) {
        this.data = data;
    }

    @Override
    public boolean compare(Value other) {
        if (other instanceof DoubleValue) {
            return this.data >= (Double) other.getData() ;
        } else {
            return false;
        }
    }

    @Override
    public Value clone() {
        return new DoubleValue(data);
    }

    @Override
    public Double getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DoubleValue that = (DoubleValue) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }
}

package org.jbpm.prediction.randomforest.features;

import java.util.Objects;

public class StringValue implements Value<String> {

    public String getData() {
        return data;
    }

    private final String data;

    public StringValue(String data) {
        this.data = data;
    }

    public Value clone() {
        return new StringValue(this.data);
    }

    @Override
    public boolean compare(Value other) {

        return data.equals(other.getData());
    }

    @Override
    public String toString() {
        return "StringValue{" +
                "data='" + data + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringValue that = (StringValue) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }
}

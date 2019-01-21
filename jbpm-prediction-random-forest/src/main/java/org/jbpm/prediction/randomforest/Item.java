package org.jbpm.prediction.randomforest;

import org.kie.internal.task.api.prediction.features.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a mapping between attributes and their respective values `(A -> foo, B -> bar, ...)`.
 * An Item is either a datum of a Decision Tree/Random Forest (DT/RF) data set (used for training), or a way to
 * express a question (prediction) to a DT/RF.
 * For instance, "What are the possible values of A, given the Item (D -> baz, E -> foobar, ...)".
 */
public class Item {

    private final Map<String, Value> values = new HashMap<>();

    private Item() {

    }

    public static Item create() {
        return new Item();
    }

    public boolean contains(String attribute) {
        return values.containsKey(attribute);
    }

    public Value get(String attribute) {
        return values.get(attribute);
    }

    public void add(String attribute, Value value) {
        values.put(attribute, value);
    }

    public Set<String> getAttributes() {
        return values.keySet();
    }

    @Override
    public int hashCode() {
        return Objects.hash(values);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(values, item.values);
    }

    @Override
    public Item clone() {
        final Item clone = new Item();
        for (String key : values.keySet()) {
            clone.add(key, values.get(key).clone());
        }
        return clone;
    }

    @Override
    public String toString() {
        return "Item{" +
                "values=" + values +
                '}';
    }
}

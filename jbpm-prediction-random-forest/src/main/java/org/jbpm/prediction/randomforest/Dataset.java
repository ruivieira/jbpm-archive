package org.jbpm.prediction.randomforest;

import java.util.ArrayList;
import java.util.List;

/**
 * In-memory representation of a Decision Tree/Random Forest dataset
 */
public class Dataset {

    private final List<Item> items = new ArrayList<>();

    private Dataset() { }

    public static Dataset create() {
        return new Dataset();
    }

    public List<Item> getItems() {
        return items;
    }

    public void add(Item item) {
        items.add(item);
    }

    public int size() {
        return items.size();
    }

    public Dataset clone() {
        final Dataset clone = new Dataset();
        for (Item entry : items) {
            final Item clonedItem = entry.clone();
            clone.add(clonedItem);
        }
        return clone;
    }

    @Override
    public String toString() {
        return "Dataset{" +
                "items=" + items +
                '}';
    }
}

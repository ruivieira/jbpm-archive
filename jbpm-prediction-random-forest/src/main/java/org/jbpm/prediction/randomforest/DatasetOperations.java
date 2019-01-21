package org.jbpm.prediction.randomforest;

import org.kie.internal.task.api.prediction.features.Value;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatasetOperations {

    private static double logBase(double x, double base) {
        return Math.log(x) / Math.log(base);
    }

    public static Value frequency(Dataset dataset, String attribute) {
        final Map<Value, Integer> counter = unique(dataset, attribute);

        int highest_count = 0;
        Value highest_count_value = null;

        for (Value value : counter.keySet()) {
            if (counter.get(value) > highest_count) {
                highest_count = counter.get(value);
                highest_count_value = value;
            }
        }

        return highest_count_value;
    }

    public static Map<Value, Integer> unique(Dataset dataset, String attribute) {
        final Map<Value, Integer> counter = new HashMap<>();

        for (Item item : dataset.getItems()) {
            if (item.contains(attribute)) {
                final Value value = item.get(attribute);
                counter.put(value, counter.getOrDefault(value, 0) + 1);
            }
        }
        return counter;
    }

    public static double entropy(Dataset dataset, String attribute) {
        final Map<Value, Integer> counter = unique(dataset, attribute);
        final double size = (double) dataset.getItems().size();
        double entropy = 0.0;

        for (Integer i : counter.values()) {
            double p = (double) i / size;
            entropy += -p * logBase(p, 2.0);
        }

        return entropy;
    }

    public static Split calculateSplit(Dataset dataset, String attribute, Value pivot) {

        final Dataset trueBranch = Dataset.create();
        final Dataset falseBranch = Dataset.create();

        for (Item item : dataset.getItems()) {
            final Value value = item.get(attribute);

            if (value.compare(pivot)) {
                trueBranch.add(item);
            } else {
                falseBranch.add(item);
            }
        }
        return Split.create(trueBranch, falseBranch);
    }

    public static synchronized Dataset sample(Dataset dataset, int n) {
        final Dataset clone = dataset.clone();
        final List<Item> items = clone.getItems();
        Collections.shuffle(items);
        final Dataset newDataset = Dataset.create();
        final List<Item> nItems = items.subList(0, n);
        for (Item item : nItems) {
            newDataset.add(item);
        }
        return newDataset;
    }
}

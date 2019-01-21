package org.jbpm.prediction.randomforest;

import org.kie.internal.task.api.prediction.features.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RandomForest {

    private final List<DecisionTree> forest= new ArrayList<>();

    private RandomForest(TreeConfiguration config, Dataset data, int number, int samples) {
        for (int i = 0 ; i < number ; i++) {
            final TreeConfiguration clonedConfig = config.clone();
            final DecisionTree tree = DecisionTree.create(clonedConfig, DatasetOperations.sample(data, samples), 70);
            forest.add(tree);
        }
    }

    public Map<Value, Integer> predict(Item item) {
        Map<Value, Integer> result = new HashMap<>();
        for (DecisionTree tree : forest) {
            Value decision = tree.predict(item);
            if (decision != null) {
                Integer newValue = result.getOrDefault(decision, 0) + 1;
                result.put(decision, newValue);
            }
        }
        return result;
    }

    public static RandomForest create(TreeConfiguration config, Dataset data, int number, int samples) {
        return new RandomForest(config, data, number, samples);
    }
}

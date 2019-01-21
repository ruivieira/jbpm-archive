/*
 * Copyright 2018 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jbpm.prediction.randomforest;

import java.util.HashMap;
import java.util.Map;

import org.kie.api.task.model.Task;
import org.kie.internal.task.api.prediction.PredictionOutcome;
import org.kie.internal.task.api.prediction.PredictionService;
import org.kie.internal.task.api.prediction.features.BooleanValue;
import org.kie.internal.task.api.prediction.features.IntValue;
import org.kie.internal.task.api.prediction.features.StringValue;
import org.kie.internal.task.api.prediction.features.Value;


public class RandomForestPredictionService implements PredictionService {
    
    public static final String IDENTIFIER = "RandomForest";
    
    private int datasetSizeThreshold = 30;
    private Dataset dataset = Dataset.create();
    private RandomForest randomForest;
    private double confidenceLevelThreshold = 90.0;

    public String getIdentifier() {
        return IDENTIFIER;
    }

    public Map<String, Object> getConfidence(String attribute, Map<Value, Integer> prediction) {

        Map<String, Object> result = new HashMap<>();

        Double currentConfidence = 0.0;
        Value currentValue = new BooleanValue(true);
        for (Value value : prediction.keySet()) {
            double currentPrediction = prediction.get(value).doubleValue();
            if (currentPrediction > currentConfidence) {
                currentConfidence = currentPrediction;
                currentValue = value;
            }
        }

        result.put("approved", currentValue.getData());
        result.put("confidence", currentConfidence);

        return result;

    }

    public PredictionOutcome predict(Task task, Map<String, Object> inputData) {

        // create random forest
        final TreeConfiguration config = TreeConfiguration.create();
        config.setDecision("approved");
        System.out.println("Dataset size: " + dataset.size());
        // TODO: Random forest should take care of this verification
        if (dataset.size() > 1) {
            randomForest = RandomForest.create(config, dataset,100, (int) Math.ceil(dataset.size() /2.0));
        } else if (dataset.size() == 1) {
            randomForest = RandomForest.create(config, dataset,100, 1);
        } else {
            return new PredictionOutcome();
        }

        System.out.println("================================================================");
        Item question = Item.create();
        question.add("level", new IntValue((Integer) inputData.get("level")));
        question.add("actor", new StringValue((String) inputData.get("ActorId")));
        Map<Value, Integer> outcomes = randomForest.predict(question);

        System.out.println("The outcome is:");
        System.out.println(outcomes);

        Map<String, Object> data = getConfidence("approved", outcomes);
        System.out.println("Confidence:");
        System.out.println(data);

        return new PredictionOutcome((Double) data.get("confidence"), confidenceLevelThreshold, dataset.size(), datasetSizeThreshold, data);
    }

    public void train(Task task, Map<String, Object> inputData, Map<String, Object> outputData) {

        System.out.println("Training the RF!");
        System.out.println("with:" + outputData.toString());

        final Item item = Item.create();
        item.add("level", new IntValue((Integer) inputData.get("level")));
        item.add("actor", new StringValue((String) inputData.get("ActorId")));
        item.add("approved", new BooleanValue((Boolean) outputData.get("approved")));
        dataset.add(item);

    }

}

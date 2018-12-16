package org.jbpm.prediction.randomforest;

import org.jbpm.prediction.randomforest.features.Value;

import java.util.HashSet;
import java.util.Set;

public class DecisionTree {

    private Value decision;
    private String attribute;
    private Value pivot;
    private DecisionTree trueBranch;
    private DecisionTree falseBranch;

    private DecisionTree() {

    }

    public static DecisionTree create(TreeConfiguration config, Dataset data, int depth) {

        if (depth == 0 || data.size() <= 1) {
            final DecisionTree tree = new DecisionTree();
            tree.decision = DatasetOperations.frequency(data, config.getDecision());
            return tree;
        }

        double entropy = DatasetOperations.entropy(data, config.getDecision());

        if (entropy <= config.getEntropyThreshold()) {
            final DecisionTree tree = new DecisionTree();
            tree.decision = DatasetOperations.frequency(data, config.getDecision());
            return tree;
        }

        final Set<CacheEntry> cache = new HashSet<>();

        Split currentSplit = Split.create();

        for (Item item : data.getItems()) {

            for (String attribute : item.getAttributes()) {
                if (attribute.equals(config.getDecision()) || config.isIgnored(attribute)) {
                    continue;
                }
                final Value pivot = item.get(attribute);
                final CacheEntry cacheEntry = CacheEntry.create(attribute, pivot);
                if (cache.contains(cacheEntry)) {
                    continue;
                }
                cache.add(cacheEntry);
                final Split split = DatasetOperations.calculateSplit(data, attribute, pivot);
                final double trueBranchEntropy = DatasetOperations.entropy(split.getTrueBranchDataset(), config.getDecision());
                final double falseBranchEntropy = DatasetOperations.entropy(split.getFalseBranchDataset(), config.getDecision());

                double newEntropy = (trueBranchEntropy * (double) split.getTrueBranchDataset().size() + falseBranchEntropy * (double) split.getFalseBranchDataset().size()) / (double) data.size();
                double currentGain = entropy - newEntropy;

                if (currentGain > currentSplit.getGain()) {
                    currentSplit = split;

                    currentSplit.setAttribute(attribute);
                    currentSplit.setPivot(pivot);
                    currentSplit.setGain(currentGain);
                }
            }
        }

        if (currentSplit.getGain() > 0.0) {
            final DecisionTree tree = new DecisionTree();
            final TreeConfiguration trueBranchConfig = config.clone();
            tree.trueBranch = create(trueBranchConfig, currentSplit.getTrueBranchDataset(), depth - 1);

            final TreeConfiguration falseBranchConfig = config.clone();
            tree.falseBranch = create(falseBranchConfig, currentSplit.getFalseBranchDataset(), depth - 1);

            tree.attribute = currentSplit.getAttribute();
            tree.pivot = currentSplit.getPivot();

            return tree;

        } else {
            final DecisionTree tree = new DecisionTree();
            tree.decision = DatasetOperations.frequency(data, config.getDecision());
            return tree;
        }
    }

    public Value predict(Item item) {

        DecisionTree tree = this;

        while (true) {
            if (tree != null) {
                if (tree.decision != null) {
                    return tree.decision;
                }

                final String attribute = tree.attribute;

                final Value value = item.get(attribute);

                final Value pivot = tree.pivot;

                if (value != null && pivot != null && value.compare(pivot)) {
                    tree = tree.trueBranch;
                } else {
                    tree = tree.falseBranch;
                }
            } else {
                return null;
            }
        }
    }

    @Override
    public String toString() {
        return "DecisionTree{" +
                "decision=" + decision +
                ", attribute='" + attribute + '\'' +
                ", pivot=" + pivot +
                ", trueBranch=" + trueBranch +
                ", falseBranch=" + falseBranch +
                '}';
    }
}

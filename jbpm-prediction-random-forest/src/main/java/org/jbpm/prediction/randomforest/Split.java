package org.jbpm.prediction.randomforest;


import org.kie.internal.task.api.prediction.features.Value;

class Split {
    private double gain = 0.0;
    private Dataset trueBranchDataset;
    private Dataset falseBranchDataset;
    private String attribute;
    private Value pivot;

    private Split(Dataset trueBranchDataset, Dataset falseBranchDataset) {
        this.trueBranchDataset = trueBranchDataset;
        this.falseBranchDataset = falseBranchDataset;
    }

    private Split() {

    }

    public static Split create(Dataset trueBranch, Dataset falseBranch) {
        return new Split(trueBranch, falseBranch);
    }

    public static Split create() {
        return new Split();
    }

    public double getGain() {
        return gain;
    }

    public void setGain(double gain) {
        this.gain = gain;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public Value getPivot() {
        return pivot;
    }

    public void setPivot(Value pivot) {
        this.pivot = pivot;
    }

    public Dataset getTrueBranchDataset() {
        return trueBranchDataset;
    }

    public Dataset getFalseBranchDataset() {
        return falseBranchDataset;
    }

    @Override
    public String toString() {
        return "Split{" +
                "trueBranchDataset=" + trueBranchDataset +
                ", falseBranchDataset=" + falseBranchDataset +
                ", attribute='" + attribute + '\'' +
                ", pivot=" + pivot +
                ", gain=" + gain +
                '}';
    }
}

package org.jbpm.prediction.randomforest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TreeConfiguration {

    private String decision = "";
    private double entropyThreshold = 1e-10;
    private Set<String> ignore = new HashSet<>();

    private TreeConfiguration() {

    }

    public static TreeConfiguration create() {
        return new TreeConfiguration();
    }

    @Override
    protected TreeConfiguration clone() {
        final TreeConfiguration clone = new TreeConfiguration();
        clone.decision = this.decision;
        clone.entropyThreshold = this.entropyThreshold;
        clone.ignore = new HashSet<>();
        clone.ignore.addAll(this.ignore);
        return clone;
    }

    @Override
    public String toString() {
        return "TreeConfiguration{" +
                ", decision='" + decision + '\'' +
                ", entropyThreshold=" + entropyThreshold +
                ", ignore=" + ignore +
                '}';
    }

    public Set<String> getIgnore() {
        return ignore;
    }

    public TreeConfiguration setIgnore(List<String> att) {
        this.ignore.addAll(att);
        return this;
    }

    public boolean isIgnored(String attribute) {
        return ignore.contains(attribute);
    }

    public double getEntropyThreshold() {
        return entropyThreshold;
    }

    public void setEntropyThreshold(double entropyThreshold) {
        this.entropyThreshold = entropyThreshold;
    }

    public String getDecision() {
        return decision;
    }

    public TreeConfiguration setDecision(String decision) {
        this.decision = decision;
        return this;
    }

}

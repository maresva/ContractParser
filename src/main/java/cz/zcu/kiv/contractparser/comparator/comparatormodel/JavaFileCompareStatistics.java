package cz.zcu.kiv.contractparser.comparator.comparatormodel;

/**
 * Instances of this class contains statistics from comparison of two JavaFiles.
 *
 * @author Vaclav Mares
 * */
public class JavaFileCompareStatistics {

    /** How many classes were added compare to the other file */
    private int classesAdded;

    /** How many classes were removed compare to the other file */
    private int classesRemoved;

    /** How many methods were added compare to the other file */
    private int methodsAdded;

    /** How many methods were removed compare to the other file */
    private int methodsRemoved;

    /** How many contracts were added compare to the other file */
    private int contractsAdded;

    /** How many contracts were removed compare to the other file */
    private int contractsRemoved;

    /** How many contracts have changed */
    private int contractsChanged;


    public JavaFileCompareStatistics() {
    }


    public JavaFileCompareStatistics(int classesAdded, int classesRemoved, int methodsAdded, int methodsRemoved,
                                     int contractsAdded, int contractsRemoved, int contractsChanged) {
        this.classesAdded = classesAdded;
        this.classesRemoved = classesRemoved;
        this.methodsAdded = methodsAdded;
        this.methodsRemoved = methodsRemoved;
        this.contractsAdded = contractsAdded;
        this.contractsRemoved = contractsRemoved;
        this.contractsChanged = contractsChanged;
    }
    

    @Override
    public String toString() {
        return "JavaFileCompareStatistics{" +
                "classesAdded=" + classesAdded +
                ", classesRemoved=" + classesRemoved +
                ", methodsAdded=" + methodsAdded +
                ", methodsRemoved=" + methodsRemoved +
                ", contractsAdded=" + contractsAdded +
                ", contractsRemoved=" + contractsRemoved +
                ", contractsChanged=" + contractsChanged +
                '}';
    }


    public void mergeStatistics(JavaFileCompareStatistics otherStatistics){

        this.classesAdded += otherStatistics.getClassesAdded();
        this.classesRemoved += otherStatistics.getClassesRemoved();
        this.methodsAdded += otherStatistics.getMethodsAdded();
        this.methodsRemoved += otherStatistics.getMethodsRemoved();
        this.contractsAdded += otherStatistics.getContractsAdded();
        this.contractsRemoved += otherStatistics.getContractsRemoved();
        this.contractsChanged += otherStatistics.getContractsChanged();
    }


    // Getters and setters
    public int getClassesAdded() {
        return classesAdded;
    }

    public void setClassesAdded(int classesAdded) {
        this.classesAdded = classesAdded;
    }

    public void increaseClassesAdded() {
        this.classesAdded++;
    }

    public int getClassesRemoved() {
        return classesRemoved;
    }

    public void setClassesRemoved(int classesRemoved) {
        this.classesRemoved = classesRemoved;
    }

    public void increaseClassesRemoved() {
        this.classesRemoved++;
    }

    public int getMethodsAdded() {
        return methodsAdded;
    }

    public void setMethodsAdded(int methodsAdded) {
        this.methodsAdded = methodsAdded;
    }

    public void increaseMethodsAdded() {
        this.methodsAdded++;
    }

    public int getMethodsRemoved() {
        return methodsRemoved;
    }

    public void setMethodsRemoved(int methodsRemoved) {
        this.methodsRemoved = methodsRemoved;
    }

    public void increaseMethodsRemoved() {
        this.methodsRemoved++;
    }

    public int getContractsAdded() {
        return contractsAdded;
    }

    public void setContractsAdded(int contractsAdded) {
        this.contractsAdded = contractsAdded;
    }

    public void increaseContractsAdded() {
        this.contractsAdded++;
    }

    public int getContractsRemoved() {
        return contractsRemoved;
    }

    public void setContractsRemoved(int contractsRemoved) {
        this.contractsRemoved = contractsRemoved;
    }

    public void increaseContractsRemoved() {
        this.contractsRemoved++;
    }

    public int getContractsChanged() {
        return contractsChanged;
    }

    public void setContractsChanged(int contractsChanged) {
        this.contractsChanged = contractsChanged;
    }

    public void increaseContractsChanged() {
        this.contractsChanged++;
    }
}

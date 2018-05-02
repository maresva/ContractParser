package cz.zcu.kiv.contractparser.comparator.comparatormodel;

/**
 * Instances of this class contains statistics from comparison of two folders containing Java source/class files.
 *
 * @author Vaclav Mares
 * */
public class JavaFolderCompareStatistics {

    /** How many files were added compare to the other folder */
    private int filesAdded;

    /** How many files were removed compare to the other folder */
    private int filesRemoved;

    /** How many contracts were added compare to the other folder */
    private int contractsAdded;

    /** How many contracts were removed compare to the other folder */
    private int contractsRemoved;

    /** How many contracts were changed compare to the other folder */
    private int contractsChanged;


    public JavaFolderCompareStatistics(int filesAdded, int filesRemoved, int contractsAdded, int contractsRemoved, int contractsChanged) {
        this.filesAdded = filesAdded;
        this.filesRemoved = filesRemoved;
        this.contractsAdded = contractsAdded;
        this.contractsRemoved = contractsRemoved;
        this.contractsChanged = contractsChanged;
    }


    @Override
    public String toString() {
        return "JavaFolderCompareStatistics{" +
                "filesAdded=" + filesAdded +
                ", filesRemoved=" + filesRemoved +
                ", contractsAdded=" + contractsAdded +
                ", contractsRemoved=" + contractsRemoved +
                ", contractsChanged=" + contractsChanged +
                '}';
    }


    // Getters and setters
    public int getFilesAdded() {
        return filesAdded;
    }

    public void setFilesAdded(int filesAdded) {
        this.filesAdded = filesAdded;
    }

    public int getFilesRemoved() {
        return filesRemoved;
    }

    public void setFilesRemoved(int filesRemoved) {
        this.filesRemoved = filesRemoved;
    }

    public int getContractsAdded() {
        return contractsAdded;
    }

    public void setContractsAdded(int contractsAdded) {
        this.contractsAdded = contractsAdded;
    }

    public int getContractsRemoved() {
        return contractsRemoved;
    }

    public void setContractsRemoved(int contractsRemoved) {
        this.contractsRemoved = contractsRemoved;
    }

    public int getContractsChanged() {
        return contractsChanged;
    }

    public void setContractsChanged(int contractsChanged) {
        this.contractsChanged = contractsChanged;
    }
}

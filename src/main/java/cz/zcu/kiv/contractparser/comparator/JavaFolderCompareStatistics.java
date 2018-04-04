package cz.zcu.kiv.contractparser.comparator;

public class JavaFolderCompareStatistics {

    private int filesAdded;

    private int filesRemoved;

    private int contractsAdded;

    private int contractsRemoved;


    public JavaFolderCompareStatistics(int filesAdded, int filesRemoved, int contractsAdded, int contractsRemoved) {
        this.filesAdded = filesAdded;
        this.filesRemoved = filesRemoved;
        this.contractsAdded = contractsAdded;
        this.contractsRemoved = contractsRemoved;
    }

    @Override
    public String toString() {
        return "JavaFolderCompareStatistics{" +
                "filesesAdded=" + filesAdded +
                ", filesRemoved=" + filesRemoved +
                ", contractsAdded=" + contractsAdded +
                ", contractsRemoved=" + contractsRemoved +
                '}';
    }

    public int getFilesAdded() {
        return filesAdded;
    }

    public void setFilesAdded(int filesesAdded) {
        this.filesAdded = filesesAdded;
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
}

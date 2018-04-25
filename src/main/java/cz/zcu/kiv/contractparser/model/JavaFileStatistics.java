package cz.zcu.kiv.contractparser.model;

import java.util.HashMap;

/**
 * Statistics about contract extraction from Java file. It contains number of classes and methods as well as number
 * of contracts in the Java file.
 *
 * @author Vaclav Mares
 */
public class JavaFileStatistics {

    /** Total number fo files in case of use as a global statistics */
    private int numberOfFiles;

    /** Total number of classes */
    private int numberOfClasses;

    /** Total number of methods */
    private int numberOfMethods;

    /** Number of methods that contain contract(s) */
    private int numberOfMethodsWithContracts;

    /** Total number of contracts */
    private int totalNumberOfContracts;

    /** Number of contracts for each type */
    private HashMap<ContractType, Integer> numberOfContracts;


    public JavaFileStatistics() {
        this.numberOfFiles = 0;
        this.numberOfClasses = 0;
        this.numberOfMethods = 0;
        this.numberOfMethodsWithContracts = 0;
        this.totalNumberOfContracts = 0;
        this.numberOfContracts = new HashMap<>();
        
        for(ContractType contractType : ContractType.values()){
            this.numberOfContracts.put(contractType, 0);
        }
    }


    /**
     * Add all data from other statistics to this statistics. It is used to create global statistics for
     * multiple Java files.
     *
     * @param javaFileStatistics    Statistics that will be merge with this ones
     */
    public void mergeStatistics(JavaFileStatistics javaFileStatistics) {

        this.numberOfFiles += javaFileStatistics.getNumberOfFiles();
        this.numberOfClasses += javaFileStatistics.getNumberOfClasses();
        this.numberOfMethods += javaFileStatistics.getNumberOfMethods();
        this.numberOfMethodsWithContracts += javaFileStatistics.getNumberOfMethodsWithContracts();
        this.totalNumberOfContracts += javaFileStatistics.getTotalNumberOfContracts();

        for(ContractType contractType : ContractType.values()){

            int sum = this.numberOfContracts.get(contractType);
            sum += javaFileStatistics.getNumberOfContracts().get(contractType);
            this.numberOfContracts.replace(contractType, sum);
        }
    }


    @Override
    public String toString() {
        return "JavaFileStatistics{" +
                "numberOfFiles=" + numberOfFiles +
                "numberOfClasses=" + numberOfClasses +
                ", numberOfMethods=" + numberOfMethods +
                ", numberOfMethodsWithContracts=" + numberOfMethodsWithContracts +
                ", totalNumberOfContracts=" + totalNumberOfContracts +
                ", numberOfContracts=" + numberOfContracts +
                '}';
    }
    

    // Getters and Setters
    public int getNumberOfFiles() {
        return numberOfFiles;
    }

    public int getNumberOfClasses() {
        return numberOfClasses;
    }

    public int getNumberOfMethods() {
        return numberOfMethods;
    }

    public int getNumberOfMethodsWithContracts() {
        return numberOfMethodsWithContracts;
    }

    public int getTotalNumberOfContracts() {
        return totalNumberOfContracts;
    }

    public HashMap<ContractType, Integer> getNumberOfContracts() {
        return numberOfContracts;
    }

    public void setNumberOfFiles(int numberOfFiles) {
        this.numberOfFiles = numberOfFiles;
    }

    public void setNumberOfClasses(int numberOfClasses) {
        this.numberOfClasses = numberOfClasses;
    }

    public void setNumberOfMethods(int numberOfMethods) {
        this.numberOfMethods = numberOfMethods;
    }

    public void setNumberOfMethodsWithContracts(int numberOfMethodsWithContracts) {
        this.numberOfMethodsWithContracts = numberOfMethodsWithContracts;
    }

    public void setTotalNumberOfContracts(int totalNumberOfContracts) {
        this.totalNumberOfContracts = totalNumberOfContracts;
    }

    public void setNumberOfContracts(HashMap<ContractType, Integer> numberOfContracts) {
        this.numberOfContracts = numberOfContracts;
    }

    public void increaseNumberOfContracts(ContractType contractType, int increase) {
        int current = numberOfContracts.get(contractType);
        this.numberOfContracts.replace(contractType, current + increase);
        this.totalNumberOfContracts += increase;
    }

    public void increaseNumberOfClasses(int increase) {
        this.numberOfClasses += increase;
    }

    public void increaseNumberOfMethods(int increase) {
        this.numberOfMethods += increase;
    }

    public void increaseNumberOfMethodsWithContracts(int increase) {
        this.numberOfMethodsWithContracts += increase;
    }
}

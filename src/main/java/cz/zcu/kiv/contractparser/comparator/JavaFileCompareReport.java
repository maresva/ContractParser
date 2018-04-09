package cz.zcu.kiv.contractparser.comparator;

import java.util.ArrayList;
import java.util.List;

/**
 * Instances of this class represent reports about comparison of two JavaFiles. It contains information about API
 * changes as well as information about contract comparisons.
 *
 * @author Vaclav Mares
 */
public class JavaFileCompareReport {

    /** Path of the first compared file */
    private String thisFilePath;

    /** Path of the second compared file */
    private String otherFilePath;

    /** Whether are the two java files equal in terms of API */
    private boolean apiEqual;

    /** Whether have the two java files equal all contracts */
    private boolean contractEqual;

    /** Statistics containing various summaries about this comparison */
    private JavaFileCompareStatistics javaFileCompareStatistics;

    /** List of all API differences between those files */
    private List<ApiChange> apiChanges;

    /** List of all contract comparison reports */
    private List<ContractCompareReport> contractCompareReports;


    JavaFileCompareReport(String thisFilePath, String otherFilePath) {
        this.thisFilePath = thisFilePath;
        this.otherFilePath = otherFilePath;
        this.apiEqual = true;
        this.contractEqual = true;
        this.javaFileCompareStatistics = new JavaFileCompareStatistics();
        this.apiChanges = new ArrayList<>();
        this.contractCompareReports = new ArrayList<>();
    }


    @Override
    public String toString() {
        return "JavaFileCompareReport{" +
                "thisFilePath='" + thisFilePath + '\'' +
                ", otherFilePath='" + otherFilePath + '\'' +
                ", apiEqual=" + apiEqual +
                ", contractEqual=" + contractEqual +
                ", javaFileCompareStatistics=" + javaFileCompareStatistics +
                ", apiChanges=" + apiChanges +
                ", contractCompareReports=" + contractCompareReports +
                '}';
    }

    /**
     * Adds API change to the list as well as updates statistics.
     *
     * @param apiChange                 New API change
     * @param reportNonContractChanges  If changes not affecting any contracts are being reported
     */
    public void addApiChange(ApiChange apiChange, boolean reportNonContractChanges){

        if(reportNonContractChanges || apiChange.getNumberOfContracts() > 0) {
            this.apiChanges.add(apiChange);

            if(apiChange.getApiType() == ApiType.CLASS){

                if(apiChange.getApiState() == ApiState.ADDED){
                    this.javaFileCompareStatistics.increaseClassesAdded();
                }

                if(apiChange.getApiState() == ApiState.REMOVED){
                    this.javaFileCompareStatistics.increaseClassesRemoved();
                }
            }
            else{

                if(apiChange.getApiState() == ApiState.ADDED){
                    this.javaFileCompareStatistics.increaseMethodsAdded();
                }

                if(apiChange.getApiState() == ApiState.REMOVED){
                    this.javaFileCompareStatistics.increaseMethodsRemoved();
                }
            }
        }
    }

    /**
     * Adds Contract change report to the list as well as updates statistics.
     *
     * @param contractCompareReport  New contract compare report
     */
    public void addContractReport(ContractCompareReport contractCompareReport){
        this.contractCompareReports.add(contractCompareReport);

        if(contractCompareReport.getApiState() == ApiState.ADDED){
            this.javaFileCompareStatistics.increaseContractsAdded();
        }
        else if (contractCompareReport.getApiState() == ApiState.REMOVED){
            this.javaFileCompareStatistics.increaseContractsRemoved();
        }
        else if(contractCompareReport.getContractComparison() != ContractComparison.EQUAL){
            this.javaFileCompareStatistics.increaseContractsChanged();
        }
    }


    // Getters and setters
    public String getThisFilePath() {
        return thisFilePath;
    }

    public String getOtherFilePath() {
        return otherFilePath;
    }

    public List<ApiChange> getApiChanges() {
        return apiChanges;
    }

    public List<ContractCompareReport> getContractCompareReports() {
        return contractCompareReports;
    }

    public void setThisFilePath(String thisFilePath) {
        this.thisFilePath = thisFilePath;
    }

    public void setOtherFilePath(String otherFilePath) {
        this.otherFilePath = otherFilePath;
    }

    public void setApiChanges(List<ApiChange> apiChanges) {
        this.apiChanges = apiChanges;
    }

    public void setContractCompareReports(List<ContractCompareReport> contractCompareReports) {
        this.contractCompareReports = contractCompareReports;
    }

    public boolean isApiEqual() {
        return apiEqual;
    }

    public void setApiEqual(boolean apiEqual) {
        this.apiEqual = apiEqual;
    }

    public boolean isContractEqual() {
        return contractEqual;
    }

    public void setContractEqual(boolean contractEqual) {
        this.contractEqual = contractEqual;
    }

    public JavaFileCompareStatistics getJavaFileCompareStatistics() {
        return javaFileCompareStatistics;
    }

    public void setJavaFileCompareStatistics(JavaFileCompareStatistics javaFileCompareStatistics) {
        this.javaFileCompareStatistics = javaFileCompareStatistics;
    }
}

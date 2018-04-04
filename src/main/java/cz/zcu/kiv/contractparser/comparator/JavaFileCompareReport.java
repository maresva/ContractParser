package cz.zcu.kiv.contractparser.comparator;

import java.util.ArrayList;
import java.util.List;

public class JavaFileCompareReport {

    private String thisFilePath;

    private String otherFilePath;

    private boolean apiEqual;

    private boolean contractEqual;

    private List<ApiChange> apiChanges;

    private List<ContractCompareReport> contractCompareReports;


    public JavaFileCompareReport(String thisFilePath, String otherFilePath) {
        this.thisFilePath = thisFilePath;
        this.otherFilePath = otherFilePath;
        this.apiChanges = new ArrayList<>();
        this.contractCompareReports = new ArrayList<>();
        this.apiEqual = true;
        this.contractEqual = true;
    }


    @Override
    public String toString() {
        return "JavaFileCompareReport{" +
                "thisFilePath='" + thisFilePath + '\'' +
                ", otherFilePath='" + otherFilePath + '\'' +
                ", apiEqual=" + apiEqual +
                ", contractEqual=" + contractEqual +
                ", apiChanges=" + apiChanges +
                ", contractCompareReports=" + contractCompareReports +
                '}';
    }


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

    public void addApiChange(ApiChange apiChange, boolean reportNonContractChanges){

        if(reportNonContractChanges || apiChange.getNumberOfContracts() > 0) {
            this.apiChanges.add(apiChange);
        }
    }

    public void addContractReport(ContractCompareReport contractCompareReport){
        this.contractCompareReports.add(contractCompareReport);
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
}

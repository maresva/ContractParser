package cz.zcu.kiv.contractparser.comparator;

import java.util.List;

public class JavaFileCompareReport {

    private JavaFileComparison javaFileComparison;

    private List<ContractCompareReport> contractReports;


    public JavaFileCompareReport(JavaFileComparison javaFileComparison, List<ContractCompareReport> contractReports) {
        this.javaFileComparison = javaFileComparison;
        this.contractReports = contractReports;
    }

    public JavaFileComparison getJavaFileComparison() {
        return javaFileComparison;
    }

    public List<ContractCompareReport> getContractReports() {
        return contractReports;
    }

    public void setJavaFileComparison(JavaFileComparison javaFileComparison) {
        this.javaFileComparison = javaFileComparison;
    }

    public void setContractReports(List<ContractCompareReport> contractReports) {
        this.contractReports = contractReports;
    }
}

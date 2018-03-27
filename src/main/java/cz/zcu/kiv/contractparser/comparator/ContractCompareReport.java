package cz.zcu.kiv.contractparser.comparator;

public class ContractCompareReport {

    private ContractComparison contractComparison;
    private String className;
    private String methodName;
    private String thisContractExpression;
    private String otherContractExpression;


    public ContractCompareReport(ContractComparison contractComparison, String className, String methodName,
                                 String thisContractExpression, String otherContractExpression) {
        this.contractComparison = contractComparison;
        this.className = className;
        this.methodName = methodName;
        this.thisContractExpression = thisContractExpression;
        this.otherContractExpression = otherContractExpression;
    }


    public ContractComparison getContractComparison() {
        return contractComparison;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getThisContractExpression() {
        return thisContractExpression;
    }

    public String getOtherContractExpression() {
        return otherContractExpression;
    }

    public void setContractComparison(ContractComparison contractComparison) {
        this.contractComparison = contractComparison;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setThisContractExpression(String thisContractExpression) {
        this.thisContractExpression = thisContractExpression;
    }

    public void setOtherContractExpression(String otherContractExpression) {
        this.otherContractExpression = otherContractExpression;
    }
}

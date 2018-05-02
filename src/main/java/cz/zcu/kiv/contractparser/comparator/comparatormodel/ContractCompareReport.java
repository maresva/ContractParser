package cz.zcu.kiv.contractparser.comparator.comparatormodel;

/**
 * Instances of this class are created when comparing two contracts in the scope of JavaFile. It says what comparison
 * between them is, if one or the other has been added or removed. It also contains expression of both contracts as well
 * as parent method and class.
 *
 * @author Vaclav Mares
 * */
public class ContractCompareReport {

    /** Enum describing comparison between two contracts */
    private ContractComparison contractComparison;

    /** Enum describing whether one contract was added, removed or pair was found */
    private ApiState apiState;

    /** Name of parent class of contracts */
    private String className;

    /** Name of parent method of contracts */
    private String methodName;

    /** Expression of the first contract */
    private String thisContractExpression;

    /** Expression of the second contract */
    private String otherContractExpression;


    public ContractCompareReport(ContractComparison contractComparison, String className, String methodName,
                          String thisContractExpression, String otherContractExpression, ApiState apiState) {
        this.contractComparison = contractComparison;
        this.className = className;
        this.methodName = methodName;
        this.thisContractExpression = thisContractExpression;
        this.otherContractExpression = otherContractExpression;
        this.apiState = apiState;
    }


    @Override
    public String toString() {
        return "ContractCompareReport{" +
                "contractComparison=" + contractComparison +
                ", className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", thisContractExpression='" + thisContractExpression + '\'' +
                ", otherContractExpression='" + otherContractExpression + '\'' +
                ", apiState=" + apiState +
                '}';
    }
    

    // Getters and setters
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

    public ApiState getApiState() {
        return apiState;
    }

    public void setApiState(ApiState apiState) {
        this.apiState = apiState;
    }
}

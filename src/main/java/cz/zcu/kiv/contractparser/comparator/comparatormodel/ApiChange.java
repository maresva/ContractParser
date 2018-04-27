package cz.zcu.kiv.contractparser.comparator.comparatormodel;

/**
 * Instances of this class are used to specify changes in API when comparing two classes or methods.
 *
 * @author Vaclav Mares
 * */
public class ApiChange {

    /** Type of API that is compared (class or method) */
    private ApiType apiType;

    /** Signature of affected class or method */
    private String signature;

    /** Number of contracts that has been part of this API change */
    private int numberOfContracts;

    /** Kind of API state - added, removed, found pair */
    private ApiState apiState;


    public ApiChange(ApiType apiType, String signature, int numberOfContracts, ApiState apiState) {
        this.apiType = apiType;
        this.signature = signature;
        this.numberOfContracts = numberOfContracts;
        this.apiState = apiState;
    }


    // Getters and setters
    public ApiType getApiType() {
        return apiType;
    }

    public String getSignature() {
        return signature;
    }

    public ApiState getApiState() {
        return apiState;
    }

    public void setApiType(ApiType apiType) {
        this.apiType = apiType;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setApiState(ApiState apiState) {
        this.apiState = apiState;
    }

    public int getNumberOfContracts() {
        return numberOfContracts;
    }

    public void setNumberOfContracts(int numberOfContracts) {
        this.numberOfContracts = numberOfContracts;
    }
}

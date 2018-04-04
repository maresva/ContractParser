package cz.zcu.kiv.contractparser.comparator;

public class ApiChange {

    private ApiType apiType;

    private String signature;

    private int numberOfContracts;

    private ApiState apiState;


    public ApiChange(ApiType apiType, String signature, int numberOfContracts, ApiState apiState) {
        this.apiType = apiType;
        this.signature = signature;
        this.numberOfContracts = numberOfContracts;
        this.apiState = apiState;
    }

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

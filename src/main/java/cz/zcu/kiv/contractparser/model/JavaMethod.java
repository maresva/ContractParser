package cz.zcu.kiv.contractparser.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Instances of this class represents individual parsed methods. It contains its signature, inner methods,
 * its body, javaDoc, and class invariants
 *
 * @author Vaclav Mares
 */
public class JavaMethod {

    /** Signature of the method used for its identification */
    protected String signature;

    /** List of inner methods in this method */
    private List<JavaMethod> innerJavaMethods;

    /** List of contracts in this method */
    protected List<Contract> contracts;

    protected HashMap<ContractType, Integer> numberOfContracts;


    public JavaMethod() {
        innerJavaMethods = new ArrayList<>();
        contracts = new ArrayList<>();
        numberOfContracts = new HashMap<>();

        for(ContractType contractType : ContractType.values()){
            numberOfContracts.put(contractType, 0);
        }
    }

    @Override
    public String toString() {
        return "JavaMethod{" +
                "signature='" + signature + '\'' +
                ", innerJavaMethods=" + innerJavaMethods +
                ", contracts=" + contracts +
                '}';
    }


    // Getters and Setters

    public String getSignature() {
        return signature;
    }

    public List<JavaMethod> getInnerJavaMethods() {
        return innerJavaMethods;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public HashMap<ContractType, Integer> getNumberOfContracts() {
        return numberOfContracts;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    public void addInnerMethod(JavaMethod javaMethod) {
        this.innerJavaMethods.add(javaMethod);
    }

    public void addContract(Contract contract) {
        this.contracts.add(contract);
    }

    public void increaseNumberOfContracts(ContractType contractType, int increase) {
        int current = numberOfContracts.get(contractType);
        this.numberOfContracts.replace(contractType, current + increase);
    }
}

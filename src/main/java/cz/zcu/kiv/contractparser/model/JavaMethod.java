package cz.zcu.kiv.contractparser.model;

import java.util.ArrayList;
import java.util.List;


/**
 * Instances of this class represents individual parsed methods. It contains its signature, inner methods,
 * its body, javaDoc, and class invariants
 *
 * @author Vaclav Mares
 */
public class JavaMethod {

    /** Signature of the method used for its identification */
    String signature;

    /** Determines whether the method is a constructor */
    private boolean isConstructor;

    /** List of contracts in this method */
    protected List<Contract> contracts;


    public JavaMethod(String signature, boolean isConstructor) {
        this.signature = signature;
        this.isConstructor = isConstructor;
        contracts = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "JavaMethod{" +
                "signature='" + signature + '\'' +
                ", contracts=" + contracts +
                '}';
    }


    // Getters and Setters

    public String getSignature() {
        return signature;
    }

    public boolean isConstructor() {
        return isConstructor;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setConstructor(boolean constructor) {
        isConstructor = constructor;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    public void addContract(Contract contract) {
        this.contracts.add(contract);
    }
}

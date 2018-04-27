package cz.zcu.kiv.contractparser.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Instances of this class represents individual parsed classes. It contains its name, inner classes,
 * methods, javaDoc, attributes and class invariants
 *
 * @author Vaclav Mares
 */
public class JavaClass {

    /** Name of the class used for its identification */
    protected String name;

    /** Complete signature of the class */
    protected String signature;

    /** List of methods in this class */
    protected List<JavaMethod> javaMethods;

    /** List of invariants in this class */
    protected List<Contract> invariants;


    public JavaClass(String name, String signature) {
        this.name = name;
        this.signature = signature;
        javaMethods = new ArrayList<>();
        invariants = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "JavaClass{" +
                "name='" + name + '\'' +
                ", signature=" + signature +
                ", javaMethods=" + javaMethods +
                ", invariants=" + invariants +
                '}';
    }


    public int getTotalNumberOfContracts() {

        int totalNumberOfContracts = 0;
        for(JavaMethod javaMethod : javaMethods){
            totalNumberOfContracts += javaMethod.getContracts().size();
        }

        return totalNumberOfContracts;
    }


    // Getters and Setters

    public String getName() {
        return name;
    }

    public String getSignature() {
        return signature;
    }

    public List<JavaMethod> getJavaMethods() {
        return javaMethods;
    }

    public List<Contract> getInvariants() {
        return invariants;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setInvariants(List<Contract> invariants) {
        this.invariants = invariants;
    }

    public void addJavaMethod(JavaMethod javaMethod) {
        this.javaMethods.add(javaMethod);
    }

    public void addInvariant(Contract contract) {
        this.invariants.add(contract);
    }
}

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

    /** List of methods in this class */
    private List<JavaMethod> javaMethods;

    /** List of invariants in this class */
    protected List<Contract> invariants;

    protected int numberOfMethods;


    public JavaClass(String name) {
        this.name = name;
        javaMethods = new ArrayList<>();
        invariants = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "JavaClass{" +
                "name='" + name + '\'' +
                ", javaMethods=" + javaMethods +
                ", invariants=" + invariants +
                '}';
    }


    // Getters and Setters

    public String getName() {
        return name;
    }

    public List<JavaMethod> getJavaMethods() {
        return javaMethods;
    }

    public List<Contract> getInvariants() {
        return invariants;
    }

    public int getNumberOfMethods() {
        return numberOfMethods;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInvariants(List<Contract> invariants) {
        this.invariants = invariants;
    }

    public void addJavaMethod(JavaMethod javaMethod) {
        this.javaMethods.add(javaMethod);
    }

    public void addInvariants(Contract contract) {
        this.invariants.add(contract);
    }

    public void increaseNumberOfMethods(int count) {
        numberOfMethods += count;
    }
}

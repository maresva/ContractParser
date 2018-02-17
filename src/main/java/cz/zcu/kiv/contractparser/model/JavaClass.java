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

    /** List of inner classes in this class */
    private List<JavaClass> innerJavaClasses;

    /** List of methods in this class */
    private List<JavaMethod> javaMethods;

    /** List of invariants in this class */
    protected List<Contract> invariants;

    protected int numberOfMethods;


    public JavaClass() {
        innerJavaClasses = new ArrayList<>();
        javaMethods = new ArrayList<>();
        invariants = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "JavaClass{" +
                "name='" + name + '\'' +
                ", innerJavaClasses=" + innerJavaClasses +
                ", javaMethods=" + javaMethods +
                ", invariants=" + invariants +
                '}';
    }


    // Getters and Setters

    public String getName() {
        return name;
    }

    public List<JavaClass> getInnerJavaClasses() {
        return innerJavaClasses;
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

    public void addInnerClass(JavaClass javaClass) {
        this.innerJavaClasses.add(javaClass);
    }

    public void addInvariants(Contract contract) {
        this.invariants.add(contract);
    }

    public void increaseNumberOfMethods(int count) {
        numberOfMethods += count;
    }
}

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
    private String name;

    /** List of inner classes in this class */
    private List<JavaClass> innerClasses;

    /** List of methods in this class */
    private List<JavaMethod> methods;

    /** List of contracts in this class */
    private List<String> annotations;

    /** List of invariants in this class */
    private List<Contract> invariants;


    public JavaClass() {
        innerClasses = new ArrayList<>();
        methods = new ArrayList<>();
        invariants = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "JavaClass{" +
                "name='" + name + '\'' +
                ", innerClasses=" + innerClasses +
                ", methods=" + methods +
                ", annotations=" + annotations +
                ", invariants=" + invariants +
                '}';
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public List<JavaClass> getInnerClasses() {
        return innerClasses;
    }

    public List<JavaMethod> getMethods() {
        return methods;
    }

    public List<String> getAnnotations() {
        return annotations;
    }

    public List<Contract> getInvariants() {
        return invariants;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addAnnotation(String annotation) {
        this.annotations.add(annotation);
    }

    public void addMethod(JavaMethod javaMethod) {
        this.methods.add(javaMethod);
    }

    public void addInnerClass(JavaClass javaClass) {
        this.innerClasses.add(javaClass);
    }

    public void addInvariants(Contract contract) {
        this.invariants.add(contract);
    }
}

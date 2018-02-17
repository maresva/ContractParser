package cz.zcu.kiv.contractparser.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Instances of this class represents individual parsed classes. It contains its name, inner classes,
 * methods, javaDoc, attributes and class invariants
 *
 * @author Vaclav Mares
 */
public class ExtendedJavaClass extends JavaClass{

    /** List of contracts in this class */
    private List<String> annotations;

    /** List of inner classes in this class */
    protected List<ExtendedJavaClass> innerExtendedJavaClasses;

    /** List of methods in this class */
    protected List<ExtendedJavaMethod> extendedJavaMethods;
    

    public ExtendedJavaClass() {
        super();
        innerExtendedJavaClasses = new ArrayList<>();
        extendedJavaMethods = new ArrayList<>();
        annotations = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "ExtendedJavaClass{" +
                "name='" + name + '\'' +
                ", innerExtendedJavaClasses=" + innerExtendedJavaClasses +
                ", extendedJavaMethods=" + extendedJavaMethods +
                ", annotations=" + annotations +
                ", invariants=" + invariants +
                '}';
    }

    
    // Getters and Setters

    public List<String> getAnnotations() {
        return annotations;
    }

    public List<ExtendedJavaClass> getInnerExtendedJavaClasses() {
        return innerExtendedJavaClasses;
    }

    public List<ExtendedJavaMethod> getExtendedJavaMethods() {
        return extendedJavaMethods;
    }

    public void addAnnotation(String annotation) {
        this.annotations.add(annotation);
    }

    public void addInnerExtendedJavaClass(ExtendedJavaClass extendedJavaClass) {
        this.innerExtendedJavaClasses.add(extendedJavaClass);
    }

    public void addExtendedJavaMethod(ExtendedJavaMethod extendedJavaMethod) {
        this.extendedJavaMethods.add(extendedJavaMethod);
    }
}

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

    /** List of annotations in this class */
    private List<String> annotations;

    /** List of methods in this class */
    protected List<ExtendedJavaMethod> extendedJavaMethods;
    

    public ExtendedJavaClass(String name) {
        super(name);
        extendedJavaMethods = new ArrayList<>();
        annotations = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "ExtendedJavaClass{" +
                "name='" + name + '\'' +
                ", extendedJavaMethods=" + extendedJavaMethods +
                ", annotations=" + annotations +
                ", invariants=" + invariants +
                '}';
    }

    
    // Getters and Setters

    public List<String> getAnnotations() {
        return annotations;
    }

    public List<ExtendedJavaMethod> getExtendedJavaMethods() {
        return extendedJavaMethods;
    }

    public void addAnnotation(String annotation) {
        this.annotations.add(annotation);
    }

    public void addExtendedJavaMethod(ExtendedJavaMethod extendedJavaMethod) {
        this.extendedJavaMethods.add(extendedJavaMethod);
    }
}

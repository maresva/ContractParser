package cz.zcu.kiv.contractparser.parser.parsermodel;

import com.github.javaparser.ast.expr.AnnotationExpr;
import cz.zcu.kiv.contractparser.model.JavaClass;

import java.util.ArrayList;
import java.util.List;

/**
 * Instances of this class represents individual parsed classes. It contains its name, inner classes,
 * methods, javaDoc, attributes and class invariants
 *
 * @author Vaclav Mares
 */
public class ExtendedJavaClass extends JavaClass {

    /** List of annotations in this class */
    private List<AnnotationExpr> annotations;

    /** List of methods in this class */
    private List<ExtendedJavaMethod> extendedJavaMethods;
    

    public ExtendedJavaClass(String name, String signature) {
        super(name, signature);
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

    public List<AnnotationExpr> getAnnotations() {
        return annotations;
    }

    public List<ExtendedJavaMethod> getExtendedJavaMethods() {
        return extendedJavaMethods;
    }

    public void addAnnotation(AnnotationExpr annotation) {
        this.annotations.add(annotation);
    }

    public void addExtendedJavaMethod(ExtendedJavaMethod extendedJavaMethod) {
        this.extendedJavaMethods.add(extendedJavaMethod);
    }
}

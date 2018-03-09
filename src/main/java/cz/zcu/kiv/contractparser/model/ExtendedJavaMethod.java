package cz.zcu.kiv.contractparser.model;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.Parameter;

import java.util.ArrayList;
import java.util.List;


/**
 * Instances of this class represents individual parsed methods. It contains its signature, inner methods,
 * its body, javaDoc, and class invariants
 *
 * @author Vaclav Mares
 */
public class ExtendedJavaMethod extends JavaMethod {

    /** Annotations for this method */
    protected List<String> annotations;

    /** List of input parameters */
    protected List<Parameter> parameters;

    /** The whole body of method */
    protected List<Node> body;

    /** List of inner methods in this method */
    protected List<ExtendedJavaMethod> innerExtendedJavaMethods;


    public ExtendedJavaMethod() {
        super();
        annotations = new ArrayList<>();
        parameters = new ArrayList<>();
        body = new ArrayList<>();
        innerExtendedJavaMethods = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "ExtendedJavaMethod{" +
                "signature='" + signature + '\'' +
                ", annotations=" + annotations +
                ", parameters=" + parameters +
                ", body=" + body +
                ", innerExtendedJavaMethods=" + innerExtendedJavaMethods +
                ", contracts=" + contracts +
                '}';
    }


    // Getters and Setters

    public List<String> getAnnotations() {
        return annotations;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public List<Node> getBody() {
        return body;
    }

    public List<ExtendedJavaMethod> getInnerExtendedJavaMethods() {
        return innerExtendedJavaMethods;
    }

    public void addAnnotation(String annotation) {
        this.annotations.add(annotation);
    }

    public void addParameter(Parameter parameter) {
        this.parameters.add(parameter);
    }

    public void addBodyNode(Node node) {
        this.body.add(node);
    }

    public void addInnerExtendedJavaMethod(ExtendedJavaMethod extendedJavaMethod) {
       this.innerExtendedJavaMethods.add(extendedJavaMethod);
    }
}

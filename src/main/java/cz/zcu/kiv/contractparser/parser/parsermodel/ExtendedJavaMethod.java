package cz.zcu.kiv.contractparser.parser.parsermodel;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import cz.zcu.kiv.contractparser.model.JavaMethod;

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
    private List<AnnotationExpr> annotations;

    /** List of input parameters */
    private List<Parameter> parameters;

    /** The whole body of method */
    private List<Node> body;


    public ExtendedJavaMethod(String signature, boolean isConstructor) {
        super(signature, isConstructor);
        annotations = new ArrayList<>();
        parameters = new ArrayList<>();
        body = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "ExtendedJavaMethod{" +
                "signature='" + signature + '\'' +
                ", annotations=" + annotations +
                ", parameters=" + parameters +
                ", body=" + body +
                ", contracts=" + contracts +
                '}';
    }


    // Getters and Setters

    public List<AnnotationExpr> getAnnotations() {
        return annotations;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public List<Node> getBody() {
        return body;
    }

    public void addAnnotation(AnnotationExpr annotation) {
        this.annotations.add(annotation);
    }

    public void addParameter(Parameter parameter) {
        this.parameters.add(parameter);
    }

    public void addBodyNode(Node node) {
        this.body.add(node);
    }
}

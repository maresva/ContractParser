package cz.zcu.kiv.contractparser.model;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.Statement;
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
    private String signature;

    /** Annotations for this method */
    private List<String> annotations;

    /** The whole body of method */
    private List<Node> body;

    /** List of inner methods in this method */
    private List<JavaMethod> innerMethods;

    /** List of contracts in this method */
    private List<Contract> contracts;


    public JavaMethod() {
        annotations = new ArrayList<>();
        body = new ArrayList<>();
        innerMethods = new ArrayList<>();
        contracts = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "JavaMethod{" +
                "signature='" + signature + '\'' +
                ", annotations=" + annotations +
                ", body=" + body +
                ", innerMethods=" + innerMethods +
                ", contracts=" + contracts +
                '}';
    }

    // Getters and Setters


    public String getSignature() {
        return signature;
    }

    public List<String> getAnnotations() {
        return annotations;
    }

    public List<Node> getBody() {
        return body;
    }

    public List<JavaMethod> getInnerMethods() {
        return innerMethods;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void addAnnotation(String annotation) {
        this.annotations.add(annotation);
    }

    public void addBodyNode(Node node) {
        this.body.add(node);
    }

    public void addInnerMethod(JavaMethod javaMethod) {
        this.innerMethods.add(javaMethod);
    }

    public void addContract(Contract contract) {
        this.contracts.add(contract);
    }
}

package cz.zcu.kiv.contractparser.model;

import java.util.ArrayList;
import java.util.List;


/**
 * Instances of this class represents individual parsed methods. It contains its signature, inner methods,
 * its body, javaDoc, and class invariants
 *
 * @Author Václav Mareš
 */
public class JavaMethod {

    /** Signature of the method used for its identification */
    public String signature;

    /** Returned type of the method
     *  TODO je potreba ?
     * */
    public String returnType;

    /** List of method input parameters
     *  TODO je potreba ?
     * */
    public List<InputParameter> parameters;

    /** The whole body of method */
    public String body;

    /** JavaDoc for this method */
    // TODO anotace soucasti nebo zvlast ?
    public String javaDoc;

    /** List of inner methods in this method */
    public List<JavaMethod> innerMethods;

    /** List of contracts in this method */
    public List<Contract> contracts;


    public JavaMethod() {
        innerMethods = new ArrayList<>();
        contracts = new ArrayList<>();
    }
}

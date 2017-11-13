package cz.zcu.kiv.contractparser.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Instances of this class represents individual parsed classes. It contains its name, inner classes,
 * methods, javaDoc, attributes and class invariants
 *
 * @Author Václav Mareš
 */
public class JavaClass {

    /** Name of the class used for its identification */
    public String name;

    /** List of inner classes in this class */
    public List<JavaClass> innerClasses;

    /** List of methods in this class */
    public List<JavaMethod> methods;

    /** JavaDoc for this class */
    // TODO anotace soucasti nebo zvlast ?
    public String javaDoc;

    /** List of invariants in this class */
    public List<Contract> invariants;


    public JavaClass() {
        innerClasses = new ArrayList<>();
        methods = new ArrayList<>();
        invariants = new ArrayList<>();
    }
}

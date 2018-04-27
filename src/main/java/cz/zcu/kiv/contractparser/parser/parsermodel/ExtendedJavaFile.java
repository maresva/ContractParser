package cz.zcu.kiv.contractparser.parser.parsermodel;

import cz.zcu.kiv.contractparser.model.JavaFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Instances of this class represents individual files parsed for better processing.
 *
 * @author Vaclav Mares
 */
public class ExtendedJavaFile extends JavaFile {

    /** List of classes in the file */
    private List<ExtendedJavaClass> extendedJavaClasses;


    public ExtendedJavaFile() {
        super();
        extendedJavaClasses = new ArrayList<>();
    }

    
    @Override
    public String toString() {
        return super.toString() + "ExtendedJavaFile{" +
                "extendedJavaClasses=" + extendedJavaClasses +
                '}';
    }


    // Getters and Setters
    public List<ExtendedJavaClass> getExtendedJavaClasses() {
        return extendedJavaClasses;
    }

    public void addExtendedJavaClass(ExtendedJavaClass extendedJavaClass) {
        this.extendedJavaClasses.add(extendedJavaClass);
    }
}

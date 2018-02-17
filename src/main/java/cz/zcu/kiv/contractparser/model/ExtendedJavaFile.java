package cz.zcu.kiv.contractparser.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Instances of this class represents individual files parsed for better processing.
 *
 * @author Vaclav Mares
 */
public class ExtendedJavaFile extends JavaFile{

    /** List of all imports in the file */
    private List<String> imports;

    /** List of classes in the file */
    protected List<ExtendedJavaClass> extendedJavaClasses;


    public ExtendedJavaFile() {
        super();
        imports = new ArrayList<>();
        extendedJavaClasses = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "ExtendedJavaFile{" +
                "fileName='" + fileName + '\'' +
                "path='" + path + '\'' +
                ", fileType=" + fileType +
                ", extendedJavaClasses=" + extendedJavaClasses +
                ", imports=" + imports +
                '}';
    }


    // Getters and Setters
    public List<String> getImports() {
        return imports;
    }

    public List<ExtendedJavaClass> getExtendedJavaClasses() {
        return extendedJavaClasses;
    }

    public void addImport(String importString) {
        this.imports.add(importString);
    }

    public void addExtendedJavaClass(ExtendedJavaClass extendedJavaClass) {
        this.extendedJavaClasses.add(extendedJavaClass);
    }
}

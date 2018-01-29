package cz.zcu.kiv.contractparser.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Instances of this class represents individual files parsed for better processing.
 *
 * @author Vaclav Mares
 */
public class JavaFile {

    /** Name of the original file */
    private String fileName;

    /** Type of the original file (*.class or *.java) */
    private FileType fileType;

    /** List of classes in the file */
    private List<JavaClass> classes;

    /** List of all imports in the file */
    private List<String> imports;


    public JavaFile() {
        classes = new ArrayList<>();
        imports = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "JavaFile{" +
                "fileName='" + fileName + '\'' +
                ", fileType=" + fileType +
                ", classes=" + classes +
                ", imports=" + imports +
                '}';
    }

    // Getters and Setters

    public String getFileName() {
        return fileName;
    }

    public FileType getFileType() {
        return fileType;
    }

    public List<JavaClass> getClasses() {
        return classes;
    }

    public List<String> getImports() {
        return imports;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public void addClass(JavaClass javaClass) {
        this.classes.add(javaClass);
    }

    public void addImport(String importString) {
        this.imports.add(importString);
    }
}

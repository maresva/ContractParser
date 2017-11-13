package cz.zcu.kiv.contractparser.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Instances of this class represents individual files parsed for better processing.
 *
 * @Author Václav Mareš
 */
public class JavaFile {

    /** Name of the original file */
    public String fileName;

    /** Type of the original file (*.class or *.java) */
    public FileType fileType;

    /** List of classes in the file */
    public List<JavaClass> classes;

    /** List of all imports in the file */
    public List<String> imports;


    public JavaFile() {

        classes = new ArrayList<>();
        imports = new ArrayList<>();
    }
}

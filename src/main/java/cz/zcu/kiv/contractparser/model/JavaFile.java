package cz.zcu.kiv.contractparser.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Instances of this class represents individual files parsed for better processing.
 *
 * @author Vaclav Mares
 */
public class JavaFile {

    /** Name of the original file */
    protected String fileName;

    /** Path of original file */
    protected String path;

    /** Type of the original file (*.class or *.java) */
    protected FileType fileType;

    protected int numberOfClasses;

    protected int numberOfMethods;

    protected HashMap<ContractType, Integer> numberOfContracts;


    /** List of classes in the file */
    private List<JavaClass> javaClasses;


    public JavaFile() {
        javaClasses = new ArrayList<>();
        numberOfClasses = 0;
        numberOfMethods = 0;
        numberOfContracts = new HashMap<>();

        for(ContractType contractType : ContractType.values()){
            numberOfContracts.put(contractType, 0);
        }
    }

    @Override
    public String toString() {
        return "JavaFile{" +
                "fileName='" + fileName + '\'' +
                "path='" + path + '\'' +
                ", fileType=" + fileType +
                ", javaClasses=" + javaClasses +
                '}';
    }

    // Getters and Setters

    public String getFileName() {
        return fileName;
    }

    public String getPath() {
        return path;
    }

    public FileType getFileType() {
        return fileType;
    }

    public List<JavaClass> getClasses() {
        return javaClasses;
    }

    public int getNumberOfClasses() {
        return numberOfClasses;
    }

    public int getNumberOfMethods() {
        return numberOfMethods;
    }

    public HashMap<ContractType, Integer> getNumberOfContracts() {
        return numberOfContracts;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public void setNumberOfClasses(int numberOfClasses) {
        this.numberOfClasses = numberOfClasses;
    }

    public void setNumberOfMethods(int numberOfMethods) {
        this.numberOfMethods = numberOfMethods;
    }

    public void setNumberOfContracts(HashMap<ContractType, Integer> numberOfContracts) {
        this.numberOfContracts = numberOfContracts;
    }

    public void increaseNumberOfClasses(int increase) {
        this.numberOfClasses += increase;
    }

    public void increaseNumberOfMethods(int increase) {
        this.numberOfMethods += increase;
    }

    public void increaseNumberOfContracts(ContractType contractType, int increase) {
        int current = numberOfContracts.get(contractType);
        this.numberOfContracts.replace(contractType, current + increase);
    }

    public void addClass(JavaClass javaClass) {
        this.javaClasses.add(javaClass);
    }
}

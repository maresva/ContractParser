package cz.zcu.kiv.contractparser.model;

import cz.zcu.kiv.contractparser.comparator.JavaFileCompareReport;
import cz.zcu.kiv.contractparser.comparator.JavaFileComparator;

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

    /** Path of original file */
    private String fullPath;

    /** Path in shortest possible way in compare to other extracted files */
    private String shortPath;

    /** Type of the original file (*.class or *.java) */
    private FileType fileType;

    /** JavaFileStatistics about this JavaFile */
    private JavaFileStatistics javaFileStatistics;


    /** List of classes in the file */
    private List<JavaClass> javaClasses;


    public JavaFile() {
        this.javaClasses = new ArrayList<>();
        this.javaFileStatistics = new JavaFileStatistics();
        this.javaFileStatistics = new JavaFileStatistics();
    }


    /**
     * Compare this JavaFile to other one. It creates report about their differences in API and contracts. Reporting of
     * equal objects as well as those that don't contain contracts can be turned off.
     *
     * @param otherJavaFile             avaFile to be compared with
     * @param reportEqual               Whether equal object should be reported or not
     * @param reportNonContractChanges  Whether changes in objects that don't contain contracts should be reported or not
     * @return                          JavaFileCompareReport containing all gathered information about comparison
     */
    public JavaFileCompareReport compareJavaFileTo(JavaFile otherJavaFile, boolean reportEqual,
                                                   boolean reportNonContractChanges){

        JavaFileComparator javaFileComparator = new JavaFileComparator();
        return javaFileComparator.compareJavaFiles(this, otherJavaFile, reportEqual, reportNonContractChanges);
    }


    /**
     * Retrieve all contracts from given JavaFile across all classes and methods.
     *
     * @return  List of contracts
     */
    public List<Contract> getContracts(){

        List<Contract> contracts = new ArrayList<>();

        for(JavaClass javaClass : javaClasses){

            contracts.addAll(javaClass.getInvariants());

            for(JavaMethod javaMethod : javaClass.getJavaMethods()){
                contracts.addAll(javaMethod.getContracts());
            }
        }

        return contracts;
    }


    @Override
    public String toString() {
        return "JavaFile{" +
                "fileName='" + fileName + '\'' +
                "fullPath='" + fullPath + '\'' +
                "shortPath='" + shortPath + '\'' +
                ", fileType=" + fileType +
                ", javaClasses=" + javaClasses +
                '}';
    }


    // Getters and Setters
    public String getFileName() {
        return fileName;
    }

    public String getCompleteFileName() {

        return fileName + "." + fileType.toString().toLowerCase();
    }

    public String getFullPath() {
        return fullPath;
    }

    public String getShortPath() {
        return shortPath;
    }

    public FileType getFileType() {
        return fileType;
    }

    public JavaFileStatistics getJavaFileStatistics() {
        return javaFileStatistics;
    }

    public List<JavaClass> getJavaClasses() {
        return javaClasses;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public void setShortPath(String shortPath) {
        this.shortPath = shortPath;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public void setJavaFileStatistics(JavaFileStatistics javaFileStatistics) {
        this.javaFileStatistics = javaFileStatistics;
    }

    public void setJavaClasses(List<JavaClass> javaClasses) {
        this.javaClasses = javaClasses;
    }

    public void addJavaClass(JavaClass javaClass) {
        this.javaClasses.add(javaClass);
    }
}

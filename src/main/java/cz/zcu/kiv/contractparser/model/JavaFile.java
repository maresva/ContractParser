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
    String fileName;

    /** Path of original file */
    String path;

    /** Type of the original file (*.class or *.java) */
    FileType fileType;

    /** JavaFileStatistics about this JavaFile */
    JavaFileStatistics javaFileStatistics;


    /** List of classes in the file */
    private List<JavaClass> javaClasses;


    public JavaFile() {
        this.javaClasses = new ArrayList<>();
        this.javaFileStatistics = new JavaFileStatistics();
        this.javaFileStatistics = new JavaFileStatistics();
    }


    public JavaFileCompareReport compareJavaFileTo(JavaFile otherJavaFile, boolean reportEqual,
                                                   boolean reportNonContractChanges){

        JavaFileComparator javaFileComparator = new JavaFileComparator();
        return javaFileComparator.compareJavaFiles(this, otherJavaFile, reportEqual, reportNonContractChanges);
    }


    public List<Contract> getContracts(){

        List<Contract> contracts = new ArrayList<>();

        for(JavaClass javaClass : javaClasses){
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
                "path='" + path + '\'' +
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

    public String getPath() {
        return path;
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

    public void setPath(String path) {
        this.path = path;
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

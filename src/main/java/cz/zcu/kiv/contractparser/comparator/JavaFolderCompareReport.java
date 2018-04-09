package cz.zcu.kiv.contractparser.comparator;

import java.util.ArrayList;
import java.util.List;

/**
 * Instances of this class provide report about comparison of two folders containing Java files. The comparison is in
 * terms of API and contract changes of individual files (classes and methods respectively).
 *
 * @author Vaclav Mares
 */
public class JavaFolderCompareReport {

    /** Path of the first folder (the main one) */
    private String thisFolderPath;

    /** Path of the second folder (the one that is being compared with) */
    private String otherFolderPath;

    /** Whether are the folders equal on terms of API (method signatures and classes) */
    private boolean apiEqual;

    /** Whether are the folders equal on terms of individual contracts */
    private boolean contractEqual;

    /** Statistics containing summary for this comparison */
    private JavaFolderCompareStatistics javaFolderCompareStatistics;

    /** List of relative paths for each added file */
    private List<String> filesAdded;

    /** List of relative paths for each removed file */
    private List<String> filesRemoved;

    /** List with compare reports for each file that found pair in the other folder */
    transient private List<JavaFileCompareReport> javaFileCompareReports;


    JavaFolderCompareReport(String thisFolderPath, String otherFolderPath) {

        this.thisFolderPath = thisFolderPath;
        this.otherFolderPath = otherFolderPath;
        this.apiEqual = true;
        this.contractEqual = true;
        this.filesAdded = new ArrayList<>();
        this.filesRemoved = new ArrayList<>();
        this.javaFileCompareReports = new ArrayList<>();
    }


    @Override
    public String toString() {
        return "JavaFolderCompareReport{" +
                "thisFolderPath='" + thisFolderPath + '\'' +
                ", otherFolderPath='" + otherFolderPath + '\'' +
                ", apiEqual=" + apiEqual +
                ", contractEqual=" + contractEqual +
                ", javaFolderCompareStatistics=" + javaFolderCompareStatistics +
                ", filesAdded=" + filesAdded +
                ", filesRemoved=" + filesRemoved +
                ", javaFileCompareReports=" + javaFileCompareReports +
                '}';
    }
    

    // Getters and setters
    public String getThisFolderPath() {
        return thisFolderPath;
    }

    public String getOtherFolderPath() {
        return otherFolderPath;
    }

    public boolean isApiEqual() {
        return apiEqual;
    }

    public boolean isContractEqual() {
        return contractEqual;
    }

    public List<String> getFilesAdded() {
        return filesAdded;
    }

    public List<String> getFilesRemoved() {
        return filesRemoved;
    }

    public List<JavaFileCompareReport> getJavaFileCompareReports() {
        return javaFileCompareReports;
    }

    public void setThisFolderPath(String thisFolderPath) {
        this.thisFolderPath = thisFolderPath;
    }

    public void setOtherFolderPath(String otherFolderPath) {
        this.otherFolderPath = otherFolderPath;
    }

    public void setApiEqual(boolean apiEqual) {
        this.apiEqual = apiEqual;
    }

    public void setContractEqual(boolean contractEqual) {
        this.contractEqual = contractEqual;
    }

    public void setFilesAdded(List<String> filesAdded) {
        this.filesAdded = filesAdded;
    }

    public void setFilesRemoved(List<String> filesRemoved) {
        this.filesRemoved = filesRemoved;
    }

    public void setJavaFileCompareReports(List<JavaFileCompareReport> javaFileCompareReports) {
        this.javaFileCompareReports = javaFileCompareReports;
    }

    public void addFileAdded(String fileAdded) {
        this.filesAdded.add(fileAdded);
        this.apiEqual = false;
    }

    public void addFileRemoved(String fileRemoved) {
        this.filesRemoved.add(fileRemoved);
        this.apiEqual = false;
    }

    public void addJavaFileCompareReport(JavaFileCompareReport javaFileCompareReport) {
        this.javaFileCompareReports.add(javaFileCompareReport);

        if(!javaFileCompareReport.isContractEqual()){
            this.contractEqual = false;
        }
    }

    public JavaFolderCompareStatistics getJavaFolderCompareStatistics() {
        return javaFolderCompareStatistics;
    }

    public void setJavaFolderCompareStatistics(JavaFolderCompareStatistics javaFolderCompareStatistics) {
        this.javaFolderCompareStatistics = javaFolderCompareStatistics;
    }
}

package cz.zcu.kiv.contractparser.api;

import cz.zcu.kiv.contractparser.comparator.JavaFolderComparator;
import cz.zcu.kiv.contractparser.comparator.comparatormodel.JavaFolderCompareReport;

import java.io.File;

/**
 * Provides methods for comparing contracts of Java files.
 *
 * @author Vaclav Mares
 */
public class DefaultContractComparatorApi implements ContractComparatorApi {

    /** Instance of JavaFolderComparator which is used to call compare methods */
    private JavaFolderComparator javaFolderComparator;


    public DefaultContractComparatorApi() {
        javaFolderComparator = new JavaFolderComparator();
    }


    /**
     * This method compares two folders containing Java files. It compares individual files and determines what changes
     * there were made on API and contracts (based on input parameters). Output is a report object which contains
     * information such as what files were added/removed, individual file compare reports and summary statistics.
     *
     * @param thisFolder                File - first folder with Java files
     * @param otherFolder               File - second folder with Java files
     * @param reportEqual               boolean - whether should equal files be reported or not
     * @param reportNonContractChanges  boolean - whether should be reported changes that doesn't affect contracts
     * @return                          JavaFolderCompareReport - containing all relevant compare information
     */
    public JavaFolderCompareReport compareJavaFolders(File thisFolder, File otherFolder, boolean reportEqual,
                                                      boolean reportNonContractChanges) {
        
        return javaFolderComparator.compareJavaFolders(thisFolder, otherFolder, reportEqual,
                reportNonContractChanges, false, null, false);
    }


    /**
     * Exports given JavaCompareFolder to JSON. Each JavaFileCompareReport is saved in separated file and there is
     * also crete one file for global information.
     *
     * @param javaFolderCompareReport  JavaFolderCompareReport to be exported
     * @param outputFolder             File representing folder where should be export saved
     * @param prettyPrint              Whether JSON should be in human readable form or not
     * @return                         Number of exported files
     */
    public int exportJavaFolderCompareReportToJson(JavaFolderCompareReport javaFolderCompareReport, File outputFolder,
                                                   boolean prettyPrint){

       return javaFolderComparator.exportJavaCompareFolderToJson(javaFolderCompareReport, outputFolder, prettyPrint);
    }
}

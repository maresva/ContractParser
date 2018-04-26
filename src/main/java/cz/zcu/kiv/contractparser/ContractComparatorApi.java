package cz.zcu.kiv.contractparser;

import cz.zcu.kiv.contractparser.comparator.JavaFolderComparator;
import cz.zcu.kiv.contractparser.comparator.JavaFolderCompareReport;

import java.io.File;

/**
 * Provides APi to be called outside this library. It gives at disposal methods necessary to compare folders containing
 * java source files. It also provides information about API changes in the files.
 *
 * @author Vaclav Mares
 */
public final class ContractComparatorApi {

    /** Private constructor to prevent its use */
    private ContractComparatorApi() {
    }

    /** Instance of JavaFolderComparator which is used to call compare methods */
    private static final JavaFolderComparator javaFolderComparator = new JavaFolderComparator();

    /**
     * This method compares two folders containing Java files. It compares individual files and determines what changes
     * there were made on API and contracts (based on input parameters). Output is a report object which contains
     * information such as what files were added/removed, individual file compare reports and summary statistics.
     * Compared data can be exported to JSON right away to avoid memory usage spikes.
     *
     * @param thisFolder                File - first folder with Java files
     * @param otherFolder               File - second folder with Java files
     * @param reportEqual               boolean - whether should equal files be reported or not
     * @param reportNonContractChanges  boolean - whether should be reported changes that doesn't affect contracts
     * @param jsonOutputFolder          File - output folder for JSON files
     * @param prettyPrint               boolean - if JSON should be in human readable form (otherwise JSON min)
     * @return                          JavaFolderCompareReport - containing all relevant compare information
     */
    public static JavaFolderCompareReport compareJavaFoldersAndExportToJson(File thisFolder, File otherFolder, boolean reportEqual,
            boolean reportNonContractChanges, File jsonOutputFolder, boolean prettyPrint) {

        return javaFolderComparator.compareJavaFolders(thisFolder, otherFolder, reportEqual,
                reportNonContractChanges, true, jsonOutputFolder, prettyPrint);
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
    public static JavaFolderCompareReport compareJavaFolders(File thisFolder, File otherFolder,
                                                             boolean reportEqual, boolean reportNonContractChanges) {
        
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
    public static int exportJavaFolderCompareReportToJson(JavaFolderCompareReport javaFolderCompareReport,
                                                           File outputFolder, boolean prettyPrint){

       return javaFolderComparator.exportJavaCompareFolderToJson(javaFolderCompareReport, outputFolder, prettyPrint);
    }
}

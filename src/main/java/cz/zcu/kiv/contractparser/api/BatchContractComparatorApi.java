package cz.zcu.kiv.contractparser.api;

import cz.zcu.kiv.contractparser.comparator.comparatormodel.JavaFolderCompareReport;

import java.io.File;

/**
 * Provides methods used to compare JavaFiles in batch similar to ContractComparatorApi. Methods are focused on batch
 * operations meaning data are not stored in memory but are exported right away.
 *
 * @author Vaclav Mares
 */
public interface BatchContractComparatorApi {

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
    JavaFolderCompareReport compareJavaFoldersAndExportToJson(File thisFolder, File otherFolder, boolean reportEqual,
                                                              boolean reportNonContractChanges, File jsonOutputFolder,
                                                              boolean prettyPrint);
}

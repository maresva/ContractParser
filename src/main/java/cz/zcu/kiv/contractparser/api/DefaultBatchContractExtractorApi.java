package cz.zcu.kiv.contractparser.api;

import cz.zcu.kiv.contractparser.model.ContractType;
import cz.zcu.kiv.contractparser.parser.ContractExtractor;

import java.io.File;
import java.util.HashMap;


/**
 * Provides methods used to extract contracts from Java files in batch similar to DefaultContractExtractorApi. Methods are
 * focused on batch operations meaning data are not stored in memory but are exported right away.
 *
 * @author Vaclav Mares
 */
public class DefaultBatchContractExtractorApi implements BatchContractExtractorApi {

    /** Instance of ContractExtractor which is used to call extraction methods */
    private ContractExtractor contractExtractor;


    public DefaultBatchContractExtractorApi() {
        contractExtractor = new ContractExtractor();
    }


    /**
     * This method extracts Design by contract constructions from every *.java and *.class file from give folder.
     * Extracted JavaFiles are then converted to JSON and saved to given location. This method should be used
     * if there is no future work with JavaFiles besides export as it is less memory demanding.
     * 
     * @param inputFolder               Input folder with java source files (*.class or *.java)
     * @param outputFolder              Output folder for generated JSON files
     * @param prettyPrint               Whether JSON should be in human readable form or not
     * @param removeNonContractObjects  Whether should objects that don't contain contracts be returned or not
     * @param contractTypes             Specifies which contract types should be extracted
     * @return                          Number of successfully exported files
     */
    public int retrieveContractsFromFolderExportToJson(File inputFolder, File outputFolder, boolean prettyPrint,
                                                       boolean removeNonContractObjects,
                                                       HashMap<ContractType, Boolean> contractTypes) {

        return contractExtractor.retrieveContractsFromFolderExportToJson(inputFolder, outputFolder, prettyPrint,
                removeNonContractObjects, contractTypes);
    }


    /**
     * This method extracts Design by contract constructions from every *.java and *.class file from give folder.
     * Extracted JavaFiles are then converted to JSON and saved to given location. This method should be used
     * if there is no future work with JavaFiles besides export as it is less memory demanding.
     *
     * @param inputFolder               Input folder with java source files (*.class or *.java)
     * @param outputFolder              Output folder for generated JSON files
     * @param prettyPrint               Whether JSON should be in human readable form or not
     * @param removeNonContractObjects  Whether should objects that don't contain contracts be returned or not
     * @return                          Number of successfully exported files
     */
    public int retrieveContractsFromFolderExportToJson(File inputFolder, File outputFolder, boolean prettyPrint,
                                                       boolean removeNonContractObjects) {

        return retrieveContractsFromFolderExportToJson(inputFolder, outputFolder, prettyPrint, removeNonContractObjects,
                null);
    }
}

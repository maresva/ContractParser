package cz.zcu.kiv.contractparser;

import cz.zcu.kiv.contractparser.model.ContractType;
import cz.zcu.kiv.contractparser.model.JavaFile;
import cz.zcu.kiv.contractparser.parser.ContractExtractor;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * This class provides API for ContractParser. Its methods are meant to be called from outside this library
 * and all of them provide way to retrieve contracts from file or folder.
 *
 * @author Vaclav Mares
 */
public final class ContractExtractorApi {

    /** Private constructor to prevent its use */
    private ContractExtractorApi() {
    }

    /** Instance of ContractExtractor which is used to call extraction methods */
    private static final ContractExtractor contractExtractor = new ContractExtractor();

    /**
     * This method extracts Design by contract constructions from given file.
     *
     * @param file                      Input file with java source file (*.class or *.java)
     * @param removeNonContractObjects  Whether should objects that don't contain contracts be returned or not
     * @param contractTypes             Specifies which contract types should be extracted
     * @return                          JavaFile containing structure of the file with contracts
     */
    public static JavaFile retrieveContracts(File file, boolean removeNonContractObjects,
                                             HashMap<ContractType, Boolean> contractTypes) {

        return contractExtractor.retrieveContracts(file, removeNonContractObjects, contractTypes);
    }


    /**
     * This method extracts Design by contract constructions from given file.
     *
     * @param file                      Input file with java source file (*.class or *.java)
     * @param removeNonContractObjects  Whether should objects that don't contain contracts be returned or not
     * @return                          JavaFile containing structure of the file with contracts
     */
    public static JavaFile retrieveContracts(File file, boolean removeNonContractObjects){

        return retrieveContracts(file, removeNonContractObjects, null);
    }


    /**
     * This method extracts Design by contract constructions from every *.java and *.class file from give folder.
     *
     * @param folder                    Input folder with java source files (*.class or *.java)
     * @param removeNonContractObjects  Whether should objects that don't contain contracts be returned or not
     * @param contractTypes             Specifies which contract types should be extracted
     * @return                          List of JavaFiles containing structure of the file with contracts
     */
    public static List<JavaFile> retrieveContractsFromFolder(File folder, boolean removeNonContractObjects,
                                                             HashMap<ContractType, Boolean> contractTypes) {

        return contractExtractor.retrieveContractsFromFolder(folder, removeNonContractObjects, contractTypes);
    }


    /**
     * This method extracts Design by contract constructions from every *.java and *.class file from give folder.
     *
     * @param folder                    Input folder with java source files (*.class or *.java)
     * @param removeNonContractObjects  Whether should objects that don't contain contracts be returned or not
     * @return                          List of JavaFiles containing structure of the file with contracts
     */
    public static List<JavaFile> retrieveContractsFromFolder(File folder, boolean removeNonContractObjects) {

        return retrieveContractsFromFolder(folder, removeNonContractObjects, null);
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
     */
    public static void retrieveContractsFromFolderExportToJson(File inputFolder, File outputFolder, boolean prettyPrint,
            boolean removeNonContractObjects, HashMap<ContractType, Boolean> contractTypes) {

        contractExtractor.retrieveContractsFromFolderExportToJson(inputFolder, outputFolder, prettyPrint,
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
     */
    public static void retrieveContractsFromFolderExportToJson(File inputFolder, File outputFolder, boolean prettyPrint,
                                                               boolean removeNonContractObjects) {

        retrieveContractsFromFolderExportToJson(inputFolder, outputFolder, prettyPrint, removeNonContractObjects,
                null);
    }

    
    /**
     * Export given list of JavaFiles to JSON.
     *
     * @param javaFiles     Input list of JavaFiles
     * @param outputFolder  Output folder for generated JSON files
     * @param prettyPrint   Whether JSON should be in human readable form or not
     */
    public static void exportJavaFilesToJson(List<JavaFile> javaFiles, File outputFolder, boolean prettyPrint) {

        contractExtractor.exportJavaFilesToJson(javaFiles, outputFolder, prettyPrint);
    }


    /**
     * Updates short path of given list of JavaFiles to remove prefix that all files has in common.
     *
     * @param javaFiles     Input list of JavaFiles
     */
    public static void updateShortPathOfJavaFiles(List<JavaFile> javaFiles){

        contractExtractor.updateShortPathOfJavaFiles(javaFiles);
    }
}
package cz.zcu.kiv.contractparser.api;

import cz.zcu.kiv.contractparser.model.ContractType;
import cz.zcu.kiv.contractparser.model.JavaFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Provides methods for extracting contracts from Java files.
 *
 * @author Vaclav Mares
 */
public interface ContractExtractorApi {

    /**
     * This method extracts Design by contract constructions from given file.
     *
     * @param file                      Input file with java source file (*.class or *.java)
     * @param removeNonContractObjects  Whether should objects that don't contain contracts be returned or not
     * @param contractTypes             Specifies which contract types should be extracted
     * @return                          JavaFile containing structure of the file with contracts
     */
    JavaFile retrieveContracts(File file, boolean removeNonContractObjects, HashMap<ContractType, Boolean> contractTypes);


    /**
     * This method extracts Design by contract constructions from given file.
     *
     * @param file                      Input file with java source file (*.class or *.java)
     * @param removeNonContractObjects  Whether should objects that don't contain contracts be returned or not
     * @return                          JavaFile containing structure of the file with contracts
     */
    JavaFile retrieveContracts(File file, boolean removeNonContractObjects);


    /**
     * This method extracts Design by contract constructions from every *.java and *.class file from give folder.
     *
     * @param folder                    Input folder with java source files (*.class or *.java)
     * @param removeNonContractObjects  Whether should objects that don't contain contracts be returned or not
     * @param contractTypes             Specifies which contract types should be extracted
     * @return                          List of JavaFiles containing structure of the file with contracts
     */
    List<JavaFile> retrieveContractsFromFolder(File folder, boolean removeNonContractObjects,
                                               HashMap<ContractType, Boolean> contractTypes);


    /**
     * This method extracts Design by contract constructions from every *.java and *.class file from give folder.
     *
     * @param folder                    Input folder with java source files (*.class or *.java)
     * @param removeNonContractObjects  Whether should objects that don't contain contracts be returned or not
     * @return                          List of JavaFiles containing structure of the file with contracts
     */
    List<JavaFile> retrieveContractsFromFolder(File folder, boolean removeNonContractObjects);


    /**
     * Export given list of JavaFiles to JSON.
     *
     * @param javaFiles     Input list of JavaFiles
     * @param outputFolder  Output folder for generated JSON files
     * @param prettyPrint   Whether JSON should be in human readable form or not      *
     * @return              Number of successfully exported files
     */
    int exportJavaFilesToJson(List<JavaFile> javaFiles, File outputFolder, boolean prettyPrint);


    /**
     * Updates short path of given list of JavaFiles to remove prefix that all files has in common.
     *
     * @param javaFiles     Input list of JavaFiles
     * @return              Number of updated paths
     */
    int updateShortPathOfJavaFiles(List<JavaFile> javaFiles);
}

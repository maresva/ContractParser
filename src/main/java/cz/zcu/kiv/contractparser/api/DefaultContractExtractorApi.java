package cz.zcu.kiv.contractparser.api;

import cz.zcu.kiv.contractparser.model.ContractType;
import cz.zcu.kiv.contractparser.model.JavaFile;
import cz.zcu.kiv.contractparser.parser.ContractExtractor;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Provides methods for extracting contracts from Java files.
 *
 * @author Vaclav Mares
 */
public class DefaultContractExtractorApi implements ContractExtractorApi{

    /** Instance of ContractExtractor which is used to call extraction methods */
    private ContractExtractor contractExtractor;

    
    public DefaultContractExtractorApi() {
        contractExtractor = new ContractExtractor();
    }


    /**
     * This method extracts Design by contract constructions from given file.
     *
     * @param file                      Input file with java source file (*.class or *.java)
     * @param removeNonContractObjects  Whether should objects that don't contain contracts be returned or not
     * @param contractTypes             Specifies which contract types should be extracted
     * @return                          JavaFile containing structure of the file with contracts
     */
    public JavaFile retrieveContracts(File file, boolean removeNonContractObjects,
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
    public JavaFile retrieveContracts(File file, boolean removeNonContractObjects){

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
    public List<JavaFile> retrieveContractsFromFolder(File folder, boolean removeNonContractObjects,
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
    public List<JavaFile> retrieveContractsFromFolder(File folder, boolean removeNonContractObjects) {

        return retrieveContractsFromFolder(folder, removeNonContractObjects, null);
    }


    /**
     * Export given list of JavaFiles to JSON.
     *
     * @param javaFiles     Input list of JavaFiles
     * @param outputFolder  Output folder for generated JSON files
     * @param prettyPrint   Whether JSON should be in human readable form or not      *
     * @return              Number of successfully exported files
     */
    public int exportJavaFilesToJson(List<JavaFile> javaFiles, File outputFolder, boolean prettyPrint) {

        return contractExtractor.exportJavaFilesToJson(javaFiles, outputFolder, prettyPrint);
    }


    /**
     * Updates short path of given list of JavaFiles to remove prefix that all files has in common.
     *
     * @param javaFiles     Input list of JavaFiles
     */
    public int updateShortPathOfJavaFiles(List<JavaFile> javaFiles){

        return contractExtractor.updateShortPathOfJavaFiles(javaFiles);
    }
}
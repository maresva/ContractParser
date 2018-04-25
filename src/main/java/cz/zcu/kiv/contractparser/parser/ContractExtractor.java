package cz.zcu.kiv.contractparser.parser;

import com.github.javaparser.ParseProblemException;
import cz.zcu.kiv.contractparser.ContractExtractorApi;
import cz.zcu.kiv.contractparser.utils.ResourceHandler;
import cz.zcu.kiv.contractparser.utils.IOServices;
import cz.zcu.kiv.contractparser.model.ContractType;
import cz.zcu.kiv.contractparser.model.ExtendedJavaFile;
import cz.zcu.kiv.contractparser.model.JavaFile;
import cz.zcu.kiv.contractparser.model.JavaFileStatistics;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class provides methods necessary to extract contracts from given file or folder.
 *
 * @author Vaclav Mares
 * */
public class ContractExtractor {

    /** Log4j logger for this class */
    private final static Logger logger = Logger.getLogger(String.valueOf(ContractExtractorApi.class));


    /**
     * This method extracts Design by contract constructions from given file.
     *
     * @param file                      Input file with java source file (*.class or *.java)
     * @param removeNonContractObjects  Whether should objects that don't contain contracts be returned or not
     * @param contractTypes             Specifies which contract types should be extracted
     * @return                          JavaFile containing structure of the file with contracts
     */
    public JavaFile retrieveContracts(File file, boolean removeNonContractObjects,
                                      HashMap<ContractType, Boolean> contractTypes){

        ParserFactory parserFactory = new ParserFactory();
        ContractParser contractParser;

        JavaFile javaFile;
        ExtendedJavaFile extendedJavaFile;

        // parse raw Java file to get structured data
        try {
            extendedJavaFile = JavaFileParser.parseFile(file);

            if(extendedJavaFile == null){
                return null;
            }

            // If Guava is selected - search for Guava Design by Contracts
            if (contractTypes == null || contractTypes.get(ContractType.GUAVA)) {
                contractParser = parserFactory.getParser(ContractType.GUAVA);
                extendedJavaFile = contractParser.retrieveContracts(extendedJavaFile);
            }

            // If JSR305 is selected - search for JSR305 Design by Contracts
            if (contractTypes == null || contractTypes.get(ContractType.JSR305)) {
                contractParser = parserFactory.getParser(ContractType.JSR305);
                extendedJavaFile = contractParser.retrieveContracts(extendedJavaFile);
            }
        }
        catch(ParseProblemException e){
            logger.warn(ResourceHandler.getMessage("errorJavaParserNotParsed", file.toString()));
            logger.warn(e.getMessage());
            return null;
        }

        // simplify ExtendedJavaFile to JavaFile for export and display purposes
        javaFile = Simplifier.simplifyExtendedJavaFile(extendedJavaFile);

        // if checked remove all classes/methods that do not have any contracts or even return null
        if(removeNonContractObjects) {
            javaFile = Simplifier.removeNonContractObjects(javaFile);
        }

        if(javaFile != null) {
            logger.info(ResourceHandler.getMessage("infoFilePared", javaFile.getFullPath(),
                    javaFile.getJavaFileStatistics().getTotalNumberOfContracts()));
        }

        return javaFile;
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

        List<JavaFile> javaFiles = new ArrayList<>();
        List<File> files = IOServices.getFilesFromFolder(folder, null);

        for (final File fileEntry : files) {
            if(fileEntry != null) {

                JavaFile javaFile = retrieveContracts(fileEntry, removeNonContractObjects, contractTypes);

                if(javaFile != null) {
                    javaFiles.add(javaFile);
                }
            }
        }

        // reduce path of files by reducing their common part
        updateShortPathOfJavaFiles(javaFiles);

        return javaFiles;
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
    public void retrieveContractsFromFolderExportToJson(File inputFolder, File outputFolder, boolean prettyPrint,
                                boolean removeNonContractObjects, HashMap<ContractType, Boolean> contractTypes) {

        JavaFileStatistics globalJavaFileStatistics = new JavaFileStatistics();

        List<File> files = IOServices.getFilesFromFolder(inputFolder, null);

        boolean success = false;
        for (final File fileEntry : files) {
            if(fileEntry != null) {

                JavaFile javaFile = retrieveContracts(fileEntry, removeNonContractObjects, contractTypes);

                // if JavaFile was retrieved - merge its statistics with the global ones and export file to JSON
                if(javaFile != null) {
                    globalJavaFileStatistics.mergeStatistics(javaFile.getJavaFileStatistics());
                    String fileName = IOServices.escapeFilePath(javaFile.getShortPath());
                    if(IOServices.saveObjectAsJson(javaFile, fileName, outputFolder, prettyPrint)){
                        success = true;
                    }
                }
            }
        }

        // global statistics are saved only if at least one file was successfully saved
        if(success) {
            String statisticsFileName = ResourceHandler.getProperties().getString("globalStatisticsFile");
            IOServices.saveObjectAsJson(globalJavaFileStatistics, statisticsFileName, outputFolder, prettyPrint);
        }
    }


    /**
     * Export given list of JavaFiles to JSON.
     *
     * @param javaFiles     Input list of JavaFiles
     * @param outputFolder  Output folder for generated JSON files
     * @param prettyPrint   Whether JSON should be in human readable form or not
     */
    public void exportJavaFilesToJson(List<JavaFile> javaFiles, File outputFolder, boolean prettyPrint) {

        JavaFileStatistics globalJavaFileStatistics = new JavaFileStatistics();

        boolean success = false;
        for(JavaFile javaFile : javaFiles){

            globalJavaFileStatistics.mergeStatistics(javaFile.getJavaFileStatistics());

            String fileName = IOServices.escapeFilePath(javaFile.getShortPath());
            if(IOServices.saveObjectAsJson(javaFile, fileName, outputFolder, prettyPrint)){
                success = true;
            }
        }

        // global statistics are saved only if at least one file was successfully saved
        if(success) {
            String statisticsFileName = ResourceHandler.getProperties().getString("globalStatisticsFile");
            IOServices.saveObjectAsJson(globalJavaFileStatistics, statisticsFileName, outputFolder, prettyPrint);
        }
    }


    /**
     * Updates short path of given list of JavaFiles to remove prefix that all files has in common.
     *
     * @param javaFiles     Input list of JavaFiles
     */
    public void updateShortPathOfJavaFiles(List<JavaFile> javaFiles){

        // reduce path of files by reducing their common part
        if(javaFiles.size() > 0){

            String longestPathPrefix = javaFiles.get(0).getFullPath();

            // get longest common prefix of all files
            for(int i = 1 ; i < javaFiles.size() ; i++){
                longestPathPrefix = longestCommonPrefix(javaFiles.get(i).getFullPath(), longestPathPrefix);
            }

            // substring its length of every file's path
            int prefixLength = longestPathPrefix.length();

            for(JavaFile javaFile : javaFiles){
                javaFile.setShortPath(javaFile.getFullPath().substring(prefixLength));
            }
        }
    }


    /**
     * Searches for the longest common prefix of two Strings. It is used when removing unnecessary prefix from
     * Java file paths.
     *
     * @param stringX   first String for prefix search
     * @param stringY   second String for prefix search
     * @return          the longest common prefix of both Strings
     */
    private String longestCommonPrefix(String stringX, String stringY) {

        int minLength = Math.min(stringX.length(), stringY.length());

        for (int i = 0; i < minLength; i++) {

            if (stringX.charAt(i) != stringY.charAt(i)) {

                return stringX.substring(0, i);
            }
        }

        return stringX.substring(0, minLength);
    }

}

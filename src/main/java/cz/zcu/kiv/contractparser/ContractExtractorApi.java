package cz.zcu.kiv.contractparser;

import com.github.javaparser.ParseProblemException;
import cz.zcu.kiv.contractparser.comparator.JavaFolderComparator;
import cz.zcu.kiv.contractparser.comparator.JavaFolderCompareReport;
import cz.zcu.kiv.contractparser.io.IOServices;
import cz.zcu.kiv.contractparser.model.ExtendedJavaFile;
import cz.zcu.kiv.contractparser.model.ContractType;
import cz.zcu.kiv.contractparser.model.JavaFile;
import cz.zcu.kiv.contractparser.parser.ContractParser;
import cz.zcu.kiv.contractparser.parser.ParserFactory;
import cz.zcu.kiv.contractparser.parser.JavaFileParser;
import cz.zcu.kiv.contractparser.parser.Simplifier;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class provides API for ContractParser. Its methods are meant to be called from outside this library
 * and all of them provide way to retrieve contract from file(s).
 *
 * @author Vaclav Mares
 */
public class ContractExtractorApi {

    final static Logger logger = Logger.getLogger(String.valueOf(ContractExtractorApi.class));

    /**
     * This method extracts Design by contract constructions from given file
     *
     * @param file  Input file with java source file (*.class or *.java)
     * @return  ContractFile containing structure of the file with contracts
     */
    public static JavaFile retrieveContracts(File file, HashMap<ContractType, Boolean> contractTypes,
                                             boolean removeNonContractObjects) {

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

            for(int i = 0 ; i < 1 ; i++) {
                // If Guava is selected - search for Guava Design by Contracts
                if (contractTypes == null || contractTypes.get(ContractType.GUAVA)) {
                    contractParser = parserFactory.getParser(ContractType.GUAVA);
                    extendedJavaFile = contractParser.retrieveContracts(extendedJavaFile);
                }
            }

            // If JSR305 is selected - search for JSR305 Design by Contracts
            if (contractTypes == null || contractTypes.get(ContractType.JSR305)) {
                contractParser = parserFactory.getParser(ContractType.JSR305);
                extendedJavaFile = contractParser.retrieveContracts(extendedJavaFile);
            }

            //logger.debug("Java file parsed: " + javaFile.getPath() + " (Contracts found: " +
            // javaFile.getJavaFileStatistics().getTotalNumberOfContracts() + ")");
        }
        catch(ParseProblemException e){
            logger.error("Could not parse file " + file.toString());
            logger.error(e.getMessage());
            return null;
        }

        // simplify ExtendedJavaFile to JavaFile for export and display purposes
        javaFile = Simplifier.simplifyExtendedJavaFile(extendedJavaFile, removeNonContractObjects);

        // if checked remove all classes/methods that do not have any contracts or even return null
        if(removeNonContractObjects) {
            javaFile = Simplifier.removeNonContractObjects(javaFile);
        }
        
        return javaFile;
    }


    public static JavaFile retrieveContracts(File file, boolean removeNonContractObjects){

        return retrieveContracts(file, null, removeNonContractObjects);
    }


    /**
     * This method extracts Design by contract constructions from file with input file name
     *
     * @param fileName  Input fileName of file with java source file (*.class or *.java)
     * @return  ExtendedJavaFile containing structure of the file with contracts
     */
    public static JavaFile retrieveContracts(String fileName, HashMap<ContractType, Boolean> contractType,
                                             boolean removeNonContractObjects) {

        File file = IOServices.getFile(fileName);
        return retrieveContracts(file, contractType, removeNonContractObjects);
    }

    public static JavaFile retrieveContracts(String fileName, boolean removeNonContractObjects) {

        return retrieveContracts(fileName, null, removeNonContractObjects);
    }


    /**
     * This method extracts Design by contract constructions from every *.java and *.class file from give folder
     *
     * @param folder  Input file with java source file (*.class or *.java)
     * @return  List of JavaFiles containing structure of the file with contracts
     */
    public static List<JavaFile> retrieveContractsFromFolder(File folder, HashMap<ContractType, Boolean> contractType,
                                                             boolean removeNonContractObjects) {

        List<JavaFile> contracts = new ArrayList<>();
        List<File> files = IOServices.getFilesFromFolder(folder, null);

        for (final File fileEntry : files) {
            if(fileEntry != null) {

                JavaFile javaFile = retrieveContracts(fileEntry, contractType, removeNonContractObjects);

                if(javaFile != null) {
                    contracts.add(javaFile);
                }
            }
        }

        return contracts;
    }

    public static List<JavaFile> retrieveContractsFromFolder(File folder, boolean removeNonContractObjects) {

        return retrieveContractsFromFolder(folder, null, removeNonContractObjects);
    }


    /**
     * This method extracts Design by contract constructions from every *.java and *.class file from folder with
     * given fileName
     *
     * @param folderName  Input file with java source file (*.class or *.java)
     * @return  List of JavaFiles containing structure of the file with contracts
     */
    public static List<JavaFile> retrieveContractsFromFolder(String folderName, HashMap<ContractType,
            Boolean> contractType, boolean removeNonContractObjects) {

        File file = IOServices.getFile(folderName);
        return retrieveContractsFromFolder(file, contractType, removeNonContractObjects);
    }

    
    public static List<JavaFile> retrieveContractsFromFolder(String folderName, boolean removeNonContractObjects) {

        File file = IOServices.getFile(folderName);
        return retrieveContractsFromFolder(file, null, removeNonContractObjects);
    }
}

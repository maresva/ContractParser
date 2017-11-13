package cz.zcu.kiv.contractparser;

import cz.zcu.kiv.contractparser.io.IOServices;
import cz.zcu.kiv.contractparser.model.JavaFile;
import cz.zcu.kiv.contractparser.model.ContractType;
import cz.zcu.kiv.contractparser.parser.GuavaParser;
import cz.zcu.kiv.contractparser.parser.JSR305Parser;
import cz.zcu.kiv.contractparser.parser.JavaFileParser;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class provides API for ContractParser. Its methods are meant to be called from outside this library
 * and all of them provide way to retrieve contract from file(s).
 *
 * @Author Václav Mareš
 */
public class Api {

    final static Logger logger = Logger.getLogger(String.valueOf(Api.class));

    /**
     * This method extracts Design by contract constructions from given file
     *
     * @param file  Input file with java source file (*.class or *.java)
     * @return  ContractFile containing structure of the file with contracts
     */
    public static JavaFile retrieveContracts(File file, HashMap<ContractType, Boolean> contractTypes) {

        // parse raw Java file to get structured data
        JavaFile javaFile = JavaFileParser.parseFile(file);

        if(javaFile == null){
            return null;
        }

        // If Guava is selected - search for Guava Design by Contracts
        if(contractTypes.get(ContractType.GUAVA)){
            logger.info("Retrieving Guava contracts...");
            GuavaParser guavaParser= new GuavaParser();
            javaFile = guavaParser.retrieveContracts(javaFile);
        }

        // If JSR305 is selected - search for JSR305 Design by Contracts
        if(contractTypes.get(ContractType.JSR305)){
            logger.info("Retrieving JSR305 contracts...");
            JSR305Parser jsr305Parser = new JSR305Parser();
            javaFile = jsr305Parser.retrieveContracts(javaFile);
        }

        return javaFile;
    }

    /**
     * This method extracts Design by contract constructions from every *.java and *.class files of given list
     *
     * @param files  List of input files with java source file (*.class or *.java)
     * @return  List of JavaFiles containing structure of the file with contracts
     */
    public static List<JavaFile> retrieveContracts(List<File> files, HashMap<ContractType, Boolean> contractType) {

        List<JavaFile> contracts = new ArrayList<>();

        for(File file : files) {
            if(file != null) {
                contracts.add(retrieveContracts(file, contractType));
            }
        }

        return contracts;
    }


    /**
     * This method extracts Design by contract constructions from file with input file name
     *
     * @param fileName  Input fileName of file with java source file (*.class or *.java)
     * @return  JavaFile containing structure of the file with contracts
     */
    public static JavaFile retrieveContracts(String fileName, HashMap<ContractType, Boolean> contractType) {

        File file = IOServices.getFile(fileName);
        return retrieveContracts(file, contractType);
    }


    /**
     * This method extracts Design by contract constructions from every *.java and *.class file from give folder
     *
     * @param folder  Input file with java source file (*.class or *.java)
     * @return  List of JavaFiles containing structure of the file with contracts
     */
    public static List<JavaFile> retrieveContractsFromFolder(File folder, HashMap<ContractType, Boolean> contractType) {

        List<JavaFile> contracts = new ArrayList<>();
        List<File> files = IOServices.getFilesFromFolder(folder, null);

        for (final File fileEntry : files) {
            if(fileEntry != null) {
                contracts.add(retrieveContracts(fileEntry, contractType));
            }
        }

        return contracts;
    }


    /**
     * This method extracts Design by contract constructions from every *.java and *.class file from folder with
     * given fileName
     *
     * @param folderName  Input file with java source file (*.class or *.java)
     * @return  List of JavaFiles containing structure of the file with contracts
     */
    public static List<JavaFile> retrieveContractsFromFolder(String folderName, HashMap<ContractType, Boolean> contractType) {

        File file = IOServices.getFile(folderName);
        return retrieveContractsFromFolder(file, contractType);
    }
}

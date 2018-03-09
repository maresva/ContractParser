package cz.zcu.kiv.contractparser;

import com.github.javaparser.ParseProblemException;
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
public class ContractManagerApi {

    final static Logger logger = Logger.getLogger(String.valueOf(ContractManagerApi.class));

    /**
     * This method extracts Design by contract constructions from given file
     *
     * @param file  Input file with java source file (*.class or *.java)
     * @return  ContractFile containing structure of the file with contracts
     */
    public static JavaFile retrieveContracts(File file, HashMap<ContractType, Boolean> contractTypes) {

        ParserFactory parserFactory = new ParserFactory();
        ContractParser contractParser;


        JavaFile javaFile = null;

        // parse raw Java file to get structured data

        try {
            ExtendedJavaFile extendedJavaFile = JavaFileParser.parseFile(file);

            if(extendedJavaFile == null){
                return null;
            }

            // If Guava is selected - search for Guava Design by Contracts
            if(contractTypes.get(ContractType.GUAVA)){
                //logger.info("Retrieving Guava contracts...");
                contractParser = parserFactory.getParser(ContractType.GUAVA);
                extendedJavaFile = contractParser.retrieveContracts(extendedJavaFile);
            }

            // If JSR305 is selected - search for JSR305 Design by Contracts
            if(contractTypes.get(ContractType.JSR305)){
                //logger.info("Retrieving JSR305 contracts...");
                contractParser = parserFactory.getParser(ContractType.JSR305);
                extendedJavaFile = contractParser.retrieveContracts(extendedJavaFile);
            }

            javaFile = Simplifier.simplifyExtendedJavaFile(extendedJavaFile);

            logger.info("Contracts found: " + javaFile.getTotalNumberOfContracts());
        }
        catch(ParseProblemException e){
            logger.error("Could not parse file " + file.toString());
        }
        
        return javaFile;
    }

    /**
     * This method extracts Design by contract constructions from every *.java and *.class files of given list
     *
     * @param files  List of input files with java source file (*.class or *.java)
     * @return  List of JavaFiles containing structure of the file with contracts
     *//*
    public static List<JavaFile> retrieveContracts(List<File> files, HashMap<ContractType, Boolean> contractType) {

        List<JavaFile> contracts = new ArrayList<>();

        for(File file : files) {
            if(file != null) {

                JavaFile javaFile = retrieveContracts(file, contractType);

                if(javaFile != null) {
                    contracts.add(javaFile);
                }
            }
        }

        return contracts;
    }*/


    /**
     * This method extracts Design by contract constructions from file with input file name
     *
     * @param fileName  Input fileName of file with java source file (*.class or *.java)
     * @return  ExtendedJavaFile containing structure of the file with contracts
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

                JavaFile javaFile = retrieveContracts(fileEntry, contractType);

                if(javaFile != null) {
                    contracts.add(javaFile);
                }
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

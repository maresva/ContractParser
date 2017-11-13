package cz.zcu.kiv.contractparser.parser;

import cz.zcu.kiv.contractparser.model.JavaFile;
import org.apache.log4j.Logger;

/**
 * @Author Václav Mareš
 */
public class GuavaParser implements ContractParser {

    final static Logger logger = Logger.getLogger(String.valueOf(ContractParser.class));

    /**
     * This method extracts Design by contract constructions from given file
     *
     * @param javaFile  Parsed input java file
     * @return  ContractFile containing structure of the file with contracts
     */
    @Override
    public JavaFile retrieveContracts(JavaFile javaFile) {

        // TODO

        return javaFile;
    }
}

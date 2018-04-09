package cz.zcu.kiv.contractparser.parser;

import cz.zcu.kiv.contractparser.model.ExtendedJavaFile;

/**
 * Every contract parser has to implement this interface.
 *
 * @author Vaclav Mares
 */
public interface ContractParser {

    /**
     * This method extracts design by contract constructions from given file.
     *
     * @param extendedJavaFile   extendedJavaFile with all necessary information about Java source file
     * @return                   the same extendedJavaFile with newly added contracts
     */
    ExtendedJavaFile retrieveContracts(ExtendedJavaFile extendedJavaFile);

}
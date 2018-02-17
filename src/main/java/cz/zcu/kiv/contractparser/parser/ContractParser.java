package cz.zcu.kiv.contractparser.parser;

import cz.zcu.kiv.contractparser.model.ExtendedJavaFile;

/**
 * This class provides variety of methods used to parse Java source file to extract
 * design by contract constructions from it.
 *
 * @author Vaclav Mares
 */
public interface ContractParser {

    ExtendedJavaFile retrieveContracts(ExtendedJavaFile extendedJavaFile);

}
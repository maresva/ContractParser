package cz.zcu.kiv.contractparser.parser;

import cz.zcu.kiv.contractparser.model.JavaFile;

/**
 * This class provides variety of methods used to parse Java source file to extract
 * design by contract constructions from it.
 *
 * @Author Václav Mareš
 */
public interface ContractParser {

    JavaFile retrieveContracts(JavaFile javaFile);

}
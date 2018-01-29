package cz.zcu.kiv.contractparser.parser;

import cz.zcu.kiv.contractparser.model.ContractType;
import cz.zcu.kiv.contractparser.parser.guavaparser.GuavaParser;
import cz.zcu.kiv.contractparser.parser.jsr305parser.JSR305Parser;
import org.apache.log4j.Logger;

/**
 * TODO
 *
 * @author Vaclav Mares
 */
public class ParserFactory {

    final Logger logger = Logger.getLogger(String.valueOf(ParserFactory.class));

    public ContractParser getParser(ContractType contractType){

        switch (contractType) {
            case GUAVA:
                return new GuavaParser();

            case JSR305:
                return new JSR305Parser();

            default:
                logger.warn("Parser type " + contractType + " not implemented in ParserFactory");
                return null;
        }
    }
}

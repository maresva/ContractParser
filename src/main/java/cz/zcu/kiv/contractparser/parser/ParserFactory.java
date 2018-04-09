package cz.zcu.kiv.contractparser.parser;

import cz.zcu.kiv.contractparser.ResourceHandler;
import cz.zcu.kiv.contractparser.model.ContractType;
import cz.zcu.kiv.contractparser.parser.guavaparser.GuavaParser;
import cz.zcu.kiv.contractparser.parser.jsr305parser.JSR305Parser;
import org.apache.log4j.Logger;

/**
 * Factory providing instance of contract parser of given type.
 *
 * @author Vaclav Mares
 */
public class ParserFactory {

    /** Log4j logger for this class */
    private final Logger logger = Logger.getLogger(String.valueOf(ParserFactory.class));

    /**
     * Create instance of contract parser based on given type
     *
     * @param contractType  Type of contract parser that should be provided
     * @return              Instance of contract parser
     */
    public ContractParser getParser(ContractType contractType){

        switch (contractType) {
            case GUAVA:
                return new GuavaParser();

            case JSR305:
                return new JSR305Parser();

            default:
                logger.warn(ResourceHandler.getMessage("errorParserFactoryUnknown", contractType));
                return null;
        }
    }
}

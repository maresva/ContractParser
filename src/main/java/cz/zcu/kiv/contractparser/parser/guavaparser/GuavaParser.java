package cz.zcu.kiv.contractparser.parser.guavaparser;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.Statement;
import cz.zcu.kiv.contractparser.model.*;
import cz.zcu.kiv.contractparser.parser.ContractParser;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author Vaclav Mares
 */
public class GuavaParser implements ContractParser {

    final static Logger logger = Logger.getLogger(String.valueOf(ContractParser.class));

    /**
     * This method extracts Design by contract constructions from given file
     *
     * @param javaFile Parsed input java file
     * @return ContractFile containing structure of the file with contracts
     */
    @Override
    public JavaFile retrieveContracts(JavaFile javaFile) {

        List<JavaClass> javaClasses = javaFile.getClasses();

        for (int i = 0; i < javaClasses.size() ; i++) {

            for (int j = 0 ; j < javaClasses.get(i).getMethods().size() ; j++) {

                for(int k = 0 ; k < javaClasses.get(i).getMethods().get(j).getBody().size() ; k++) {

                    Node node = javaClasses.get(i).getMethods().get(j).getBody().get(k);

                    Contract contract = new Contract(ContractType.GUAVA, ConditionType.PRE,
                            node, "TEST message");

                    javaClasses.get(i).getMethods().get(j).addContract(contract);
                }
            }
        }

        // TODO

        return javaFile;
    }
}

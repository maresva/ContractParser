package cz.zcu.kiv.contractparser.parser.guavaparser;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import cz.zcu.kiv.contractparser.model.*;
import cz.zcu.kiv.contractparser.parser.ContractParser;
import cz.zcu.kiv.contractparser.utility.StringOperator;
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
     * @param extendedJavaFile Parsed input java file
     * @return ContractFile containing structure of the file with contracts
     */
    @Override
    public ExtendedJavaFile retrieveContracts(ExtendedJavaFile extendedJavaFile) {

        List<ExtendedJavaClass> extendedJavaClasses = extendedJavaFile.getExtendedJavaClasses();

        if(extendedJavaClasses == null){
            return extendedJavaFile;
        }

        for (int i = 0; i < extendedJavaClasses.size() ; i++) {

            for (int j = 0; j < extendedJavaClasses.get(i).getExtendedJavaMethods().size() ; j++) {

                for(int k = 0; k < extendedJavaClasses.get(i).getExtendedJavaMethods().get(j).getBody().size() ; k++) {

                    Node node = extendedJavaClasses.get(i).getExtendedJavaMethods().get(j).getBody().get(k);

                    Contract contract = evaluateExpression(node);

                    if(contract != null){
                        extendedJavaClasses.get(i).getExtendedJavaMethods().get(j).addContract(contract);
                        extendedJavaClasses.get(i).getExtendedJavaMethods().get(j)
                                .increaseNumberOfContracts(ContractType.GUAVA, 1);
                        extendedJavaFile.increaseNumberOfContracts(ContractType.GUAVA, 1);
                    }
                }
            }
        }

        // TODO

        return extendedJavaFile;
    }


    private Contract evaluateExpression (Node node){

        // TODO vymyslet jak lépe získat metody z Guava Preconditions
        String[] preconditionsMethods = {"checkArgument", "checkState", "checkNotNull", "checkElementIndex",
                "badElementIndex", "checkPositionIndex", "badPositionIndex", "checkPositionIndexes",
                "badPositionIndexes"};

        for(String methodName : preconditionsMethods){

            String expression = node.toString();
            int index = expression.indexOf(methodName);
            if(index > 0){

                if(StringOperator.verifyMethodClass(expression, "Preconditions", index)) {
                    //System.out.println(" - TRUE");
                    // TODO pridat kontrolu, jestli není metoda jiné knihovny (pokud . před, musí být Preconditions)
                    // importy sledovat ???
                    Contract contract = new Contract(ContractType.GUAVA, ConditionType.PRE,
                            node, node.toString(), "TEST message");

                    return contract;
                }
            }
        }

        return null;
    }
}

package cz.zcu.kiv.contractparser.parser.guavaparser;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import cz.zcu.kiv.contractparser.model.*;
import cz.zcu.kiv.contractparser.parser.ContractParser;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vaclav Mares
 */
public class GuavaParser implements ContractParser {

    final static Logger logger = Logger.getLogger(String.valueOf(ContractParser.class));

    final static String[] PRECONDITIONS_METHODS = {"checkArgument", "checkState", "checkNotNull", "checkElementIndex",
            "badElementIndex", "checkPositionIndex", "badPositionIndex", "checkPositionIndexes",
            "badPositionIndexes"};

    
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
                        
                        contract.setFile(extendedJavaFile.getPath());
                        contract.setClassName(extendedJavaClasses.get(i).getName());
                        contract.setMethodName(extendedJavaClasses.get(i).getExtendedJavaMethods().get(j).getSignature());

                        extendedJavaClasses.get(i).getExtendedJavaMethods().get(j).addContract(contract);
                        extendedJavaClasses.get(i).getExtendedJavaMethods().get(j)
                                .increaseNumberOfContracts(ContractType.GUAVA, 1);
                        extendedJavaFile.increaseNumberOfContracts(ContractType.GUAVA, 1);
                    }
                }
            }
        }

        return extendedJavaFile;
    }


    private Contract evaluateExpression (Node node){

        // try if given is a method call (Guava contract is always a method call)
        try{
            // convert node to method call expression
            Expression expression = ((ExpressionStmt) node).getExpression();
            MethodCallExpr methodCallExpr = (MethodCallExpr) expression;

            // get used contract method name
            String methodName = methodCallExpr.getNameAsString();

            // if the method is one of Precondition methods - process contract
            for(String preconditionMethod : PRECONDITIONS_METHODS){

                if(preconditionMethod.compareTo(methodName) == 0){

                    // TODO zkontrolovat jestli je scope nic nebo Preconditions nebo com.google.common.base.Preconditions
                    System.out.println("SCOPE: " + methodCallExpr.getScope().get());

                    // get method call arguments - we are interested in first two (expression and error message)
                    NodeList<Expression> arguments = methodCallExpr.getArguments();

                    String errorMessage = "";
                    List<String> stringArguments = new ArrayList<>();

                    // Guava contracts always have at least one argument
                    if(arguments != null && arguments.size() > 0){

                        String parameterExpression = arguments.get(0).toString();

                        // second argument (message or message template) is optional
                        if(arguments.size() > 1){
                            errorMessage = arguments.get(1).toString();
                        }

                        // other arguments are used to fill parameters into message template
                        for(int i = 2 ; i < arguments.size() ; i++){
                            stringArguments.add(arguments.get(i).toString());
                        }

                        return new Contract(ContractType.GUAVA, ConditionType.PRE, node.toString(), methodName,
                                parameterExpression, errorMessage, stringArguments);
                    }
                    else{
                        return null;
                    }
                }
            }
        }
        catch (ClassCastException e){
            // node is not a method call - not a Guava contract
            return null;
        }

        return null;
    }
}

package cz.zcu.kiv.contractparser.parser.guavaparser;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import cz.zcu.kiv.contractparser.ResourceHandler;
import cz.zcu.kiv.contractparser.model.*;
import cz.zcu.kiv.contractparser.parser.ContractParser;

import java.util.ArrayList;
import java.util.List;

/**
 * GuavaParser provides means to extract contracts of Guava type from given Extended Java File
 *
 * @author Vaclav Mares
 */
public class GuavaParser implements ContractParser {

    /** Names of Guava Precondition methods. Those methods are used as to realize contracts. */
    private final static String[] PRECONDITIONS_METHODS = {"checkArgument", "checkState", "checkNotNull",
            "checkElementIndex", "badElementIndex", "checkPositionIndex", "badPositionIndex", "checkPositionIndexes",
            "badPositionIndexes"};

    /**
     * This method extracts design by contract constructions of Guava Preconditions type from given file.
     *
     * @param extendedJavaFile   extendedJavaFile with all necessary information about Java source file
     * @return                   the same extendedJavaFile with newly added contracts
     */
    @Override
    public ExtendedJavaFile retrieveContracts(ExtendedJavaFile extendedJavaFile) {

        List<ExtendedJavaClass> extendedJavaClasses = extendedJavaFile.getExtendedJavaClasses();

        if(extendedJavaClasses == null){
            return extendedJavaFile;
        }

        // for each class
        for (ExtendedJavaClass extendedJavaClass : extendedJavaClasses) {

            // for each method
            for (int j = 0; j < extendedJavaClass.getExtendedJavaMethods().size(); j++) {

                boolean methodHasContract = false;

                // for each method body node
                for (int k = 0; k < extendedJavaClass.getExtendedJavaMethods().get(j).getBody().size(); k++) {

                    Node node = extendedJavaClass.getExtendedJavaMethods().get(j).getBody().get(k);

                    Contract contract = evaluateExpression(node);

                    if (contract != null) {

                        if (extendedJavaClass.getExtendedJavaMethods().get(j).getContracts().size() == 0) {
                            methodHasContract = true;
                        }

                        extendedJavaClass.getExtendedJavaMethods().get(j).addContract(contract);
                        extendedJavaFile.getJavaFileStatistics().increaseNumberOfContracts(ContractType.GUAVA, 1);
                    }
                }

                if (methodHasContract) {
                    extendedJavaFile.getJavaFileStatistics().increaseNumberOfMethodsWithContracts(1);
                }
            }
        }

        return extendedJavaFile;
    }


    /**
     * This method evaluates given Node and determines whether it is a Guava contract or not. If so contract is prepared
     * otherwise null is returned.
     *
     * @param node  Node - complex object representing line of code
     * @return      prepared Contract or null
     */
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

                    // check the scope of method - it has to have short Preconditions.* or full scope
                    // com.google.common.base.Preconditions.* or it has to be empty
                    if(methodCallExpr.getScope() != null && methodCallExpr.getScope().isPresent()) {
                        String scope = methodCallExpr.getScope().get().toString();

                        if(scope.compareTo(ResourceHandler.getProperties().getString("guavaPreconditionFullScope"))
                                != 0 && scope.compareTo(ResourceHandler.getProperties().
                                getString("guavaPreconditionShortScope")) != 0 && scope.length() != 0){

                            return null;
                        }
                    }

                    // get method call arguments
                    NodeList<Expression> arguments = methodCallExpr.getArguments();

                    List<String> stringArguments = new ArrayList<>();

                    // Guava contracts always have at least one argument
                    if(arguments != null && arguments.size() > 0){

                        String parameterExpression = arguments.get(0).toString();

                        // other arguments are used to further parametrize the contract (usually it contains error messages)
                        for(int i = 1 ; i < arguments.size() ; i++){
                            stringArguments.add(arguments.get(i).toString());
                        }

                        return new Contract(ContractType.GUAVA, ConditionType.PRE, node.toString(), methodName,
                                parameterExpression, stringArguments);
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

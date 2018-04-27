package cz.zcu.kiv.contractparser.parser.jsr305parser;

import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import cz.zcu.kiv.contractparser.model.*;
import cz.zcu.kiv.contractparser.parser.ContractParser;
import cz.zcu.kiv.contractparser.parser.parsermodel.ExtendedJavaClass;
import cz.zcu.kiv.contractparser.parser.parsermodel.ExtendedJavaFile;

import java.util.ArrayList;
import java.util.List;

/**
 * JSR305Parser provides means to extract contracts of JSR305 type from given Extended Java File
 *
 * @author Vaclav Mares
 */
public class JSR305Parser implements ContractParser {

    /** List of JSR305 annotations. Those are used in methods and classes to realise JSR305 contracts. */
    private final String[] JSR305_ANNOTATIONS = {"CheckForNull", "CheckForSigned", "CheckReturnValue", "Detainted",
            "MatchesPattern", "Nonnegative", "Nonnull", "Nullable", "OverridingMethodsMustInvokeSuper",
            "ParametersAreNonnullByDefault", "ParametersAreNullableByDefault", "PropertyKey", "RegEx", "Signed",
            "Syntax", "Tainted", "Untainted", "WillClose", "WillCloseWhenClosed", "WillNotClose"};
    

    /**
     * This method extracts design by contract constructions of JSR305 type from given file.
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

            // save JSR305 contracts in class annotations
            for (AnnotationExpr annotationExpr : extendedJavaClass.getAnnotations()) {

                // check the class annotation for invariant
                Contract contract = prepareContract(annotationExpr, ConditionType.INVARIANT, annotationExpr.toString());

                // if recognized as a invariant - save it
                if (contract != null) {
                    extendedJavaClass.addInvariant(contract);
                    extendedJavaFile.getJavaFileStatistics().increaseNumberOfContracts(ContractType.JSR305, 1);
                }
            }

            // for each method
            for (int j = 0; j < extendedJavaClass.getExtendedJavaMethods().size(); j++) {

                boolean methodHasContract = false;

                // save JSR305 contracts in class annotations
                for (AnnotationExpr annotation : extendedJavaClass.getExtendedJavaMethods().get(j).getAnnotations()) {

                    // check the method annotation for post-condition
                    Contract contract = prepareContract(annotation, ConditionType.POST, annotation.toString());

                    // if recognized as a contract - save it
                    if (contract != null) {
                        extendedJavaClass.getExtendedJavaMethods().get(j).addContract(contract);
                        extendedJavaFile.getJavaFileStatistics().increaseNumberOfContracts(ContractType.JSR305, 1);
                        methodHasContract = true;
                    }
                }

                // for each parameter
                for (Parameter parameter : extendedJavaClass.getExtendedJavaMethods().get(j).getParameters()) {

                    // for each annotation
                    for (AnnotationExpr annotation : parameter.getAnnotations()) {

                        // check the method annotation for pre-condition
                        Contract contract = prepareContract(annotation, ConditionType.PRE, parameter.toString());

                        // if recognized as a contract - save it
                        if (contract != null) {
                            contract.setExpression(parameter.getType() + " " + parameter.getName().toString());
                            extendedJavaClass.getExtendedJavaMethods().get(j).addContract(contract);
                            extendedJavaFile.getJavaFileStatistics().increaseNumberOfContracts(ContractType.JSR305, 1);
                            methodHasContract = true;
                        }
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
     * Determines whether it is a JSR305 contract and if so contract object is prepared based on given data. Otherwise
     * null is returned.
     *
     * @param annotationExpr        expression containing JSR305 annotation
     * @param conditionType         type of contract condition based on location where it was found
     * @param completeExpression    complete expression prepared for saving
     * @return                      Contract object with filled data or null
     */
    private Contract prepareContract(AnnotationExpr annotationExpr, ConditionType conditionType,
                                     String completeExpression) {

        Contract contract = null;

        // go through all possible JSR305 annotations and try to find match
        for(String JSR305annotation : JSR305_ANNOTATIONS){

            // if match is found contract is prepared
            if(annotationExpr.getName().toString().compareTo(JSR305annotation) == 0){

                String expression = "";
                List<String> arguments = new ArrayList<>();

                if(annotationExpr.getChildNodes().size() > 1){
                    expression = annotationExpr.getChildNodes().get(1).toString();

                    arguments = new ArrayList<>();
                    for(int i = 2 ; i < annotationExpr.getChildNodes().size() ; i++){
                        arguments.add(annotationExpr.getChildNodes().get(i).toString());
                    }
                }

                contract = new Contract(ContractType.JSR305, conditionType,
                        completeExpression, JSR305annotation, expression, arguments);
            }
        }

        return contract;
    }
}

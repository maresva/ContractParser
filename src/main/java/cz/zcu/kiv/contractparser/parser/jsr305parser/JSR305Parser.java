package cz.zcu.kiv.contractparser.parser.jsr305parser;

import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import cz.zcu.kiv.contractparser.model.*;
import cz.zcu.kiv.contractparser.parser.ContractParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Vaclav Mares
 */
public class JSR305Parser implements ContractParser {

    private final String[] JSR305_ANNOTATIONS = {"CheckForNull", "CheckForSigned", "CheckReturnValue", "Detainted",
            "Generated", "MatchesPattern", "Nonnegative", "Nonnull", "Nullable", "OverridingMethodsMustInvokeSuper",
            "ParametersAreNonnullByDefault", "ParametersAreNullableByDefault", "PostConstruct", "PreDestroy",
            "PropertyKey", "RegEx", "Resource", "Resources", "Signed", "Syntax", "Tainted", "Untainted",
            "WillClose", "WillCloseWhenClosed", "WillNotClose"};

    
    @Override
    public ExtendedJavaFile retrieveContracts(ExtendedJavaFile extendedJavaFile) {

        List<ExtendedJavaClass> extendedJavaClasses = extendedJavaFile.getExtendedJavaClasses();

        if(extendedJavaClasses == null){
            return extendedJavaFile;
        }

        String className, methodSignature;

        // for each class
        for (int i = 0; i < extendedJavaClasses.size() ; i++) {

            className = extendedJavaClasses.get(i).getName();

            // save JSR305 contracts in class annotations
            for(AnnotationExpr annotationExpr : extendedJavaClasses.get(i).getAnnotations()){

                // check the class annotation for invariant
                Contract contract = prepareContract(annotationExpr, ConditionType.INVARIANT, annotationExpr.toString(),
                        extendedJavaFile.getPath(), className, "");

                // if recognized as a invariant - save it
                if(contract != null) {
                    extendedJavaClasses.get(i).addInvariant(contract);
                    extendedJavaFile.getJavaFileStatistics().increaseNumberOfContracts(ContractType.JSR305, 1);
                }
            }

            // for each method
            for (int j = 0; j < extendedJavaClasses.get(i).getExtendedJavaMethods().size() ; j++) {

                methodSignature = extendedJavaClasses.get(i).getExtendedJavaMethods().get(j).getSignature();
                boolean methodHasContract = false;

                // save JSR305 contracts in class annotations
                for(AnnotationExpr annotation : extendedJavaClasses.get(i).getExtendedJavaMethods().get(j).getAnnotations()){

                    // check the method annotation for post-condition
                    Contract contract = prepareContract(annotation, ConditionType.POST, annotation.toString(),
                            extendedJavaFile.getPath(), className, methodSignature);

                    // if recognized as a contract - save it
                    if(contract != null) {
                        extendedJavaClasses.get(i).getExtendedJavaMethods().get(j).addContract(contract);
                        extendedJavaFile.getJavaFileStatistics().increaseNumberOfContracts(ContractType.JSR305, 1);
                        methodHasContract = true;
                    }
                }

                // for each parameter
                for(Parameter parameter : extendedJavaClasses.get(i).getExtendedJavaMethods().get(j).getParameters()) {

                    // for each annotation
                    for(AnnotationExpr annotation : parameter.getAnnotations()){

                        // check the method annotation for pre-condition
                        Contract contract = prepareContract(annotation, ConditionType.PRE, parameter.toString(),
                                extendedJavaFile.getPath(), className, methodSignature);

                        // if recognized as a contract - save it
                        if(contract != null) {
                            extendedJavaClasses.get(i).getExtendedJavaMethods().get(j).addContract(contract);
                            extendedJavaFile.getJavaFileStatistics().increaseNumberOfContracts(ContractType.JSR305, 1);
                            methodHasContract = true;
                        }
                    }
                }

                if(methodHasContract){
                    extendedJavaFile.getJavaFileStatistics().increaseNumberOfMethodsWithContracts(1);
                }
            }
        }

        return extendedJavaFile;
    }


    private Contract prepareContract(AnnotationExpr annotationExpr, ConditionType conditionType, String completeExpression,
                                     String filePath, String className, String methodSignature) {

        Contract contract = null;

        for(String JSR305annotation : JSR305_ANNOTATIONS){

            if(annotationExpr.getName().toString().compareTo(JSR305annotation) == 0){

                String expression = "";
                List<String> arguments = new ArrayList<>();
                for(int i = 1 ; i < annotationExpr.getChildNodes().size() ; i++){

                      if(i == 1){
                          expression = annotationExpr.getChildNodes().get(i).toString();
                      }
                      else{

                      }
                }

                contract = new Contract(ContractType.JSR305, conditionType,
                        completeExpression, JSR305annotation, expression,
                        "", arguments);

                contract.setFile(filePath);
                contract.setClassName(className);
                contract.setMethodName(methodSignature);
            }
        }

        return contract;
    }
}

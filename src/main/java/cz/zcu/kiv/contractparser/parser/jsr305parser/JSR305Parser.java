package cz.zcu.kiv.contractparser.parser.jsr305parser;

import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.AnnotationExpr;
import cz.zcu.kiv.contractparser.model.*;
import cz.zcu.kiv.contractparser.parser.ContractParser;

import java.util.List;

/**
 * @author Vaclav Mares
 */
public class JSR305Parser implements ContractParser {

    @Override
    public ExtendedJavaFile retrieveContracts(ExtendedJavaFile extendedJavaFile) {

        List<ExtendedJavaClass> extendedJavaClasses = extendedJavaFile.getExtendedJavaClasses();

        // TODO complete list
        String[] JSR305annotations = {"Nonnull", "Nullable"};

        if(extendedJavaClasses == null){
            return extendedJavaFile;
        }

        for (int i = 0; i < extendedJavaClasses.size() ; i++) {

            for (int j = 0; j < extendedJavaClasses.get(i).getExtendedJavaMethods().size() ; j++) {

                // TODO check overall method annotations

                for(Parameter parameter : extendedJavaClasses.get(i).getExtendedJavaMethods().get(j).getParameters()) {

                    for(AnnotationExpr annotation : parameter.getAnnotations()){

                        for(String JSR305annotation : JSR305annotations){

                            if(annotation.toString().compareTo("@" + JSR305annotation) == 0){
                                
                                Contract contract = new Contract(ContractType.JSR305, ConditionType.PRE,
                                        parameter.toString(), annotation.toString(), parameter.toString(),
                                        "", null);

                                contract.setFile(extendedJavaFile.getPath());
                                contract.setClassName(extendedJavaClasses.get(i).getName());
                                contract.setMethodName(extendedJavaClasses.get(i).getExtendedJavaMethods()
                                        .get(j).getSignature());

                                extendedJavaClasses.get(i).getExtendedJavaMethods().get(j).addContract(contract);
                                extendedJavaClasses.get(i).getExtendedJavaMethods().get(j)
                                        .increaseNumberOfContracts(ContractType.JSR305, 1);
                                extendedJavaFile.increaseNumberOfContracts(ContractType.JSR305, 1);
                            }
                        }
                    }
                }
            }
        }

        return extendedJavaFile;
    }
}

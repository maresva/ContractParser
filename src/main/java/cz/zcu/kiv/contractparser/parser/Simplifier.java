package cz.zcu.kiv.contractparser.parser;

import cz.zcu.kiv.contractparser.model.*;

public class Simplifier {

    public static JavaFile simplifyExtendedJavaFile(ExtendedJavaFile extendedJavaFile) {

        if(extendedJavaFile == null){
            return null;
        }

        JavaFile javaFile = new JavaFile();

        for(ExtendedJavaClass extendedJavaClass : extendedJavaFile.getExtendedJavaClasses()) {

             JavaClass javaClass = simplifyJavaClass(extendedJavaClass);
             javaFile.addClass(javaClass);
        }

        javaFile.setPath(extendedJavaFile.getPath());
        javaFile.setFileName(extendedJavaFile.getFileName());
        javaFile.setFileType(extendedJavaFile.getFileType());

        javaFile.setNumberOfClasses(extendedJavaFile.getNumberOfClasses());
        javaFile.setNumberOfMethods(extendedJavaFile.getNumberOfMethods());
        javaFile.setNumberOfContracts(extendedJavaFile.getNumberOfContracts());

        return javaFile;
    }


    public static JavaClass simplifyJavaClass(ExtendedJavaClass extendedJavaClass) {

        if(extendedJavaClass == null){
            return null;
        }

        JavaClass javaClass = new JavaClass();

        for(ExtendedJavaMethod extendedJavaMethod : extendedJavaClass.getExtendedJavaMethods()) {

            JavaMethod javaMethod = simplifyJavaMethod(extendedJavaMethod);
            javaClass.addJavaMethod(javaMethod);
        }

        for(ExtendedJavaClass innerExtendedJavaClass : extendedJavaClass.getInnerExtendedJavaClasses()) {

            JavaClass innerJavaClass = simplifyJavaClass(innerExtendedJavaClass);
            javaClass.addInnerClass(innerJavaClass);
        }

        javaClass.setName(extendedJavaClass.getName());
        javaClass.setInvariants(extendedJavaClass.getInvariants());

        return javaClass;
    }

    public static JavaMethod simplifyJavaMethod(ExtendedJavaMethod extendedJavaMethod) {

        if(extendedJavaMethod == null){
            return null;
        }

        JavaMethod javaMethod = new JavaMethod();

        for(ExtendedJavaMethod innerExtendedJavaMethod : extendedJavaMethod.getInnerExtendedJavaMethods()) {

            JavaMethod innerJavaMethod = simplifyJavaMethod(innerExtendedJavaMethod);
            javaMethod.addInnerMethod(innerJavaMethod);
        }

        javaMethod.setSignature(extendedJavaMethod.getSignature());
        javaMethod.setContracts(extendedJavaMethod.getContracts());

        return javaMethod;
    }
}

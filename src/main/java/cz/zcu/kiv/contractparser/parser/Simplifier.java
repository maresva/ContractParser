package cz.zcu.kiv.contractparser.parser;

import cz.zcu.kiv.contractparser.model.*;

public class Simplifier {

    public static JavaFile simplifyExtendedJavaFile(ExtendedJavaFile extendedJavaFile, boolean retrieveWholeApi) {

        if(extendedJavaFile == null){
            return null;
        }

        JavaFile javaFile = new JavaFile();

        for(ExtendedJavaClass extendedJavaClass : extendedJavaFile.getExtendedJavaClasses()) {

             JavaClass javaClass = simplifyJavaClass(extendedJavaClass);
             javaFile.addJavaClass(javaClass);
        }

        javaFile.setPath(extendedJavaFile.getPath());
        javaFile.setFileName(extendedJavaFile.getFileName());
        javaFile.setFileType(extendedJavaFile.getFileType());
        javaFile.setJavaFileStatistics(extendedJavaFile.getJavaFileStatistics());

        return javaFile;
    }


    public static JavaClass simplifyJavaClass(ExtendedJavaClass extendedJavaClass) {

        if(extendedJavaClass == null){
            return null;
        }

        JavaClass javaClass = new JavaClass(extendedJavaClass.getName(), extendedJavaClass.getSignature());

        for(ExtendedJavaMethod extendedJavaMethod : extendedJavaClass.getExtendedJavaMethods()) {

            JavaMethod javaMethod = simplifyJavaMethod(extendedJavaMethod);
            javaClass.addJavaMethod(javaMethod);
        }

        javaClass.setName(extendedJavaClass.getName());
        javaClass.setInvariants(extendedJavaClass.getInvariants());

        return javaClass;
    }

    public static JavaMethod simplifyJavaMethod(ExtendedJavaMethod extendedJavaMethod) {

        if(extendedJavaMethod == null){
            return null;
        }

        JavaMethod javaMethod = new JavaMethod(extendedJavaMethod.getSignature(), extendedJavaMethod.isConstructor());

        javaMethod.setSignature(extendedJavaMethod.getSignature());
        javaMethod.setContracts(extendedJavaMethod.getContracts());

        return javaMethod;
    }


    public static JavaFile removeNonContractObjects(JavaFile javaFile) {

        // if there is no contract in the whole file just return null
        if(javaFile.getJavaFileStatistics().getTotalNumberOfContracts() == 0){
            return null;
        }
        else{

            int i = 0;
            int j = 0;
            int classesSize = javaFile.getJavaClasses().size();

            while(i < classesSize){

               int methodsSize = javaFile.getJavaClasses().get(i).getJavaMethods().size();

               while(j < methodsSize){

                   // if there are no contracts in the method - remove it
                   if(javaFile.getJavaClasses().get(i).getJavaMethods().get(j).getContracts().size() == 0){
                       javaFile.getJavaClasses().get(i).getJavaMethods().remove(j);
                       methodsSize--;
                   }
                   else{
                       j++;
                   }
               }

               // if there are no methods in the class - remove it
               if(methodsSize == 0){
                   javaFile.getJavaClasses().remove(i);
                   classesSize--;
               }
               else{
                   i++;
               }
            }
        }

        return javaFile;
    }
}

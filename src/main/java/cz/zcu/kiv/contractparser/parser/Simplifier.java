package cz.zcu.kiv.contractparser.parser;


import cz.zcu.kiv.contractparser.model.*;

/**
 * This method provides to means to do transition from ExtendedJavaFile to JavaFile. It basically removes all
 * information that are needed for contract extraction and retains only those that are useful for further work
 * and export.
 *
 * @author Vaclav Mares
 */
public class Simplifier {

    /** Private constructor to prevent its use */
    private Simplifier() {
    }
    

    /**
     * Simplifies the input ExtendedJavaFile by discarding data that are no longer useful and creates JavaFile.
     *
     * @param extendedJavaFile  ExtendedJavaFile to be simplified
     * @return                  JavaFile created from ExtendedJavaFile
     */
    public static JavaFile simplifyExtendedJavaFile(ExtendedJavaFile extendedJavaFile) {

        if(extendedJavaFile == null){
            return null;
        }

        JavaFile javaFile = new JavaFile();

        for(ExtendedJavaClass extendedJavaClass : extendedJavaFile.getExtendedJavaClasses()) {

             JavaClass javaClass = simplifyJavaClass(extendedJavaClass);
             javaFile.addJavaClass(javaClass);
        }

        javaFile.setShortPath(extendedJavaFile.getShortPath());
        javaFile.setFullPath(extendedJavaFile.getFullPath());
        javaFile.setFileName(extendedJavaFile.getFileName());
        javaFile.setFileType(extendedJavaFile.getFileType());
        javaFile.setJavaFileStatistics(extendedJavaFile.getJavaFileStatistics());

        return javaFile;
    }


    /**
     * Simplifies the input ExtendedJavaClass by discarding data that are no longer useful and creates JavaClass.
     *
     * @param extendedJavaClass  ExtendedJavaClass to be simplified
     * @return                  JavaClass created from ExtendedJavaClass
     */
    private static JavaClass simplifyJavaClass(ExtendedJavaClass extendedJavaClass) {

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

    /**
     * Simplifies the input ExtendedJavaMethod by discarding data that are no longer useful and creates JavaMethod.
     *
     * @param extendedJavaMethod  ExtendedJavaMethod to be simplified
     * @return                  JavaMethod created from ExtendedJavaMethod
     */
    private static JavaMethod simplifyJavaMethod(ExtendedJavaMethod extendedJavaMethod) {

        if(extendedJavaMethod == null){
            return null;
        }

        JavaMethod javaMethod = new JavaMethod(extendedJavaMethod.getSignature(), extendedJavaMethod.isConstructor());

        javaMethod.setSignature(extendedJavaMethod.getSignature());
        javaMethod.setContracts(extendedJavaMethod.getContracts());

        return javaMethod;
    }


    /**
     * This method removes all classes and methods that does not contain any contracts. It is useful for situations
     * where many object doesn't have any contracts.
     *
     * @param javaFile  JavaFile to be reduced
     * @return          JavaFile without non contract objects
     */
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

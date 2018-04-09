package cz.zcu.kiv.contractparser.comparator;

import cz.zcu.kiv.contractparser.model.Contract;
import cz.zcu.kiv.contractparser.model.JavaClass;
import cz.zcu.kiv.contractparser.model.JavaFile;
import cz.zcu.kiv.contractparser.model.JavaMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class provides means to compare two JavaFiles respectively their API and contracts.
 *
 * @author Vaclav Mares
 * */
public class JavaFileComparator {

    /** Contract comparisons which are allowed for the first search loop. It says contract should be equal or almost equal */
    private final static List<ContractComparison> ALLOWED_COMPARISONS_FIRST_SEARCH = Arrays.asList(
            ContractComparison.EQUAL, ContractComparison.MINOR_CHANGE);

    /** Contract comparisons which are allowed for the second search loop. It says contract should be equal except for expression */
    private final static List<ContractComparison> ALLOWED_COMPARISONS_SECOND_SEARCH = Arrays.asList(
            ContractComparison.GENERALIZED, ContractComparison.SPECIALIZED, ContractComparison.DIFFERENT_EXPRESSION);

    /** Contract comparison of two current contracts */
    private static ContractComparison contractComparison = ContractComparison.DIFFERENT;


    /**
     * This method compares two JavaFiles and creates report about their differences in API and contracts. Reporting of
     * equal objects as well as those that don't contain contracts can be turned off.
     *
     * @param javaFileX                 First JavaFile to be compared
     * @param javaFileY                 Second JavaFile to be compared
     * @param reportEqual               Whether equal object should be reported or not
     * @param reportNonContractChanges  Whether changes in objects that don't contain contracts should be reported or not
     * @return                          JavaFileCompareReport containing all gathered information about comparison
     */
    public JavaFileCompareReport compareJavaFiles(JavaFile javaFileX, JavaFile javaFileY, boolean reportEqual, boolean reportNonContractChanges) {

        JavaFileCompareReport javaFileCompareReport = new JavaFileCompareReport(javaFileX.getFullPath(), javaFileY.getFullPath());

        // go through all classes of file X
        for(int javaClassXId = 0; javaClassXId < javaFileX.getJavaClasses().size() ; javaClassXId++){

            JavaClass javaClassX = javaFileX.getJavaClasses().get(javaClassXId);

            // try to find pair class in the other file
            int javaClassYId = findPairClassY(javaClassX, javaFileY.getJavaClasses());

            // if the class was not found - report the api change
            if(javaClassYId < 0){
                    javaFileCompareReport.addApiChange(new ApiChange(ApiType.CLASS, javaClassX.getSignature(),
                            javaClassX.getTotalNumberOfContracts(), ApiState.REMOVED), reportNonContractChanges);
                break;
            }

            JavaClass javaClassY = javaFileY.getJavaClasses().get(javaClassYId);


            // go through all methods of class X
            for(int javaMethodXId = 0 ; javaMethodXId < javaClassX.getJavaMethods().size() ; javaMethodXId++){

                JavaMethod javaMethodX = javaClassX.getJavaMethods().get(javaMethodXId);

                // try to find pair method in the other class
                int javaMethodYId = findPairMethodY(javaMethodX, javaClassY.getJavaMethods());

                // if the method was not found - save the api change information
                if(javaMethodYId < 0){
                    javaFileCompareReport.addApiChange(new ApiChange(ApiType.METHOD, javaMethodX.getSignature(),
                            javaMethodX.getContracts().size(), ApiState.REMOVED), reportNonContractChanges);
                    break;
                }

                JavaMethod javaMethodY = javaClassY.getJavaMethods().get(javaMethodYId);

                List<Contract> notFoundContractsX = new ArrayList<>();


                // go through all contracts of method X - try to find contract that is equal or has only minor changes
                for(int contractXId = 0 ; contractXId < javaMethodX.getContracts().size() ; contractXId++){

                    Contract contractX = javaMethodX.getContracts().get(contractXId);
                    int contractYId = findPairContract(contractX, javaMethodY.getContracts(),
                            ALLOWED_COMPARISONS_FIRST_SEARCH);

                    // if the contract was not found - add it to list of not found contracts
                    if(contractYId < 0){
                        notFoundContractsX.add(contractX);
                    }
                    // otherwise add it to compare report if equal reports are desired
                    else{
                        Contract contractY = javaMethodY.getContracts().get(contractYId);

                        if (reportEqual) {
                            ContractCompareReport contractCompareReport = new ContractCompareReport(
                                    contractComparison, javaClassX.getName(), javaMethodX.getSignature(),
                                    contractX.getCompleteExpression(), contractY.getCompleteExpression(),
                                    ApiState.FOUND_PAIR);

                            javaFileCompareReport.addContractReport(contractCompareReport);
                        }

                        // remove processed contract
                        javaMethodY.getContracts().remove(contractYId);
                    }
                }


                // go through all contracts of method X - try to find the same type with different expression
                for(Contract contractX : notFoundContractsX){

                    int contractYId = findPairContract(contractX, javaMethodY.getContracts(),
                            ALLOWED_COMPARISONS_SECOND_SEARCH);

                    String contractYExpression;
                    ApiState contractYApiState;

                    // if the contract was not found - prepare data as if the contract was removed
                    if(contractYId < 0){
                        contractYExpression = "";
                        contractYApiState = ApiState.REMOVED;
                    }
                    // otherwise prepare data of found contract and then remove it from the list
                    else{
                        Contract contractY = javaMethodY.getContracts().get(contractYId);
                        contractYExpression = contractY.getCompleteExpression();
                        contractYApiState = ApiState.FOUND_PAIR;

                        // remove processed contract
                        javaMethodY.getContracts().remove(contractYId);
                    }

                    // create report based on prepared data and add it to list of reports
                    ContractCompareReport contractCompareReport = new ContractCompareReport(
                            contractComparison, javaClassX.getName(), javaMethodX.getSignature(),
                            contractX.getCompleteExpression(), contractYExpression, contractYApiState);

                    javaFileCompareReport.addContractReport(contractCompareReport);
                }


                // report all contracts that left as newly added
                for(Contract contractY : javaMethodY.getContracts()){

                    ContractCompareReport contractCompareReport = new ContractCompareReport(
                            ContractComparison.DIFFERENT, javaClassX.getName(), javaMethodX.getSignature(),
                            "", contractY.getCompleteExpression(), ApiState.ADDED);

                    javaFileCompareReport.addContractReport(contractCompareReport);
                }

                // mark if there were any changes in contracts
                if(notFoundContractsX.size() > 0 || javaMethodY.getContracts().size() > 0){
                    javaFileCompareReport.setContractEqual(false);
                }

                // remove processed method
                javaClassY.getJavaMethods().remove(javaMethodYId);
            }


            // report all methods that left
            for(JavaMethod javaMethodY : javaClassY.getJavaMethods()){

                javaFileCompareReport.addApiChange(new ApiChange(ApiType.METHOD, javaMethodY.getSignature(),
                        javaMethodY.getContracts().size(), ApiState.ADDED), reportNonContractChanges);
            }

            // remove processed class
            javaFileY.getJavaClasses().remove(javaClassYId);
        }


        // report all classes that left
        for(JavaClass javaClassY : javaFileY.getJavaClasses()){

            javaFileCompareReport.addApiChange(new ApiChange(ApiType.CLASS, javaClassY.getSignature(),
                    javaClassY.getTotalNumberOfContracts(), ApiState.ADDED), reportNonContractChanges);
        }

        // mark if there were any changes in the API
        if(javaFileCompareReport.getApiChanges().size() > 0){
            javaFileCompareReport.setApiEqual(false);
        }

        return javaFileCompareReport;
    }


    /**
     * Find a pair class for given class in given list of class. Class has to has to the same name. If class was found
     * its ID is returned otherwise it is -1.
     *
     * @param javaClassX    JavaClass which pair should be found
     * @param javaClassesY  List of classes where the pair should be
     * @return              ID of class in list if found, -1 otherwise
     */
    private int findPairClassY(JavaClass javaClassX, List<JavaClass> javaClassesY) {

        int classIndexY = 0;
        boolean classFound = false;

        // go through classes of file X and try to find class with the same name
        while(classIndexY < javaClassesY.size()){

            if(javaClassX.getName().compareTo(javaClassesY.get(classIndexY).getName()) == 0){
                classFound = true;
                break;
            }

            classIndexY++;
        }

        // return ID of class if it was found otherwise return -1
        if(!classFound){
            return -1;
        }
        else{
            return classIndexY;
        }
    }


    /**
     * Find a pair method for given method in given list of methods. Method has to has to the same signature. If method
     * was found its ID is returned otherwise it is -1.
     *
     * @param javaMethodX   JavaMethod which pair should be found
     * @param javaMethodsY  List of methods where the pair should be
     * @return              ID of method in list if found, -1 otherwise
     */
    private int findPairMethodY(JavaMethod javaMethodX, List<JavaMethod> javaMethodsY) {

        int methodIndexY = 0;
        boolean methodFound = false;

        // go through methods of class X and try to find method with the same signature
        while (methodIndexY < javaMethodsY.size()){

            if(javaMethodX.getSignature().compareTo(javaMethodsY.get(methodIndexY).getSignature()) == 0){
                methodFound = true;
                break;
            }

            methodIndexY++;
        }

        // return ID of method if it was found otherwise return -1
        if(!methodFound){
            return -1;
        }
        else{
            return methodIndexY;
        }
    }


    /**
     * Find a pair contract for given contract in given list of contracts. Contracts has to have one of given allowed
     * comparisons. If contract was found its ID is returned otherwise it is -1.
     *
     * @param contractX             Contract which pair should be found
     * @param contractsY            List of contracts where the pair should be
     * @param allowedComparisons    Which contracts comparisons are considered as found
     * @return                      ID of contract in list if found, -1 otherwise
     */
    private int findPairContract(Contract contractX, List<Contract> contractsY, List<ContractComparison> allowedComparisons) {

        int contractIndexY = 0;
        boolean contractFound = false;

        // go through contracts of method X and try to find contract that is equal or has just minor changes
        while (contractIndexY < contractsY.size()) {

            // compare given contracts
            Contract contractY = contractsY.get(contractIndexY);
            contractComparison = contractX.compareTo(contractY);

            // if contracts are the same or there are minor changes - it is considered as found
            if (allowedComparisons.contains(contractComparison)) {
                contractFound = true;
                break;
            }

            contractIndexY++;
        }

        // return ID of method if it was found otherwise return -1
        if(!contractFound){
            return -1;
        }
        else{
            return contractIndexY;
        }
    }
}

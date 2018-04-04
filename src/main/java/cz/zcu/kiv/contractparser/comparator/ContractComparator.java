package cz.zcu.kiv.contractparser.comparator;

import cz.zcu.kiv.contractparser.model.Contract;

public class ContractComparator {

    public ContractComparison compareContracts(Contract contractX, Contract contractY) {

        // if any of the these conditions is false - contracts are considered different
        // whether the contracts have the same type (Guava, JSR305, ...)
        if(contractX.getContractType() != contractY.getContractType()){
            return ContractComparison.DIFFERENT;
        }

        // whether the contracts have the same condition type (pre-condition, post-condition, class invariant)
        if(contractX.getConditionType() != contractY.getConditionType()) {
            return ContractComparison.DIFFERENT;
        }

        // whether the contracts are in the same class
        if(contractX.getClassName().compareTo(contractY.getClassName()) != 0){
            return ContractComparison.DIFFERENT;
        }

        // whether the contracts are in the same method
        if(contractX.getMethodName().compareTo(contractY.getMethodName()) != 0){
            return ContractComparison.DIFFERENT;
        }

        // whether the contracts use the same function
        if(contractX.getFunction().compareTo(contractY.getFunction()) != 0){
            return ContractComparison.DIFFERENT;
        }


        // compare contract expression (first argument of the function)
        // unless are the contracts considered the same return the answer
        ContractComparison expressionComparison = compareContractExpressions(contractX.getExpression(),
                contractY.getExpression());
        if(expressionComparison != ContractComparison.EQUAL){
            return expressionComparison;
        }


        // all of following comparisons are message related - if there are some differences they are considered minor
        // compare messages of contracts
        if(contractX.getErrorMessage().compareTo(contractY.getErrorMessage()) != 0){
            return ContractComparison.MINOR_CHANGE;
        }

        // compare number of arguments
        // different counts of arguments are considered just as changed as they are usually related to message
        if(contractX.getArguments().size() != contractY.getArguments().size()){
            return ContractComparison.MINOR_CHANGE;
        }

        // compare arguments itself
        for (int i = 0 ; i < contractX.getArguments().size() ; i++) {

            if(contractY.getArguments().size() <= i){
                break;
            }

            // if the message or the arguments were somehow different return minor change
            if(contractX.getArguments().get(i).compareTo(contractY.getArguments().get(i)) != 0){
                return ContractComparison.MINOR_CHANGE;
            }
        }

        
        // if there was not found any difference - contracts are considered equal
        return ContractComparison.EQUAL;
    }


    private ContractComparison compareContractExpressions(String contractXExpression, String contractYExpression) {

        // TODO vetsi komplexita rozhodnuti nez booolean - rozebrat expression a pouzit SPECIALIZED/GENERALIZED

        if(contractXExpression.compareTo(contractYExpression) == 0){
            return ContractComparison.EQUAL;
        }
        else{
            return ContractComparison.DIFFERENT_EXPRESSION;
        }
    }
}

package cz.zcu.kiv.contractparser.comparator;

import cz.zcu.kiv.contractparser.model.Contract;

/**
 * ContractComparator provides means to compare two contracts. It produces ContractComparison which says how the
 * contracts differentiate.
 *
 * @author Vaclav Mares
 * */
public class ContractComparator {

    /**
     * This method compares two contracts. It produces ContractComparison which says how the contracts differentiate.
     * It compares its types, condition types, expressions and other arguments.
     *
     * @param contractX     First contract to be compared
     * @param contractY     Second contract to be compared
     * @return              ContractComparison - ENUM specifying the difference between two contracts
     */
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


        // following comparisons are related to secondary arguments - any differences are considered minor

        // compare number of arguments
        // different counts of arguments are considered also just as minor change as they are usually related to message
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


    /**
     * This method compares expression of both contracts. As for now method only compares whether the expressions
     * are the completely equal or not.
     *
     * @param contractXExpression   Expression of first contract to be compared
     * @param contractYExpression   Expression of second contract to be compared
     * @return                      ContractComparison - ENUM specifying the difference between the expression
     */
    private ContractComparison compareContractExpressions(String contractXExpression, String contractYExpression) {

        // This method can be largely improved to increase information gain from the contract comparison

        if(contractXExpression.compareTo(contractYExpression) == 0){
            return ContractComparison.EQUAL;
        }
        else{
            return ContractComparison.DIFFERENT_EXPRESSION;
        }
    }
}

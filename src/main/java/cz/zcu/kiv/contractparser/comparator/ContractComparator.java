package cz.zcu.kiv.contractparser.comparator;

import cz.zcu.kiv.contractparser.model.Contract;

public class ContractComparator {

    public ContractComparison compareContracts(Contract contractX, Contract contractY) {

        boolean contractTypeEqual = contractX.getContractType() == contractY.getContractType();
        boolean conditionTypeEqual = contractX.getConditionType() == contractY.getConditionType();
        boolean classEqual = contractX.getClassName().compareTo(contractY.getClassName()) == 0;
        boolean methodEqual = contractX.getMethodName().compareTo(contractY.getMethodName()) == 0;

        // if contracts aren't the same type or aren't in the same class(method) they are considered different
        if(contractTypeEqual && conditionTypeEqual && classEqual && methodEqual){

            // compare contract functions
            boolean functionEqual = contractX.getFunction().compareTo(contractY.getFunction()) == 0;

            // if the function is different contracts are considered also different
            if(functionEqual){

                // TODO dat jako MESSAGE_CHANGE/SMAZAT nebo parametrizovat
                // compare messages of contracts
                boolean messageEqual = contractX.getErrorMessage().compareTo(contractY.getErrorMessage()) == 0;

                // compare number of arguments
                // different counts of arguments are considered just as changed as it is usually related to message
                boolean argumentCountEqual = contractX.getArguments().size() == contractY.getArguments().size();

                // compare arguments itself
                boolean argumentEqual = true;
                for (int i = 0 ; i < contractX.getArguments().size() ; i++) {

                    if(contractY.getArguments().size() <= i){
                        break;
                    }

                    if(contractX.getArguments().get(i).compareTo(contractY.getArguments().get(i)) != 0){
                        argumentEqual = false;
                    }
                }// TODO konec message casti


                // compare contract expression (first argument of the function
                // TODO vetsi komplexita rozhodnuti nez booolean (např x > 0 vs x >= 0) -> MESSAGE_CHANGE
                boolean expressionEqual = contractX.getExpression().compareTo(contractY.getExpression()) == 0;

                if(expressionEqual){

                    if(!messageEqual || !argumentCountEqual || !argumentEqual){
                        return ContractComparison.MESSAGE_CHANGE;
                    }
                    else{
                        return ContractComparison.EQUAL;
                    }
                }
                else{
                    return ContractComparison.DIFFERENT;
                }
            }
            else{
                return ContractComparison.DIFFERENT;
            }
        }
        else{
            return ContractComparison.DIFFERENT;
        }
    }
}

package cz.zcu.kiv.contractparser.model;

import cz.zcu.kiv.contractparser.comparator.ContractComparator;
import cz.zcu.kiv.contractparser.comparator.comparatormodel.ContractComparison;

import java.util.ArrayList;
import java.util.List;

/**
 * Instances of this class represent individual design by contracts that occurred in classes as invariants
 * or in methods as pre/post conditions.
 *
 * @author Vaclav Mares
 */
public class Contract {

    /** Type of this contract (Guava, JSR305, ...) */
    private ContractType contractType;

    /** Type of the contract condition (pre-condition, post-condition or class invariant) */
    private ConditionType conditionType;

    /** Complete expression representing the contract */
    private String completeExpression;

    /** Contract function used. For instance "checkNotNull" or "Nonnull" */
    private String function;

    /** Contract condition expression represented as a String (first argument of contract function)*/
    private String expression;

    /** All other arguments used to specify contract. Usually used for error messages */
    private List<String> arguments;


    public Contract(ContractType contractType, ConditionType conditionType, String completeExpression, String function,
                    String expression, List<String> arguments) {
        this.contractType = contractType;
        this.conditionType = conditionType;
        this.completeExpression = completeExpression;
        this.function = function;
        this.expression = expression;

        if(arguments != null){
            this.arguments = arguments;
        }
        else{
            this.arguments = new ArrayList<>();
        }
    }


    /**
     * Compare this contract to other contract.
     *
     * @param otherContract     Contract to be compared with
     * @return                  Comparison of both contracts
     */
    public ContractComparison compareTo(Contract otherContract) {
        
        ContractComparator contractComparator = new ContractComparator();
        return contractComparator.compareContracts(this, otherContract);
    }


    @Override
    public String toString() {
        return "Contract{" +
                "contractType=" + contractType +
                ", conditionType=" + conditionType +
                ", function='" + function + '\'' +
                ", expression='" + expression + '\'' +
                ", arguments=" + arguments +
                '}';
    }
    

    // Getters and Setters
    public ContractType getContractType() {
        return contractType;
    }

    public ConditionType getConditionType() {
        return conditionType;
    }

    public String getExpression() {
        return expression;
    }

    public String getFunction() {
        return function;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }

    public void setConditionType(ConditionType conditionType) {
        this.conditionType = conditionType;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public String getCompleteExpression() {
        return completeExpression;
    }

    public void setCompleteExpression(String completeExpression) {
        this.completeExpression = completeExpression;
    }
}

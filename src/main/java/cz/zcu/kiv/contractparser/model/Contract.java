package cz.zcu.kiv.contractparser.model;

/**
 * Instances of this class represent individual design by contracts that occurred in classes as invariants
 * or in methods as pre/post conditions.
 *
 * @Author Václav Mareš
 */
public class Contract {

    /** Type of this contract (Guava, JSR305) */
    public ContractType contractType;

    /** Type of the contract condition (pre-condition, post-condition or class invariant) */
    public ConditionType conditionType;

    /** Name of the variable that is affected
     * TODO pripadne i volani funkce ? ... x.length() ... typ zustane podle navratu
     * */
    public String variableName;

    /** Type of the variable that is affected
     * TODO budu schopen ziskat ?
     * */
    public String variableType;

    /** Operator of the condition */
    public Operator operator;

    /** Whether the operator is binary or unary */
    public boolean isOperatorBinary;

    /** Value to which the variable is compared to (used only with binary operator) */
    public String comparedValue;

    /** Error message which is shown when the contract is broken */
    public String errorMessage;
}

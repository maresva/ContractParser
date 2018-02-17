package cz.zcu.kiv.contractparser.model;

import com.github.javaparser.ast.Node;

/**
 * Instances of this class represent individual design by contracts that occurred in classes as invariants
 * or in methods as pre/post conditions.
 *
 * @author Vaclav Mares
 */
public class Contract {

    /** Type of this contract (Guava, JSR305) */
    private ContractType contractType;

    /** Type of the contract condition (pre-condition, post-condition or class invariant) */
    private ConditionType conditionType;

    /** Node containing expressionNode with the contract */
    transient private Node expressionNode;

    private String expression;

    /** Error message which is shown when the contract is broken */
    private String errorMessage;


    public Contract(ContractType contractType, ConditionType conditionType, Node expressionNode,
                    String expression, String errorMessage) {
        this.contractType = contractType;
        this.conditionType = conditionType;
        this.expressionNode = expressionNode;
        this.expression = expression;
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "contractType=" + contractType +
                ", conditionType=" + conditionType +
                ", expression=" + expression +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }

    // Getters and Setters
    public ContractType getContractType() {
        return contractType;
    }

    public ConditionType getConditionType() {
        return conditionType;
    }

    public Node getExpressionNode() {
        return expressionNode;
    }

    public String getExpression() {
        return expression;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }

    public void setConditionType(ConditionType conditionType) {
        this.conditionType = conditionType;
    }

    public void setExpressionNode(Node expressionNode) {
        this.expressionNode = expressionNode;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

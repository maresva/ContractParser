package cz.zcu.kiv.contractparser.comparator;

/**
 * Values which can be used to determine type of contract.
 *
 * @author Vaclav Mares
 */
public enum ContractComparison {
    EQUAL,              // Contracts are the same
    MESSAGE_CHANGE,     // Contracts are the same but error message is different
    SPECIALIZED,        // Contract has more strict criteria than the other
    GENERALIZED,        // Contract has more loose criteria than the other
    DIFFERENT           // Contracts are different
}

package cz.zcu.kiv.contractparser.comparator;

/**
 * Values which can be used to determine differences between two contract.
 *
 * @author Vaclav Mares
 */
public enum ContractComparison {
    EQUAL,                  // Contracts are the same
    MINOR_CHANGE,           // Contracts are almost the same (ie. error message could be different)
    SPECIALIZED,            // Contract has more strict criteria than the other
    GENERALIZED,            // Contract has more loose criteria than the other
    DIFFERENT_EXPRESSION,   // Contract is the same type but has completely different expression
    DIFFERENT               // Contracts are completely different (contract type, condition type etc.)
}

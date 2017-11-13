package cz.zcu.kiv.contractparser.model;

/**
 * Values which can be used as a contract condition operators.
 *
 * @Author Václav Mareš
 */
public enum Operator {
    // Binary
    EQUAL,
    NOT_EQUAL,
    GREATER_THAN,
    GREATER_THAN_OR_EQUAL,
    LESS_THAN,
    LESS_THAN_OR_EQUAL,

    // Unary
    NOT_NULL
}

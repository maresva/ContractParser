package cz.zcu.kiv.contractparser.model;

/**
 * Values which can be used as a condition types.
 *
 * @author Vaclav Mares
 */
public enum ConditionType {
    PRE,        // contract pre-condition
    POST,       // contract post-condition
    INVARIANT   // class invariant
}

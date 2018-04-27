package cz.zcu.kiv.contractparser.comparator.comparatormodel;

/** Kind of API state - added, removed, found pair. Used in ApiChange
 *
 * @author Vaclav Mares
 * */
public enum ApiState {
    REMOVED,        // class/method has been removed
    ADDED,          // class/method has been added
    FOUND_PAIR      // pair for given class/method has been found
}

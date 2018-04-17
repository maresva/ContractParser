public class TestGuavaManyFunctions {

    public void guavaTest(String x){

        checkNotNull(x);
        checkArgument(x.length > 0);
        checkState(x.length > 0);
        checkNotNull(x);
        checkElementIndex(0, 2);
        badElementIndex(0, 2, "Desc");
        checkPositionIndex(0, 2);
        badPositionIndex(0, 2, "Desc");
        checkPositionIndexes(0, 1, 2);
        badPositionIndexes(0, 1, 2);
    }
}

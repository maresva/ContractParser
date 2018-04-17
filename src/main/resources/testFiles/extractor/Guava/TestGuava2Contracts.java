public class TestGuava2Contracts {

    public void guavaTest(String x){

        Preconditions.checkNotNull(x);
        Preconditions.checkArgument(x.length > 0);
    }
}
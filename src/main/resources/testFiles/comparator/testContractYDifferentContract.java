public class TestContractYDifferentContract {

    public static int guavaTest(String x){

        Preconditions.checkArgument(x.length() == 0, "Illegal Argument passed: Negative value %s.", x);

        return x;
    }
}

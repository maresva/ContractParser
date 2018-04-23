public class TestContractYChangeContractMessage {

    public static int guavaTest(String x){

        com.google.common.base.Preconditions.checkNotNull(x, "Parameter x must not be null.");
        //com.google.common.base.Preconditions.checkArgument(x.length() == 0, "Illegal Argument passed: Negative value %s.", x);

        return x;
    }
}

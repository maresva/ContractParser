public class TestContractX {

    public static int guavaTest(String x){

        Preconditions.checkNotNull(x, "x must not be null.");

        return x;
    }
}
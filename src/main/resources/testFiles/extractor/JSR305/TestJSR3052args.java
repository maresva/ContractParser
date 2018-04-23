package extractor.JSR305;

public class TestJSR3052args {

    public void guavaTest(String x){

        com.google.common.base.Preconditions.checkNotNull(x, "x must not be null.");
    }
}

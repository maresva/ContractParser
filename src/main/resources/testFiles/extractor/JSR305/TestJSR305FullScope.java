package extractor.JSR305;

public class TestJSR305FullScope {

    public void guavaTest(String x){

        com.google.common.base.Preconditions.checkNotNull(x);
    }
}

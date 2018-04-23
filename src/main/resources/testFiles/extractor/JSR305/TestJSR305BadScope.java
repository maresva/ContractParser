package extractor.JSR305;

public class TestJSR305BadScope {

    public void guavaTest(String x){

        Bad.checkNotNull(x);
    }
}

class Bad {

    public static void checkNotNull(String x){

    }
}

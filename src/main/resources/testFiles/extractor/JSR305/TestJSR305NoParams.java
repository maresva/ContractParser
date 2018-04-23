package extractor.JSR305;

public class TestJSR305NoParams {

    public void guavaTestNoParams(String x){

        Preconditions.checkNotNull();
    }
}

class Preconditions {

    public static void checkNotNull(){

    }

    public static void badMethod(String x){

    }
}

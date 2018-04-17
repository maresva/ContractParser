@CheckReturnValue
public class TestJSR3052Methods {

    @Untainted
    public void JSR305Test(String x){
      // empty
    }
    
    public void JSR305Test2(@Nonnull String x){
      // empty
    }
}
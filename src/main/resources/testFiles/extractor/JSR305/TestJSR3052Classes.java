@CheckReturnValue
public class TestJSR3052Classes {
    
    public void JSR305Test(String x){
      // empty
    }
}

public class TestJSR3052Classes2 {
  
    @Untainted
    public void JSR305Test2(@Nonnull String x){
      // empty
    }
}
    
    
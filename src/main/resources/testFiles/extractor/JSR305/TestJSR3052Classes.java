package extractor.JSR305;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Untainted;

@CheckReturnValue
public class TestJSR3052Classes {
    
    public void JSR305Test(String x){
      // empty
    }
}

class TestJSR3052Classes2 {
  
    @Untainted
    public void JSR305Test2(@Nonnull String x){
      // empty
    }
}
    
    
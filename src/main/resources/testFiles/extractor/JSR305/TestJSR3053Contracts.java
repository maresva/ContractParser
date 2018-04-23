package extractor.JSR305;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Untainted;

@CheckReturnValue
public class TestJSR3053Contracts {

    @Untainted
    public void JSR305Test(@Nonnull String x){
      // empty
    }
}
package extractor.JSR305;

import javax.annotation.*;
import java.io.BufferedReader;

@Nonnegative
@ParametersAreNullableByDefault
public class TestJSR305ManyFunctions {

    @Untainted
    public void JSR305Test(@Nonnull String x, @CheckForNull String y, @WillClose BufferedReader bufferedReader){
      // empty
    }
}
@OverridingMethodsMustInvokeSuper
@ParametersAreNullableByDefault
public class TestJSR305ManyFunctions {

    @Untainted
    public void JSR305Test(@Nonnull String x, @CheckForNull String y, @WillClose BufferedReader bufferedReader){
      // empty
    }
}
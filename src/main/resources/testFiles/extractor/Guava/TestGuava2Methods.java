public class TestGuava2Methods {

    public void guavaTest(String x){

        Preconditions.checkNotNull(x);
    }
    
    public void guavaTest2(String x){
      
        Preconditions.checkArgument(x.length > 0);
    }
}
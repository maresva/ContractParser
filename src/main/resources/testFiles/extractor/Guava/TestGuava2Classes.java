public class TestGuava2Classes {

    public void guavaTest(String x){

        Preconditions.checkNotNull(x);
    }
}


public class TestGuava2Classes2 {
    
    public void guavaTest2(String x){
      
        Preconditions.checkArgument(x.length > 0);
    }
}
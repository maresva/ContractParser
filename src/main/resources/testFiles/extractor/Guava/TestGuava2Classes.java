package extractor.Guava;

import com.google.common.base.Preconditions;

public class TestGuava2Classes {

    public void guavaTest(String x){

        Preconditions.checkNotNull(x);
    }
}


class TestGuava2Classes2 {
    
    public void guavaTest2(String x){
      
        Preconditions.checkArgument(x.length() > 0);
    }
}
package extractor.Guava;

import static com.google.common.base.Preconditions.checkNotNull;

public final class TestGuavaMoreContractsInOneNode{

  public String guavaTest(String x, String y, String z) {

      String test = "";
    if((checkNotNull(x).length() == 0) || (checkNotNull(y).length() == 0)){
      test = checkNotNull(z);
    }
    
    return checkNotNull(test);
  }
}


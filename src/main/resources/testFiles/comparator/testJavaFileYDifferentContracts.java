package cz.zcu.kiv.contractparser;

import com.google.common.base.Preconditions;
import cz.zcu.kiv.contractparser.io.IOServices;
import cz.zcu.kiv.contractparser.model.JavaFile;
import cz.zcu.kiv.contractparser.parser.ContractType;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.HashMap;

/**
 * Main class - FOR DEBUGGING PURPOSES ONLY
 *
 * @Author Vaclav Mares
 */
public class TestJavaFileX {

    final Logger logger = Logger.getLogger(String.valueOf(Main.class));
    static HashMap<ContractType, Boolean> contractTypes = new HashMap<>();

    @Nonnull
    @Override
    public static void main(String[] args) {

        contractTypes.put(ContractType.GUAVA, true);
        contractTypes.put(ContractType.JSR305, false);

        // Test of single line commentary

        /* Test of multi-line conmmentary #0
          Test of multi-line conmmentary #1
          Test of multi-line conmmentary #2
        Test of multi-line conmmentary #n */ 
        
        boolean x = true;
        if(x){
          System.out.println("TRUE");
        }
        else{
          System.out.println("FALSE");
        }

        JavaFile javaFile = Api.retrieveContracts("D:/test/twoClass.java", contractTypes);
        IOServices.exportToJson(javaFile, "D:/test/testJson");
    }


    @Nonnull
    public static int guavaTest(@Nonnull String x){

        //Preconditions.checkNotNull(x, "x must not be null.");

        Preconditions.checkArgument(x.length() == 0, "Illegal Argument passed: Negative value %s.", x);

        int y = 5;

        return y;
    }
}

package cz.zcu.kiv.contractparser;

import com.google.common.base.Preconditions;
import cz.zcu.kiv.contractparser.io.IOServices;
import cz.zcu.kiv.contractparser.model.ContractType;
import cz.zcu.kiv.contractparser.model.JavaFile;
import org.apache.log4j.Logger;

import javax.annotation.Nonnull;
import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Main class - FOR DEBUGGING PURPOSES ONLY
 *
 * @author Vaclav Mares
 */
public class Main {

    final Logger logger = Logger.getLogger(String.valueOf(Main.class));
    static HashMap<ContractType, Boolean> contractTypes = new HashMap<>();

    public static void main(String[] args) {

        contractTypes.put(ContractType.GUAVA, true);
        contractTypes.put(ContractType.JSR305, false);

        //JavaFile javaFile = ContractManagerApi.retrieveContracts("D:/test/mainParser.java", contractTypes);

        List<JavaFile> javaFiles = ContractManagerApi.retrieveContractsFromFolder("D:/test", contractTypes);

        IOServices.exportManyToJson(javaFiles, new File("D:/test/JSON_export"));

        //System.out.println(javaFile);
        //IOServices.exportToJson(javaFile, "D:/test/mainParserJson");
    }


    @Nonnull
    public static int guavaTest(@Nonnull String x){

        Preconditions.checkNotNull(x, "x must not be null.");

        //Preconditions.checkArgument(x.length() == 0, "Illegal Argument passed: Negative value %s.", x);

        int y = 5;

        return y;
    }
}

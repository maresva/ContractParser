package cz.zcu.kiv.contractparser;

import com.google.common.base.Preconditions;
import cz.zcu.kiv.contractparser.io.IOServices;
import cz.zcu.kiv.contractparser.model.Contract;
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
        contractTypes.put(ContractType.JSR305, true);

        //long tStart = System.nanoTime();


        //List<JavaFile> javaFiles = ContractManagerApi.retrieveContractsFromFolder("D:\\My\\_ZCU\\dp\\anot-20170926T144409Z-001\\anot\\guava\\guava-10.0", contractTypes);
        //List<JavaFile> javaFiles = ContractManagerApi.retrieveContractsFromFolder("D:\\", contractTypes);

        JavaFile javaFile = ContractManagerApi.retrieveContracts("D:\\test\\twoClass.java", contractTypes);
        //JavaFile javaFile = ContractManagerApi.retrieveContracts("tempFile.java", contractTypes);

        //for(Contract contract : javaFile.getContracts()){
            //System.out.println(contract);
        //}

        File file = new File("D:/test/mainParserJson");
        IOServices.exportToJson(javaFile, file);

        //long tEnd = System.nanoTime();
        //long tRes = tEnd - tStart;

        //System.out.println("Total time: " + (tRes/1000000) + "ms (" + javaFiles.size() + " files)");

        //IOServices.exportManyToJson(javaFiles, new File("D:/test/JSON_export"));

        //System.out.println(javaFile);

    }


    @Nonnull
    public static int guavaTest(@Nonnull String x){

        Preconditions.checkNotNull(x, "x must not be null.");

        //Preconditions.checkArgument(x.length() == 0, "Illegal Argument passed: Negative value %s.", x);

        int y = 5;

        return y;
    }
}

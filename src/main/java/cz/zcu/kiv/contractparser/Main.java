package cz.zcu.kiv.contractparser;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.javafx.iio.ios.IosDescriptor;
import cz.zcu.kiv.contractparser.comparator.JavaFolderCompareReport;
import cz.zcu.kiv.contractparser.io.IOServices;
import cz.zcu.kiv.contractparser.model.JavaFile;
import org.apache.log4j.Logger;

import javax.annotation.*;
import java.io.File;
import java.text.MessageFormat;
import java.util.List;


/**
 * Main class - FOR DEBUGGING PURPOSES ONLY
 *
 * @author Vaclav Mares
 */
@WillClose
public class Main {

    private static final Logger logger = Logger.getLogger(String.valueOf(Main.class));

    public static void main(String[] args) {

        logger.info("Starting Main ...");

        //IOServices.saveJsonToFile("JSON", "blbost::java", new File("D:/test"));

        //System.out.println(ResourceHandler.getMessage("errorFilesNotRetrieved2", "TEST", "test2"));

        //List<JavaFile> javaFilesX = ContractExtractorApi.retrieveContractsFromFolder(new File("D:\\test\\guava-10.0"), false);

        //ContractExtractorApi.retrieveContractsFromFolderExportToJson(new File("D:\\test\\guava-10.0"), new File("D:/test/guavaOUTJson"), true, true);

        //JavaFile javaFileX = ContractExtractorApi.retrieveContracts(new File("D:\\test\\guava-10.0\\com\\google\\common\\cache\\CacheBuilder.java"), false);
        //JavaFile javaFileY = ContractExtractorApi.retrieveContracts(new File("D:\\test\\guava-13.0\\com\\google\\common\\base\\CharMatcher.java"), false);

        //ContractComparatorApi.compareJavaFoldersAndExportToJson(new File("D:\\test\\guava-10.0\\com"), new File("D:\\test\\guava-14.0\\com"), true, true, new File("D:\\test\\ExportTest"), true);

        //JavaFile javaFileX = ContractExtractorApi.retrieveContracts(new File("D:\\test\\testCase.java"), false);
        //JavaFile javaFileY = ContractExtractorApi.retrieveContracts("D:\\test\\testCaseY.java", false);

        //IOServices.saveObjectAsJson(javaFileX, "TESTJSON", new File("D:/test/1234"), true);

        Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        String json = gson.toJson("RemovalListener<? super K, ? super V> removalListener");
        System.out.println(json);

        System.out.println("END");

        //JavaFolderCompareReport javaFolderCompareReport = ContractComparatorApi.compareJavaFolders("D:\\test\\guava-10.0","D:\\test\\guava-14.0", false, false);

        //ContractComparatorApi.exportJavaFolderCompareReportToJson(javaFolderCompareReport, new File("D:\\test\\compareJson"));

        //JavaFile javaFile = ContractExtractorApi.retrieveContracts(new File("D:/test/BLBOST.java"), true);

        //JavaFileCompareReport javaFileCompareReport = javaFileX.compareJavaFileTo(javaFileY, true, false);
        //exportJavaFileCompareReportToJson(javaFileCompareReport, new File("D:\\test\\compareJson"));


        //IOServices.exportToJson(javaFile, new File("D:/test/mainParserJson"));
        //IOServices.exportManyToJson(javaFiles, new File("D:/test/JSON_export"));
    }


    private static void meassureTime(){

        //long tStart = System.nanoTime();
        //long tEnd = System.nanoTime();
        //long tRes = tEnd - tStart;

        //System.out.println("Total time: " + (tRes/1000000) + "ms (" + javaFiles.size() + " files)");
    }



    @PropertyKey
    @WillClose
    public static String guavaTest(@WillClose String x){

        Preconditions.checkNotNull(x, "x must not be null.");

        //Preconditions.checkArgument(x.length() == 0, "Illegal Argument passed: Negative value %s.", x);

        String y = "ystring";

        return y;
    }
}
